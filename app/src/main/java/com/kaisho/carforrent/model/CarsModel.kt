package com.kaisho.carforrent.model

class CarsModel : Comparable<CarsModel> {
    var name: String? = null
    var image: String? = null
    var description: String? = null
    var price: Int? = null

    constructor()
    constructor(name: String?, image: String?, description: String?, price: Int?) {
        this.name = name
        this.image = image
        this.description = description
        this.price = price
    }

    override fun compareTo(other: CarsModel): Int {
        return this.price!! - other.price!!
    }

    var highToLow: Comparator<CarsModel> =
        Comparator { o1, o2 ->
            return@Comparator o2.price!!.compareTo(o1.price!!)
        }
    var lowToHigh: Comparator<CarsModel> =
        Comparator { o1, o2 ->
            return@Comparator o1.price!!.compareTo(o2.price!!)
        }
}