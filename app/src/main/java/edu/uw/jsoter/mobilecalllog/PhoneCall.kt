package edu.uw.jsoter.mobilecalllog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A class representing a single PhoneCall.
 * @author John Soter
 */
@Parcelize
data class PhoneCall(
    val phoneNumber : String,
    val callDirection : Int,
    val callTime:Long
) : Parcelable
