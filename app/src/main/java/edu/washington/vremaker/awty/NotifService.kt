package edu.washington.vremaker.awty

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import android.net.Uri
import android.R
import android.widget.LinearLayout
import android.telephony.SmsManager
import android.view.Gravity



class NotifService: IntentService("NotifService") {

    companion object {
        val REQUEST_SMS_SEND_PERMISSION = 1234
    }

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
        val number = intent!!.getStringExtra("phone").toString()
        val uri = Uri.parse("smsto:$number")
        val interval = intent!!.getStringExtra("interval").toInt() * 60000
        val message = intent!!.getStringExtra("message").toString()
        while (runNotifs) {
            mHandler.post {
                val smgr = SmsManager.getDefault()
                smgr.sendTextMessage(number, null, message, null, null)
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
