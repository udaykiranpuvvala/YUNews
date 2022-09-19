package com.unik.yunews.activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.databinding.ActivityVerifyOtpBinding
import java.util.concurrent.TimeUnit

class VerifyOTPActivity : AppCompatActivity() {

    lateinit var binding : ActivityVerifyOtpBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var phNo: String
    lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_verify_otpactivity)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)

        initUI()
    }

    private fun initUI() {

        phNo = intent.getStringExtra("phno").toString()
        verificationId = intent.getStringExtra("verificationId").toString()
        mAuth = FirebaseAuth.getInstance();


        binding.txtVerification.text = "We have send the OTP to ${phNo}"

        binding.txtVerifyOtp.setOnClickListener {
            if (!Utility.isValueNullOrEmpty(binding.pinOTP.text.toString())) {
                verifyCode(binding.pinOTP.text.toString())
            } else {
//                PopUtils.alertDialog(this, "Please Enter OTP", null)
//                Utility.setSnackBarEnglish(this,txtVerifyOtp,"Please Enter OTP")
                Toast.makeText(this,"Please Enter OTP",Toast.LENGTH_LONG).show()
            }
        }

        callTimerForResendText()

        binding.txtResend.setOnClickListener {
            if(binding.txtResend.text.equals("Resend")){
                Utility.showLoadingDialog(this, "Wait for OTP...", true)
                sendVerificationCode("+91$phNo")
            }
//            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    private fun callTimerForResendText() {
        val timer = object : CountDownTimer(20000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toString()
                if (seconds.length == 1) {
                    binding.txtResend.text = "00:0${seconds}"
                } else {
                    binding.txtResend.text = "00:${seconds}"
                }
            }

            override fun onFinish() {
                binding.txtResend.text = "Resend"
            }
        }
        timer.start()
    }


    private fun verifyCode(code: String) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        // after getting credential we are
        // calling sign in method.
        Utility.showLoadingDialog(this, "Loading...", false)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                Utility.hideLoadingDialog()
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    val intent = Intent(this@VerifyOTPActivity, HomeActivity::class.java)
                    intent.putExtra("phno", "" + phNo)
                    startActivity(intent)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun sendVerificationCode(phone: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone, // first parameter is user's mobile number
            60, // second parameter is time limit for OTP
            // verification which is 60 seconds in our case.
            TimeUnit.SECONDS, // third parameter is for initializing units
            // for time period which is in seconds in our case.
            this, // this task will be excuted on Main thread.
            mCallBack // we are calling callback method when we recieve OTP for
            // auto verification of user.
        );
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
                Utility.hideLoadingDialog()
                /* val intent = Intent(this@SignUPActivity, VerifyOTPActivity::class.java)
                 intent.putExtra("phno", "" + edtPhone.text.trim().toString())
                 intent.putExtra("verificationId", "" + verificationId)*/
                startActivity(intent)
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
//                    edtPhone.setText(code)
                    callTimerForResendText()

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
//                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(this@VerifyOTPActivity, "Verify OTP : " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
}