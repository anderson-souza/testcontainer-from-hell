package com.aps.integrationtestcontainer

import jakarta.persistence.*

@Entity
@Table(name = "car")
open class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "name")
    open var name: String? = null

    @Column(name = "model")
    open var model: String? = null

    constructor(name: String?, model: String?) {
        this.name = name
        this.model = model
    }

    override fun toString(): String {
        return "Car(id=$id, name=$name, model=$model)"
    }


}