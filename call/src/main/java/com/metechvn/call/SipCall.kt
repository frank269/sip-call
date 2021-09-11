package com.metechvn.call

import android.app.Activity
import android.content.Intent
import com.metechvn.call.service.LinphoneService
import org.linphone.core.*

class SipCall {
    private val TAG = "SipCall"

    fun startService(activity: Activity) {
        activity.startService(Intent(activity, LinphoneService::class.java))
    }

    fun login(username: String, password: String, domain: String) {

        val mAccountCreator = LinphoneService.getCore()?.createAccountCreator(null)
        mAccountCreator?.username = username
        mAccountCreator?.password = password
        mAccountCreator?.domain = domain
        mAccountCreator?.transport = TransportType.Tcp
        val cfg: ProxyConfig? = mAccountCreator?.createProxyConfig()
        LinphoneService.getCore()?.defaultProxyConfig = cfg
    }

    fun call(number: String, call_id: String, user_id: String) {
        val addressToCall: Address? = LinphoneService.getCore()?.interpretUrl(number)
        val params: CallParams? = LinphoneService.getCore()?.createCallParams(null)
        params?.enableAudio(true)
        if (addressToCall != null) {
            params?.addCustomHeader("X-Call-Id", call_id)
            params?.addCustomHeader("X-User-Id", user_id)
            LinphoneService.getCore()?.inviteAddressWithParams(addressToCall, params)
        }
    }

    fun accept() {
        val core = LinphoneService.getCore()
        if (core != null) {
            val call: Call? = core.currentCall
            call?.let {
                val params: CallParams = core.createCallParams(call)
                params.enableAudio(true)
                call.acceptWithParams(params)
            }
        }
    }

    fun decline() {
        val core = LinphoneService.getCore()
        if (core != null) {
            val call: Call? = core.currentCall
            call?.decline(Reason.Declined)
        }
    }

    fun endCall() {
        val core = LinphoneService.getCore()
        if (core != null) {
            if (core.callsNb > 0) {
                var call: Call? = core.currentCall
                if (call == null) call =
                    core.calls[0]
                call?.terminate()
            }
        }
    }

    fun logout() {
        try {
            val cfg = LinphoneService.getCore()?.defaultProxyConfig
            if (cfg != null) {
                LinphoneService.getCore()?.removeProxyConfig(cfg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}