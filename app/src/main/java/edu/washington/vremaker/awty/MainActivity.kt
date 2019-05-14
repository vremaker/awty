package edu.washington.vremaker.awty


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.EditText
import android.widget.Button

class MainActivity : AppCompatActivity(), ServiceConnection {
    private lateinit var nService: NotifService
    private var nServiceBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val interval = findViewById<EditText>(R.id.length)
        val msg = findViewById<EditText>(R.id.message)
        val phone = findViewById<EditText>(R.id.phone)
        val button = findViewById<Button>(R.id.start)
        button.setOnClickListener(){
            if(button.text.equals("START")) {
                val number = phone.getText().toString()

                if(msg.length() > 0 && number != null && number.length === 10 && interval.length() > 0) {
                    button.text = "STOP"
                    val intent = Intent(this@MainActivity, NotifService::class.java)
                    startService(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You have not filled in all of the fields correctly. " +
                            "please try again. Reminder, the phone number is 10 digits with in () or -", Toast.LENGTH_LONG).show()
                }
            } else { // if button = STOP
                button.text = "START"
                stopService(Intent(this@MainActivity, NotifService::class.java))
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e("O", "service disconnected")
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        Log.e("MainActivity", "service connected")

    }

}