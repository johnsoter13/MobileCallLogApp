package edu.uw.jsoter.mobilecalllog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.phonecall_list_content.view.*

class PhoneCallRecyclerAdapter(private val parentActivity: MainActivity,
                                  private val values: List<PhoneCall>) :
    RecyclerView.Adapter<PhoneCallRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneCallRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.phonecall_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.phoneNumberView.text = item.phoneNumber

        with(holder.itemView) {
            tag = item
        }
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val phoneNumberView: TextView = view.phoneNumber
    }
}