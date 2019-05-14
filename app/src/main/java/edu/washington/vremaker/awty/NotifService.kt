package edu.washington.vremaker.awty

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast


class NotifService: IntentService("NotifService") {

    val TAG = "NotifService"
    private lateinit var mHandler: Handler
    var count = 0

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
        count = 1
        while (count <= 10) {
            Log.v(TAG, "Count: $count")
            mHandler.post {
                Toast.makeText(this@NotifService, "Count: $count", Toast.LENGTH_SHORT).show()
                Log.v(TAG, "" + count)
            }

            if (count == 5) {
                stopSelf()
            }

            try {
                Thread.sleep(2000) //sleep for 2 seconds
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }

            count++
        }
    }


    fun getPhone(phone: String): String {
        val newPhone = "(" + phone.substring(0,3) + ") " + phone.substring(3,6) + "-" + phone.substring(6)
        return newPhone
    }

    override fun onDestroy() {
      Log.v(TAG, "Service finished")
        count = 11 //stop counting

        super.onDestroy()
    }
}
