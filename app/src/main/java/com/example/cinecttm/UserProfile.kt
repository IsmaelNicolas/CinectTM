package com.example.cinecttm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.cinecttm.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson

class UserProfile : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth= FirebaseAuth.getInstance()

        val gson = Gson()
        try {
            val username =  intent.getStringExtra("user")
            val joined = intent.getStringExtra("joined")
            //val age = intent.getStringExtra("user")
            findViewById<TextView>(R.id.txtName).text = username
        }catch (e: Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
        }

        //

        findViewById<Button>(R.id.btnLogOut).setOnClickListener{
            auth.signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.revokeAccess()

            val prefs = getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}