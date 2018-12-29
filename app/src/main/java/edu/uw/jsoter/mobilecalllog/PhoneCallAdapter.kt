package edu.uw.jsoter.mobilecalllog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

private data class ViewHolder constructor(
    val phoneNumberView: TextView,
    val callDateView: TextView,
    val callDirectionView: TextView
)

/*
 * This class is used to create custom adapters for handling PhoneCalls
 * author: John Soter
 */
class PhoneCallAdapter(context: Context, layout: Int, data: List<PhoneCall>) : ArrayAdapter<PhoneCall>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var retView = convertView

        // if null inflate the view
        if(retView == null) {
            retView = LayoutInflater.from(context).inflate(R.layout.phonecall_list_item, parent, false)
        }
        // grab ids
        val holder = ViewHolder(
            retView!!.findViewById(R.id.phoneNumber),
            retView!!.findViewById(R.id.callDate),
            retView!!.findViewById(R.id.callDirection)
        )
        val data: PhoneCall? = getItem(position)
        retView.tag = holder
        // add content to xml items
        holder.phoneNumberView.text = ("${data!!.phoneNumber}")
        holder.callDateView.text = ("${data!!.callDate}")
        holder.callDirectionView.text = ("${data!!.callDirection}")

        return retView
    }
}