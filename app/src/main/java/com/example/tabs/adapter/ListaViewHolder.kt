package com.example.tabs.adapter
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabs.R
import com.example.tabs.models.Personaje
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ListaViewHolder (view : View  ) : RecyclerView.ViewHolder(view) {
    val context = view.context
    val tvItemNombre = view.findViewById<TextView>(R.id.tvItemNombre)
    val tvItemDescripcion = view.findViewById<TextView>(R.id.tvItemDescripcion)
    val ivItemFoto = view.findViewById<ImageView>(R.id.ivItemFoto)
    val cgTipo = view.findViewById<ChipGroup>(R.id.cgTipo)


    fun render(personaje : Personaje){
        tvItemNombre.text = personaje.nombre
        tvItemDescripcion.text  =personaje.descripcion
        if(personaje.principal == 1){
            generateChip("Principal")
        }
        if(personaje.secundario == 1){
            generateChip("Secundario")
        }
        if(personaje.extra == 1){
            generateChip("Extra")
        }
        Log.i("Cargando personaje",personaje.imagen)
        ivItemFoto.setImageURI(Uri.parse(personaje.imagen))
    }
    private fun generateChip(text : String  ) {

        val chip = Chip(cgTipo.context)
        chip.text = text
        cgTipo.addView(chip)
    }

}