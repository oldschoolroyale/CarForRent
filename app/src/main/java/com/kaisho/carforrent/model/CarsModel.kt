package com.kaisho.carforrent.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CarsModel(
    var name: String? = null,
    var image: String? = null,
    var description: String? = null,
    var price: Int? = null,
    var airCondition: String? = null,
    var manual: String? = null,
    var passengers: String? = null,
    var gasStation: String? = null,
    var totalPrice: String? = null
) : Comparable<CarsModel>, Parcelable {






    override fun compareTo(other: CarsModel): Int {
        return this.price!! - other.price!!
    }

    @IgnoredOnParcel
    var highToLow: Comparator<CarsModel> =
        Comparator { o1, o2 ->
            return@Comparator o2.price!!.compareTo(o1.price!!)
        }
    @IgnoredOnParcel
    var lowToHigh: Comparator<CarsModel> =
        Comparator { o1, o2 ->
            return@Comparator o1.price!!.compareTo(o2.price!!)
        }
}