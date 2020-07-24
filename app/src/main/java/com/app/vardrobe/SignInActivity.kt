package com.app.vardrobe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 123
    private val TAG = "SignInActivity"
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getPreferences(Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val providers = arrayListOf(
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        if (FirebaseAuth.getInstance().currentUser == null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
//            startActivityForResult(
//                AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setAvailableProviders(providers)
//                    .build(),
//                RC_SIGN_IN
//            )
        } else {
            if (!preferences.getBoolean("profile_exits", false)) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        finish()
        startActivity(Intent(this, MainActivity::class.java))
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                Log.e(TAG, response.toString())
            }
        }
    }

}