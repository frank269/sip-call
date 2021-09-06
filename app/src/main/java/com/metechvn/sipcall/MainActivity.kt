package com.metechvn.sipcall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //
//    private lateinit var sipCall: SipCall
//    private lateinit var login: Button
//    private lateinit var call: Button
//    private lateinit var accept: Button
//    private lateinit var decline: Button
//    private lateinit var endcall: Button
//    private lateinit var logout: Button
//
//
//    private val TAG = "MainActivity"
//    private lateinit var receiver: BroadcastReceiver
//    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        login = findViewById(R.id.login)
//        call = findViewById(R.id.call)
//        accept = findViewById(R.id.accept)
//        decline = findViewById(R.id.decline)
//        endcall = findViewById(R.id.endcall)
//        logout = findViewById(R.id.logout)
//
//
//        receiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                Log.e(TAG, "onReceive: ${intent?.action}")
//                when (intent?.action) {
//                    Status.RegistrationState_Ok -> {
//                        call.isEnabled = true
//                        login.isEnabled = false
//                        logout.isEnabled = true
//                    }
//
//                    Status.Call_State_IncomingReceived -> {
//                        accept.isEnabled = true
//                        decline.isEnabled = true
//                        call.isEnabled = false
//                    }
//
//                    Status.Call_State_Connected -> {
//                        endcall.isEnabled = true
//                    }
//
//                    Status.Call_State_End -> {
//                        call.isEnabled = true
//                        accept.isEnabled = false
//                        decline.isEnabled = false
//                        endcall.isEnabled = false
//                    }
//
//                    Status.Call_State_Released -> {
//                        call.isEnabled = true
//                        accept.isEnabled = false
//                        decline.isEnabled = false
//                        endcall.isEnabled = false
//                    }
//
//                    Status.RegistrationState_Cleared -> {
//                        call.isEnabled = false
//                        logout.isEnabled = false
//                        login.isEnabled = true
//                    }
//                }
//            }
    }
//
//        sipCall = SipCall()
//        sipCall.startService(this)
//
//        login.setOnClickListener {
//            sipCall.login(
//                "1115",
//                "K3AqvqG8.?Kb7!5%7^Mf",
//                "call.metechvn.com:5090"
//            )
//        }
//
//        call.setOnClickListener {
//            sipCall.call("1114")
//            call.isEnabled = false
//            endcall.isEnabled = true
//        }
//
//        accept.setOnClickListener {
//            sipCall.accept()
//            accept.isEnabled = false
//            decline.isEnabled = false
//        }
//
//        decline.setOnClickListener {
//            sipCall.decline()
//            accept.isEnabled = false
//            decline.isEnabled = false
//        }
//
//        endcall.setOnClickListener {
//            sipCall.endCall()
//        }
//
//        logout.setOnClickListener {
//            sipCall.logout()
//        }
//
//    }
//
//    override fun onStart() {
//        super.onStart()
//        checkAndRequestCallPermissions()
//        val filter = IntentFilter()
//        filter.addAction(Status.RegistrationState_None)
//        filter.addAction(Status.RegistrationState_Progress)
//        filter.addAction(Status.RegistrationState_Ok)
//        filter.addAction(Status.RegistrationState_Cleared)
//        filter.addAction(Status.RegistrationState_Failed)
//        filter.addAction(Status.Call_State_Idle)
//        filter.addAction(Status.Call_State_IncomingReceived)
//        filter.addAction(Status.Call_State_OutgoingInit)
//        filter.addAction(Status.Call_State_OutgoingProgress)
//        filter.addAction(Status.Call_State_OutgoingRinging)
//        filter.addAction(Status.Call_State_OutgoingEarlyMedia)
//        filter.addAction(Status.Call_State_Connected)
//        filter.addAction(Status.Call_State_StreamsRunning)
//        filter.addAction(Status.Call_State_Pausing)
//        filter.addAction(Status.Call_State_Paused)
//        filter.addAction(Status.Call_State_Resuming)
//        filter.addAction(Status.Call_State_Referred)
//        filter.addAction(Status.Call_State_Error)
//        filter.addAction(Status.Call_State_End)
//        filter.addAction(Status.Call_State_PausedByRemote)
//        filter.addAction(Status.Call_State_UpdatedByRemote)
//        filter.addAction(Status.Call_State_IncomingEarlyMedia)
//        filter.addAction(Status.Call_State_Updating)
//        filter.addAction(Status.Call_State_Released)
//        filter.addAction(Status.Call_State_EarlyUpdatedByRemote)
//        filter.addAction(Status.Call_State_EarlyUpdating)
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter)
//    }
//
//    private fun checkAndRequestCallPermissions() {
//        val permissionsList: MutableList<String> = ArrayList()
//        val recordAudio = packageManager.checkPermission(Manifest.permission.RECORD_AUDIO, packageName)
//        Log.e(
//            TAG, "[Permission] Record audio permission is " +
//                    if (recordAudio == PackageManager.PERMISSION_GRANTED) "granted" else "denied"
//        )
//
//        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
//            Log.e(TAG, "[Permission] Asking for record audio")
//            permissionsList.add(Manifest.permission.RECORD_AUDIO)
//        }
//
//        if (permissionsList.size > 0) {
//            ActivityCompat.requestPermissions(this, permissionsList.toTypedArray(), 0)
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        for (i in permissions.indices) {
//            Log.e(
//                TAG,
//                "[Permission] ${permissions[i]} is " +
//                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) "granted" else "denied"
//            )
//        }
//    }
}