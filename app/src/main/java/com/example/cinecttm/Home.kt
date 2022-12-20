package com.example.cinecttm

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth : FirebaseAuth
    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_CinectTM_AppBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_one -> Toast.makeText(this,"Home",Toast.LENGTH_LONG).show()
            R.id.nav_item_two -> {
                val intentt = Intent(this,UserProfile::class.java)
                intentt.putExtra("email",intent.getStringExtra("email"))
                intentt.putExtra("name",intent.getStringExtra("name"))
                startActivity(intentt)
            }
            R.id.nav_item_three -> Toast.makeText(this,"Stadistics",Toast.LENGTH_LONG).show()
            R.id.nav_item_four -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
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