package com.example.datacard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {


    private val GALLERY_REQUEST = 302
    var photoUri: Uri? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var imageIV: ImageView
    private lateinit var nameET: EditText
    private lateinit var surnameET: EditText
    private lateinit var dateBirthET: EditText
    private lateinit var numberPhoneET: EditText
    private lateinit var saveBTN: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toolbarTB = findViewById(R.id.toolbarMain)
        imageIV = findViewById(R.id.editImageIV)
        nameET = findViewById(R.id.nameET)
        surnameET = findViewById(R.id.surnameET)
        dateBirthET = findViewById(R.id.dateBirthET)
        numberPhoneET = findViewById(R.id.phoneNumberET)
        saveBTN = findViewById(R.id.saveBTN)

        setSupportActionBar(toolbarTB)

        imageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            if (imageIV.toString().isNotEmpty() &&
                nameET.text.toString().isNotEmpty() &&
                surnameET.text.toString().isNotEmpty() &&
                numberPhoneET.text.toString().isNotEmpty() &&
                dateBirthET.text.toString().isNotEmpty()) {
                if (isValidDate(dateBirthET.text.toString())) {
                    val infoIntent = Intent(this, ViewingInformation::class.java)
                    photoUri?.let { infoIntent.putExtra("image", it.toString()) }
                    infoIntent.putExtra("name", nameET.text.toString())
                    infoIntent.putExtra("surname", surnameET.text.toString())
                    infoIntent.putExtra("phone", numberPhoneET.text.toString())
                    infoIntent.putExtra("date", dateBirthET.text.toString())
                    startActivity(infoIntent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.enterDate),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.notAllFieldsAreFilledIn),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateFormat.isLenient = false // Этот параметр запрещает нестрогую проверку
        return try {
            dateFormat.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {R.id.exit -> finish() }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            photoUri = data?.data
            imageIV.setImageURI(photoUri)
        }
    }
}