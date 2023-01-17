package com.example.cinecttm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

class CreateAccount : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        btnCreateAccount.setOnClickListener{
            val txtName = findViewById<TextView>(R.id.txtNameAdd)
            val txtpass = findViewById<TextView>(R.id.txtPassAdd)
            val txtemail = findViewById<TextView>(R.id.txtemailAdd)

            if (txtName.text.toString() != "" && txtpass.text.toString() != "" && txtemail.text.toString() != ""){
                createPopMenu(btnCreateAccount)
            }   else{
                Toast.makeText(this, "Complete all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun createPopMenu(btnCreateAccount:Button){
        val popupMenu: PopupMenu = PopupMenu(this,btnCreateAccount)
        popupMenu.menuInflater.inflate(R.menu.context_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.menu_item2 ->{
                    Toast.makeText(this@CreateAccount, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Home::class.java)
                    startActivity(intent)
                }
                R.id.menu_item1 ->

                    Toast.makeText(this@CreateAccount, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
            }
            true
        })
        popupMenu.show()
    }

}