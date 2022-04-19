package com.example.testapplication.signup

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.testapplication.R
import com.example.testapplication.otp.OtpReceivedInterface
import com.example.testapplication.otp.SmsBroadcastReceiver
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task

class ThirdFragment : Fragment(), GoogleApiClient.ConnectionCallbacks,
    OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    var mGoogleApiClient: GoogleApiClient? = null
    var mSmsBroadcastReceiver: SmsBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSmsBroadcastReceiver = SmsBroadcastReceiver()
        activity?.let {
            mGoogleApiClient = GoogleApiClient.Builder(it)
                .addConnectionCallbacks(this)
                .enableAutoManage(it, this)
                .addApi(Auth.CREDENTIALS_API)
                .build()
        }

        mSmsBroadcastReceiver?.setOnOtpListeners(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        activity?.registerReceiver(mSmsBroadcastReceiver, intentFilter)
    }

    private fun startSMSListener() {
        activity?.let { act ->
            val mClient = SmsRetriever.getClient(act)
            val mTask: Task<Void> = mClient.startSmsRetriever()
            mTask.addOnSuccessListener {
                Toast.makeText(act, "SMS Retriever starts", Toast.LENGTH_LONG).show()
            }
            mTask.addOnFailureListener {
                Toast.makeText(act, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getHintPhoneNumber() {
        /*val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest)
        try {
            startIntentSenderForResult(mIntent.intentSender, RESOLVE_HINT, null, 0, 0, 0)
        } catch (e: SendIntentException) {
            e.printStackTrace()
        }*/
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val credential: Credential? = data.getParcelableExtra(Credential.EXTRA_KEY)
                    // credential.getId();  < – will need to process phone number string
                    Log.i("", "onActivityResult: ${credential?.id}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_third, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHintPhoneNumber()
    }

    override fun onStart() {
        super.onStart()
        startSMSListener()
    }

    companion object {
        const val RESOLVE_HINT = 2
        fun newInstance(): ThirdFragment = ThirdFragment()
    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onOtpReceived(otp: String) {
        Log.i("ThirdFragment", "onOtpReceived: $otp")
    }

    override fun onOtpTimeout() {
        Log.i("ThirdFragment", "onOtpTimeout: Time out, please resend")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}