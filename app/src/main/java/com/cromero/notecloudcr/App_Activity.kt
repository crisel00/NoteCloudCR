package com.cromero.notecloudcr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_app_.*

class App_Activity : AppCompatActivity() {

    var notes: ArrayList<String> = ArrayList()
    var raiz = FirebaseDatabase.getInstance().getReference()
    var uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var useri : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_)

        rec_noteList.layoutManager = LinearLayoutManager(this)

        cargarNotas(this, uid)

        rec_noteList.adapter = NoteAdapter(applicationContext,User())

        fab_addNota.setOnClickListener {
            var intent = Intent(this, addNota::class.java)
            if (useri != null){
                intent.putExtra("user", useri)
                startActivity(intent)
            }
        }

    }

    fun cargarNotas(context :Context, uid : String){

        raiz.child("User").child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                var user : User
                if(snap.exists()){
                    user = snap.getValue(User::class.java)!!
                } else {
                    user = User(uid)

                    var nota :Note = Note("Bienvenida 1", "Wilcomen")

                    user.addNota(nota)

                    nota = Note("WUUUU", "MATAME")
                    user.addNota(nota)

                    nota = Note("qqwerqwerq", "MATAME")
                    user.addNota(nota)

                    nota = Note("YUUUS", "MATAME")
                    user.addNota(nota)

                    raiz.child("User").child(uid).setValue(user)
                }

                rec_noteList.adapter = NoteAdapter(context,user)
                useri = user

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}