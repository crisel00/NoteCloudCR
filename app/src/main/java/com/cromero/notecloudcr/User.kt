package com.cromero.notecloudcr

import java.io.Serializable

data class User(var uid: String? = null, var notas : ArrayList<Note> = ArrayList<Note>()) : Serializable{

    fun addNota(nota:Note){
        notas.add(nota)
    }
}