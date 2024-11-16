package com.example.tabs.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabs.R
import com.example.tabs.adapter.ListaAdapter
import com.example.tabs.helpers.DataBasePersonaje
import com.example.tabs.models.Personaje
import kotlinx.coroutines.launch

class ConsultaFragment : Fragment() {
    private var listaPersonaje = mutableListOf<Personaje>()
    private lateinit var rvList: RecyclerView
    private lateinit var adapter: ListaAdapter
    private var dataBasePersonaje: DataBasePersonaje? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_consulta, container, false)
        initComponent(view)
        initListener()
        return view
    }

    override fun onResume() {
        super.onResume()
        initListener()
    }

    private fun initComponent(view: View) {
        dataBasePersonaje = DataBasePersonaje(view.context)
        rvList = view.findViewById(R.id.rvListPersonaje)
        adapter = ListaAdapter(listaPersonaje)
        rvList.layoutManager = LinearLayoutManager(view.context)
        rvList.adapter = adapter
    }

    private fun initListener() {
        Log.i("ConsultaFragment", "Iniciando la consulta de personajes")
        dataBasePersonaje?.let {
            val lp = it.selectAllPersonaje()

            if (lp.isNotEmpty()) {
                listaPersonaje.clear()
                listaPersonaje.addAll(lp)
                adapter.notifyDataSetChanged()
                Log.i("ConsultaFragment", "Personajes cargados correctamente.")
            } else {
                Log.i("ConsultaFragment", "No se encontraron personajes.")
                showNoDataMessage()
            }
        } ?: run {
            Log.e("ConsultaFragment", "Error al acceder a la base de datos.")
            showDatabaseErrorMessage()
        }
    }

    private fun showNoDataMessage() {
        Log.i("ConsultaFragment", "No hay personajes disponibles.")
    }

    private fun showDatabaseErrorMessage() {
        Log.e("ConsultaFragment", "Hubo un error al acceder a la base de datos.")
    }
}
