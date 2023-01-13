package com.example.cinecttm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class UserProfile : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CinectTM_AppBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth= FirebaseAuth.getInstance()

        val name = intent.getStringExtra("name")

        findViewById<TextView>(R.id.txtName).text = name

        findViewById<Button>(R.id.btnLogOut).setOnClickListener{
            auth.signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.revokeAccess()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}