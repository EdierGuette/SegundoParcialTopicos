package com.example.tabs.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tabs.R
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tabs.helpers.DataBasePersonaje
import com.example.tabs.models.Personaje
import kotlin.io.path.Path
import kotlin.io.path.outputStream

class FormularioFragment : Fragment() {
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var cbPrincipal: CheckBox
    private lateinit var cbSecundario: CheckBox
    private lateinit var cbExtra: CheckBox
    private lateinit var ivUpload: ImageView
    private lateinit var button: Button
    private lateinit var pickImage: ActivityResultLauncher<Intent>
    private var dataBasePersonaje: DataBasePersonaje? = null

    private var imagenUri = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)
        initComponent(view)
        initListener(view)
        initUI()
        return view
    }

    private fun initComponent(view: View) {
        etName = view.findViewById(R.id.etNombre)
        etDescription = view.findViewById(R.id.etDescripcion)
        cbPrincipal = view.findViewById(R.id.cbPrincipal)
        cbSecundario = view.findViewById(R.id.cbSecundario)
        cbExtra = view.findViewById(R.id.cbExtra)
        ivUpload = view.findViewById(R.id.ivFoto)
        button = view.findViewById(R.id.btnSave)
        dataBasePersonaje = DataBasePersonaje(view.context)

        pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let { uri ->
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val filePath = Path(requireContext().filesDir.absolutePath, "nombre_imagen.jpg")

                    inputStream?.use { input ->
                        filePath.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }

                    imagenUri = filePath.toString()

                    val bitmap = resizeImage(uri, 200, 200)
                    ivUpload.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun initListener(view: View) {
        ivUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        button.setOnClickListener {
            val nombre = etName.text.toString()
            val descripcion = etDescription.text.toString()
            val principal = if (cbPrincipal.isChecked) 1 else 0
            val secundario = if (cbSecundario.isChecked) 1 else 0
            val extra = if (cbExtra.isChecked) 1 else 0
            val imagen = imagenUri

            if (validation(nombre, imagen, principal, secundario, extra)) {
                dataBasePersonaje?.let {
                    val msg = it.insertPersonaje(
                        Personaje(
                            nombre = nombre, descripcion = descripcion, principal = principal,
                            secundario = secundario, extra = extra, imagen = imagen
                        )
                    )
                    viewMsg(msg)

                    clearForm()
                } ?: viewMsg("Ocurrió un error al acceder a la base de datos. Intenta nuevamente.")
            }
        }
    }

    private fun viewMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun validation(nombre: String, img: String, principal: Int, secundario: Int, extra: Int): Boolean {
        if (nombre.isEmpty()) {
            viewMsg("¡Atención! El nombre del personaje no puede estar vacío.")
            return false
        }

        if (img.isEmpty()) {
            viewMsg("Es necesario cargar una imagen para el personaje. Por favor, selecciona una.")
            return false
        }

        if (principal == 0 && secundario == 0 && extra == 0) {
            viewMsg("Debes seleccionar al menos un tipo de personaje (principal, secundario o extra).")
            return false
        }

        return true
    }


    private fun resizeImage(uri: Uri, width: Int, height: Int): Bitmap {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
    }

    private fun clearForm() {
        etName.setText("")
        etDescription.setText("")
        cbPrincipal.isChecked = false
        cbSecundario.isChecked = false
        cbExtra.isChecked = false
        ivUpload.setImageURI(null)
        imagenUri = ""
    }

    private fun initUI() {
    }
}
