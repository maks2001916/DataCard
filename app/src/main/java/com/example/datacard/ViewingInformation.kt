package com.example.datacard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri

class ViewingInformation : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    var imageUriString: Uri? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var imageIV: ImageView
    private lateinit var nameTV: TextView
    private lateinit var surnameTV: TextView
    private lateinit var dateBirthTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_viewing_information)

        toolbarTB = findViewById(R.id.toolbarInfo)
        imageIV = findViewById(R.id.imageInfoIV)
        nameTV = findViewById(R.id.nameTV)
        surnameTV = findViewById(R.id.surnameTV)
        dateBirthTV = findViewById(R.id.dateBirthTV)

        setSupportActionBar(toolbarTB)
        setFields()

    }

    fun setFields() {
        imageUriString = intent.getStringExtra("image")?.toUri()
        imageIV.setImageURI(imageUriString)
        nameTV.text = intent.getStringExtra("name") ?: ""
        surnameTV.text = intent.getStringExtra("surname") ?: ""
        dateBirthTV.text = intent.getStringExtra("date") ?: ""
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
            imageUriString = data?.data
            imageIV.setImageURI(imageUriString)
        }
    }

}