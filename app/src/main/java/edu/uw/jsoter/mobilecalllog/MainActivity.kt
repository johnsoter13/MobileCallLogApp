package edu.uw.jsoter.mobilecalllog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var Phone_Call_REQUEST_CODE = 1
    private lateinit var mAdapter: PhoneCallRecyclerAdapter
    private var phoneCalls = listOf<PhoneCall>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            getPhoneCalls()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), Phone_Call_REQUEST_CODE)
        }
    }

    private fun getPhoneCalls() {
        
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Phone_Call_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
