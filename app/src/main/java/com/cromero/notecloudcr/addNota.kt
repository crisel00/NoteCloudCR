package com.cromero.notecloudcr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_nota.*

class addNota : AppCompatActivity() {

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nota)

        user = intent.getSerializableExtra("user") as User?

        add_bt_add.setOnClickListener{
            if(!add_tv_title.text.isEmpty() && !add_tv_note.text.isEmpty()){
                user?.addNota(Note(add_tv_title.text.toString(), add_tv_note.text.toString()))

                FirebaseDatabase.getInstance().reference.child("User").child(user!!.uid!!).setValue(user)

                finish()
            } else {
                Toast.makeText(this,"Campos vacios", Toast.LENGTH_SHORT)
            }

        }
    }
}