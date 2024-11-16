package com.example.tabs.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabs.R
import com.example.tabs.models.Personaje

class ListaAdapter  (private val personajeList : List<Personaje>) : RecyclerView.Adapter<ListaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ListaViewHolder(layoutInflater.inflate(R.layout.item_personaje, parent,false ))

    }

    override fun getItemCount(): Int  = personajeList.size

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        holder.render(personajeList[position])
    }
}