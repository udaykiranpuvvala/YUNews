package com.unik.yunews.activites

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unik.yunews.R
import com.unik.yunews.Utility
import com.unik.yunews.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

//        Firebase.auth(FirebaseApp.initializeApp(this)!!).firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        binding.txtSignIn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.txtSendOTP.setOnClickListener {
            if (!TextUtils.isEmpty(binding.edtPhone.text.trim().toString())) {
                Utility.showLoadingDialog(this, "Loading...", false)
                sendVerificationCode("+91" + binding.edtPhone.text.trim().toString());
            } else {
                Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendVerificationCode(phNo: String) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phNo)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                Utility.hideLoadingDialog()
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
                val intent = Intent(this@LoginActivity, VerifyOTPActivity::class.java)
                intent.putExtra("phno", "" + binding.edtPhone.text.trim().toString())
                intent.putExtra("verificationId", "" + verificationId)
                startActivity(intent)
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Utility.hideLoadingDialog()
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    binding.edtPhone.setText(code)


                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
//                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                Utility.hideLoadingDialog()
                // displaying error message with firebase exception.
                Toast.makeText(this@LoginActivity, "Verify OTP : " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
}