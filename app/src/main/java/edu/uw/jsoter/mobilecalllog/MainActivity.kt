package edu.uw.jsoter.mobilecalllog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.widget.ListView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var PHONE_CALL_REQUEST_CODE = 1
    private lateinit var adapter: PhoneCallAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            getPhoneCalls()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), PHONE_CALL_REQUEST_CODE)
        }
    }

    private fun getPhoneCalls() {
        val allCalls = ArrayList<PhoneCall>()
        val managedCursor = this.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, "DATE DESC")
        val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val direction = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        var counter = 0
        while (counter < 50 && managedCursor.moveToNext()) {
            var phNumber = managedCursor.getString(number)
            val callDate = managedCursor.getString(date)
            val callType = managedCursor.getString(direction)
            var callDirection: String = if(callType.toInt() == CallLog.Calls.OUTGOING_TYPE) "OUTGOING" else "INCOMING"
            val sdf = SimpleDateFormat("h:mm a M/d/yy", Locale.getDefault())
            val formattedDate = sdf.format(callDate.toLong())
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                phNumber = PhoneNumberUtils.formatNumber(phNumber, "US")
            }
            allCalls.add(PhoneCall(phNumber, formattedDate, callDirection))
            counter++
        }
        adapter = PhoneCallAdapter(this, R.layout.phonecall_list_item, allCalls)
        val listView = findViewById<ListView>(R.id.phonecall_list_view)
        listView.adapter = adapter
        managedCursor.close()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PHONE_CALL_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneCalls()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
