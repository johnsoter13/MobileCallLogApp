package edu.uw.jsoter.mobilecalllog

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.CallLog
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.telephony.PhoneNumberUtils
import android.widget.ListView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    // instance variables
    private var PHONE_CALL_REQUEST_CODE = 1
    private lateinit var adapter: PhoneCallAdapter
    private val TAG = "MainActivity"
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var currentActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentActivity = this
        getPhoneCalls()
        pullToRefresh()
    }

    // This function initializes the pull to refresh feature to update the list of calls on the screen
    private fun pullToRefresh() {
        val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_container)
        handler = Handler()
        swipeLayout.setOnRefreshListener {
            runnable = Runnable {
                // check for permission
                val permissionCheck =
                    ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_CALL_LOG)
                // if granted, refresh call list
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    getPhoneCalls()
                } else {
                    ActivityCompat.requestPermissions(
                        currentActivity,
                        arrayOf(Manifest.permission.READ_CALL_LOG),
                        PHONE_CALL_REQUEST_CODE
                    )
                }
                swipeLayout.isRefreshing = false
            }
            handler.postDelayed(runnable, 2000)
        }
    }

    // This function populates a list view with calls from the user's phone
    private fun getPhoneCalls() {
        // check for permission
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
        // if granted, get a list of phone calls
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val allCalls = ArrayList<PhoneCall>()
            // create a cursor while sorting by Date descending so that we show the most recent calls at the top of the list
            val managedCursor = this.contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, "DATE DESC")
            val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
            val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
            val direction = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
            var counter = 0
            // only get the most recent 50 results
            while (counter < 50 && managedCursor.moveToNext()) {
                var phNumber = managedCursor.getString(number)
                val callDate = managedCursor.getString(date)
                val callType = managedCursor.getString(direction)
                // check to see if call is Outgoing or Incoming
                var callDirection: String = if (callType.toInt() == CallLog.Calls.OUTGOING_TYPE) "OUTGOING" else "INCOMING"
                // format Date into a readable string
                val formattedDate = SimpleDateFormat("h:mm a M/d/yy", Locale.getDefault()).format(callDate.toLong())
                // check to see if SDK is greater than Nougat to allow us to format phone number
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    phNumber = PhoneNumberUtils.formatNumber(phNumber, "US")
                }
                // add PhoneCall to list and increment counter
                allCalls.add(PhoneCall(phNumber, formattedDate, callDirection))
                counter++
            }
            // Send list to adapter function
            setUpAdapter(allCalls)
            managedCursor.close()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), PHONE_CALL_REQUEST_CODE)
        }

    }

    // This function takes in a list of PhoneCalls and uses an adapter to list the phone calls on the screen
    private fun setUpAdapter(calls: ArrayList<PhoneCall>) {
        adapter = PhoneCallAdapter(this, R.layout.phonecall_list_item, calls)
        val listView = findViewById<ListView>(R.id.phonecall_list_view)
        listView.adapter = adapter
    }

    // This function handles the results of requesting permissions from the user
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            // when request is a to read phone call logs
            PHONE_CALL_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneCalls()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
