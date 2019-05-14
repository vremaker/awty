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
    private lateinit var mService: NotifService
    private var mServiceBound: Boolean = false

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
                    intent.putExtra("number", number.toString())
                    intent.putExtra("message", msg.text.toString())
                    intent.putExtra("interval", interval.text.toString())
                    startService(intent)
                    //add the interval service
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
        Log.e("O", "service connected")
    }

}