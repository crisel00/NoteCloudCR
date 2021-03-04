package com.cromero.notecloudcr

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_nota.view.*
import java.lang.IllegalArgumentException

class NoteAdapter (val context: Context, var user: User) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    var listaNotas: ArrayList<Note> = user.notas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_nota,parent,false))

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        if(holder is NotesViewHolder){

            holder.bind(listaNotas[position].titulo,position)

            holder.itemView.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Nota \" " + listaNotas[position] + "\"")
                builder.setMessage("Quieres Borrar esta nota?")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(context,
                            R.string.deleted, Toast.LENGTH_SHORT).show()
                    deleteNote(position)

                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->

                }
                builder.show()
            }



        } else {
            throw IllegalArgumentException("Viewholder erroneo")
        }
    }

    inner class NotesViewHolder(itemView: View):BaseViewHolder<String>(itemView){
        override fun bind(item: String, posicion: Int) {
            itemView.note_title.text = item
            itemView.tv_nota.text = listaNotas[posicion].nota
        }
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }

    fun deleteNote(position : Int) {

        user.notas.removeAt(position)
        FirebaseDatabase.getInstance().reference.child("User").child(user.uid!!).setValue(user)

    }

}