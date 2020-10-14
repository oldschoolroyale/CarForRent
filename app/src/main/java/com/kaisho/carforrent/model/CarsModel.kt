package com.kaisho.carforrent.model

class CarsModel {
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
}