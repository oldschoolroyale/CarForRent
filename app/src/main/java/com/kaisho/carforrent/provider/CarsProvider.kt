package com.kaisho.carforrent.provider

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kaisho.carforrent.model.CarsModel
import com.kaisho.carforrent.presenter.CarsPresenter

class CarsProvider(val presenter: CarsPresenter) {
    fun parse(){
        val loadList: ArrayList<CarsModel> = ArrayList()
        val reference = FirebaseDatabase.getInstance().reference.child("Cars")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                presenter.error(error = error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                loadList.clear()
                for (i in snapshot.children){
                    val model = i.getValue(CarsModel::class.java)
                    loadList.add(model!!)
                }
                presenter.tryToSetUp(loadList)
            }
        })
    }
}