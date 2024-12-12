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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ViewingInformation : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    var imageUriString: Uri? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var imageIV: ImageView
    private lateinit var nameTV: TextView
    private lateinit var surnameTV: TextView
    private lateinit var phoneNumberTV: TextView
    private lateinit var dateBirthTV: TextView
    private lateinit var beforeBirthdayTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_viewing_information)

        toolbarTB = findViewById(R.id.toolbarInfo)
        imageIV = findViewById(R.id.imageInfoIV)
        nameTV = findViewById(R.id.nameTV)
        surnameTV = findViewById(R.id.surnameTV)
        phoneNumberTV = findViewById(R.id.phoneNumberTV)
        dateBirthTV = findViewById(R.id.dateBirthTV)
        beforeBirthdayTV = findViewById(R.id.beforeBirthdayTV)

        setSupportActionBar(toolbarTB)
        setFields()

    }

    fun setFields() {
        imageUriString = intent.getStringExtra("image")?.toUri()
        imageIV.setImageURI(imageUriString)
        nameTV.text = intent.getStringExtra("name") ?: ""
        surnameTV.text = intent.getStringExtra("surname") ?: ""
        phoneNumberTV.text = intent.getStringExtra("phone") ?: ""
        dateBirthTV.text = intent.getStringExtra("date") ?: ""
        beforeBirthdayTV.text = getTimeUntilBirthday(intent.getStringExtra("date").toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {R.id.exit -> finishAffinity() }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUriString = data?.data
            imageIV.setImageURI(imageUriString)
        }
    }

    fun getTimeUntilBirthday(birthdayString: String): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val birthday = dateFormat.parse(birthdayString)
        val currentDate = Calendar.getInstance()
        val birthdayDate = Calendar.getInstance()

        birthdayDate.time = birthday

        birthdayDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR))

        if (birthdayDate.before(currentDate)) {
            birthdayDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR) + 1)
        }

        val monthsRemaining = birthdayDate.get(Calendar.MONTH) - currentDate.get(Calendar.MONTH)
        val daysRemaining = birthdayDate.get(Calendar.DAY_OF_MONTH) - currentDate.get(Calendar.DAY_OF_MONTH)

        val adjustedMonths = if (daysRemaining < 0) monthsRemaining - 1 else monthsRemaining
        val adjustedDays = if (daysRemaining < 0) {
            val daysInLastMonth = birthdayDate.getActualMaximum(Calendar.DAY_OF_MONTH)
            daysInLastMonth + daysRemaining
        } else {
            daysRemaining
        }

        return "до дня рождения осталось $adjustedMonths месяцев и $adjustedDays дней"
    }

}