package com.example.cinecttm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.cinecttm.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

class Home : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth : FirebaseAuth
    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_CinectTM_AppBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createNotification()

        val toolbar: androidx.appcompat.widget.Toolbar =findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer =findViewById(R.id.drawer_layout)

        toogle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)

        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


    }

    private fun createNotification(){
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Crear un canal de notificación si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val largeIcon = BitmapFactory.decodeResource(this.resources, R.drawable.bitmap)

        val builder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.bitmap)
            .setContentTitle("Cinect te recomienda")
            .setContentText("Piratas del caribe")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(largeIcon)

        val notification = builder.build()
        notificationManager.notify(1, notification)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_one -> {
                Toast.makeText(this,"Home",Toast.LENGTH_LONG).show()
                val i = Intent(this,Home::class.java)
            }
            R.id.nav_item_two -> {
                val gson = Gson()
                val username = intent.getStringExtra("user")
                val joined = intent.getStringExtra("joined")
                val email = intent.getStringExtra("email")
                val intent = Intent(this,UserProfile::class.java)
                intent.putExtra("user",username)
                intent.putExtra("joined",joined)
                intent.putExtra("email",email)
                startActivity(intent)
                finish()
            }
            R.id.nav_item_three -> Toast.makeText(this,"Stadistics",Toast.LENGTH_LONG).show()
            R.id.nav_item_four -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                val googleSignInClient = GoogleSignIn.getClient(this, gso)
                googleSignInClient.revokeAccess()

                val prefs = getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("is_logged_in",false)
                editor.apply()

                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toogle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}