package edu.washington.vremaker.awty

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import android.widget.EditText


class NotifService: IntentService("NotifService") {

    val TAG = "NotifService"
    private lateinit var mHandler: Handler
    var runNotifs = false

    override fun onCreate() {
        Log.v(TAG, "Service started")

        mHandler = Handler()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "Service started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.v(TAG, "Handling Intent")
        runNotifs = true
        val number = intent!!.getStringExtra("phone")
        val interval = intent!!.getStringExtra("interval").toInt() * 60000
        val message = intent!!.getStringExtra("message")
        while (runNotifs) {
            mHandler.post {
                Toast.makeText(this@NotifService," " + number + ":" + message, Toast.LENGTH_LONG).show()
            }
            try {
                Thread.sleep(interval.toLong()) //sleep for 2 seconds
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }


    fun getPhone(phone: String): String {
        val newPhone = "(" + phone.substring(0,3) + ") " + phone.substring(3,6) + "-" + phone.substring(6)
        return newPhone
    }

    override fun onDestroy() {
      Log.v(TAG, "Service finished")
        runNotifs = false
        super.onDestroy()
    }


}
