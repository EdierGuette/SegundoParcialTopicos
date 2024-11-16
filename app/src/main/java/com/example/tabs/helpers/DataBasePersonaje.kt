package com.example.tabs.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tabs.models.Personaje

class DataBasePersonaje(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "personaje.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "personaje"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_PRINCIPAL = "principal"
        const val COLUMN_SECUNDARIO = "secundario"
        const val COLUMN_EXTRA = "extra"
        const val COLUMN_IMAGEN = "imagen"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = ("""CREATE TABLE $TABLE_NAME ( 
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_NOMBRE TEXT,
                $COLUMN_DESCRIPCION TEXT,
                $COLUMN_PRINCIPAL INTEGER,
                $COLUMN_SECUNDARIO INTEGER,
                $COLUMN_EXTRA INTEGER,
                $COLUMN_IMAGEN TEXT
                )""")
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_EXTRA INTEGER")
        }
    }

    fun insertPersonaje(personaje: Personaje): String {
        val db = this.writableDatabase
        val contentValue = ContentValues().apply {
            put(COLUMN_NOMBRE, personaje.nombre)
            put(COLUMN_DESCRIPCION, personaje.descripcion)
            put(COLUMN_PRINCIPAL, personaje.principal)
            put(COLUMN_SECUNDARIO, personaje.secundario)
            put(COLUMN_EXTRA, personaje.extra)
            put(COLUMN_IMAGEN, personaje.imagen)
        }

        val resultado = db.insert(TABLE_NAME, null, contentValue)

        return if (resultado == (-1).toLong()) {
            "Hubo un problema al guardar el personaje. Intenta nuevamente."
        } else {
            "Personaje guardado correctamente. ¡Éxito!"
        }
    }

    fun selectAllPersonaje(): MutableList<Personaje> {
        val namesList = mutableListOf<Personaje>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                val principal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRINCIPAL))
                val secundario = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SECUNDARIO))
                val extra = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXTRA))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN))
                val personaje = Personaje(
                    id,
                    nombre,
                    descripcion,
                    principal,
                    secundario,
                    extra,
                    imagen
                )
                namesList.add(personaje)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return namesList
    }

    fun updateName(id: String, name: String): String {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COLUMN_NOMBRE, name)

        val resultado = db.update(TABLE_NAME, contentValue, "id=?", arrayOf(id))

        return if (resultado > 0) {
            "El nombre del personaje fue actualizado correctamente."
        } else {
            "No se pudo actualizar el nombre. Verifica los datos y vuelve a intentar."
        }
    }

    fun deleteName(id: String): String {
        val db = this.writableDatabase
        val resultado = db.delete(TABLE_NAME, "id=?", arrayOf(id))

        return if (resultado > 0) {
            "El personaje fue eliminado correctamente."
        } else {
            "No se pudo eliminar el personaje. Intenta nuevamente."
        }
    }

    fun deleteAll() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "", arrayOf())
    }
}
