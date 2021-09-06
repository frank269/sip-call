package com.metechvn.call.service

import android.app.Service
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.metechvn.call.R
import org.linphone.core.*
import org.linphone.mediastream.Version
import java.io.File
import java.io.IOException
import java.util.*

class LinphoneService : Service() {
    private val TAG = "LinphoneService"
    private val START_LINPHONE_LOGS: String = " ==== Device information dump ===="
    private val CHANNEL_ID = "12345"
    private val NOTIFICATION_ID = 0

    private var mCore: Core? = null
    private var mCoreListener: CoreListenerStub? = null

    private var mTimer: Timer? = null
    private var mHandler: Handler? = null

    companion object {
        var sInstance: LinphoneService? = null

        fun isReady(): Boolean {
            return sInstance != null
        }

        fun getInstance(): LinphoneService {
            return sInstance!!
        }

        fun getCore(): Core? {
            return sInstance?.mCore
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val basePath = filesDir.absolutePath
        Factory.instance().setLogCollectionPath(basePath)
        Factory.instance().enableLogCollection(LogCollectionState.Enabled)
        Factory.instance().setDebugMode(true, "sip-call")
        dumDeviceInformation()
        dumInstalledLinphoneInformation()
        mHandler = Handler(Looper.getMainLooper())
        mCoreListener = object : CoreListenerStub() {
            override fun onCallStateChanged(lc: Core?, call: Call?, cstate: Call.State?, message: String?) {
                super.onCallStateChanged(lc, call, cstate, message)
                when (cstate) {
                    Call.State.Idle -> { sendActionBroadcast("Call.State.Idle") }

                    Call.State.IncomingReceived -> { sendActionBroadcast("Call.State.IncomingReceived") }

                    Call.State.OutgoingInit -> { sendActionBroadcast("Call.State.OutgoingInit") }

                    Call.State.OutgoingProgress -> { sendActionBroadcast("Call.State.OutgoingProgress") }

                    Call.State.OutgoingRinging -> { sendActionBroadcast("Call.State.OutgoingRinging") }

                    Call.State.OutgoingEarlyMedia -> { sendActionBroadcast("Call.State.OutgoingEarlyMedia") }

                    Call.State.Connected -> { sendActionBroadcast("Call.State.Connected") }

                    Call.State.StreamsRunning -> { sendActionBroadcast("Call.State.StreamsRunning") }

                    Call.State.Pausing -> { sendActionBroadcast("Call.State.Pausing") }

                    Call.State.Paused -> { sendActionBroadcast("Call.State.Paused") }

                    Call.State.Resuming -> { sendActionBroadcast("Call.State.Resuming") }

                    Call.State.Referred -> { sendActionBroadcast("Call.State.Referred") }

                    Call.State.Error -> { sendActionBroadcast("Call.State.Error") }

                    Call.State.End -> { sendActionBroadcast("Call.State.End") }

                    Call.State.PausedByRemote -> { sendActionBroadcast("Call.State.PausedByRemote") }

                    Call.State.UpdatedByRemote -> { sendActionBroadcast("Call.State.UpdatedByRemote") }

                    Call.State.IncomingEarlyMedia -> { sendActionBroadcast("Call.State.IncomingEarlyMedia") }

                    Call.State.Updating -> { sendActionBroadcast("Call.State.Updating") }

                    Call.State.Released -> { sendActionBroadcast("Call.State.Released") }

                    Call.State.EarlyUpdatedByRemote -> { sendActionBroadcast("Call.State.EarlyUpdatedByRemote") }

                    Call.State.EarlyUpdating -> { sendActionBroadcast("Call.State.EarlyUpdating") }
                    else -> {}
                } }

            override fun onRegistrationStateChanged(lc: Core?, cfg: ProxyConfig?, cstate: RegistrationState?, message: String?) {
                super.onRegistrationStateChanged(lc, cfg, cstate, message)
                when (cstate) {
                    RegistrationState.None -> { sendActionBroadcast("RegistrationState.None") }

                    RegistrationState.Progress -> { sendActionBroadcast("RegistrationState.Progress") }

                    RegistrationState.Ok -> { sendActionBroadcast("RegistrationState.Ok") }

                    RegistrationState.Cleared -> { sendActionBroadcast("RegistrationState.Cleared") }

                    RegistrationState.Failed -> { sendActionBroadcast("RegistrationState.Failed") }
                } }

        }

        try {
            copyIfNotExist(R.raw.linphonerc_default, "$basePath/.linphonerc")
            copyFromPackage(R.raw.linphonerc_factory, "linphonerc")
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
        mCore = Factory.instance().createCore("$basePath/.linphonerc", "$basePath/linphonerc", this)
        mCore?.addListener(mCoreListener)
        configureCore()
    }

    private fun sendActionBroadcast(action: String){
        val intent = Intent()
        intent.action = action
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (sInstance != null) return START_STICKY
        sInstance = this
        mCore?.start()
        val lTask = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    if (mCore != null) mCore!!.iterate()
                }
            }
        }

        mTimer = Timer("Linphone scheduler")
        mTimer?.schedule(lTask, 0, 20)
        return START_STICKY
    }

    private fun dumDeviceInformation() {
        val sb = StringBuilder()
        sb.append("DEVICE=").append(Build.DEVICE).append("\n")
        sb.append("MODEL=").append(Build.MODEL).append("\n")
        sb.append("MANUFACTURER=").append(Build.MANUFACTURER).append("\n")
        sb.append("SDK=").append(Build.VERSION.SDK_INT).append("\n")
        sb.append("Supported ABIs=")

        for (abi in Version.getCpuAbis()) {
            sb.append(abi).append(", ")
        }
        sb.append("\n")
    }

    private fun dumInstalledLinphoneInformation() {
        var info: PackageInfo? = null
        try {
            info = packageManager.getPackageInfo(packageName, 0)
        } catch (nnfe: PackageManager.NameNotFoundException) {
            nnfe.printStackTrace()
        }
        if (info != null) {
            Log.e(
                TAG,
                "[Service] Linphone version is ${info.versionName} ${info.versionCode}"
            )
        } else {
            Log.e(TAG, "[Service] Linphone version is unknown")
        }
    }

    @Throws(IOException::class)
    private fun copyIfNotExist(ressourceId: Int, target: String) {
        val lFileToCopy = File(target)
        if (!lFileToCopy.exists()) {
            copyFromPackage(ressourceId, lFileToCopy.name)
        }
    }

    @Throws(IOException::class)
    private fun copyFromPackage(ressourceId: Int, target: String) {
        val lOutputStream = openFileOutput(target, 0)
        val lInputStream = resources.openRawResource(ressourceId)
        var readByte: Int
        val buff = ByteArray(8048)
        while (lInputStream.read(buff).also { readByte = it } != -1) {
            lOutputStream.write(buff, 0, readByte)
        }

        lOutputStream.flush()
        lOutputStream.close()
        lInputStream.close()
    }

    private fun configureCore() {
        // We will create a directory for user signed certificates if needed
        val basePath = filesDir.absolutePath
        val userCerts = "$basePath/user-certs"
        val f = File(userCerts)
        if (!f.exists()) {
            if (!f.mkdir()) {
                Log.e(TAG, "$userCerts can't be created.")
            }
        }
        mCore?.userCertificatesPath = userCerts
    }

    override fun onDestroy() {
        mCore?.removeListener(mCoreListener)
        mTimer?.cancel()
        mCore?.stop()
        mCore = null
        sInstance = null
        super.onDestroy()
    }
}