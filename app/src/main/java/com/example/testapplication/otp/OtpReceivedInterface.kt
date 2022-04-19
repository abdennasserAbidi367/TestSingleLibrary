package com.example.testapplication.otp

interface OtpReceivedInterface {
    fun onOtpReceived(otp: String)
    fun onOtpTimeout()
}
