package com.kaisho.carforrent.model

class CarsModel {
    var name: String? = null
    var image: String? = null
    var description: String? = null
    var price: Double? = null

    constructor()
    constructor(name: String?, image: String?, description: String?, price: Double?) {
        this.name = name
        this.image = image
        this.description = description
        this.price = price
    }
}