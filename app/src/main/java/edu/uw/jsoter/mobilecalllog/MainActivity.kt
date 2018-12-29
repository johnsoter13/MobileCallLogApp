package edu.uw.jsoter.mobilecalllog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: PhoneCallRecyclerAdapter
    private var phoneCalls = listOf<PhoneCall>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}
