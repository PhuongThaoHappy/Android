package com.example.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var birthdayInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var agreeCheckbox: CheckBox
    private lateinit var selectButton: Button
    private lateinit var registerButton: Button
    private lateinit var calendarView: CalendarView

    private var selectedDate: String? = null

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        // Ánh xạ view
        firstName = findViewById(R.id.firstname)
        lastName = findViewById(R.id.lastname)
        birthdayInput = findViewById(R.id.birthday_input)
        addressInput = findViewById(R.id.address_input)
        emailInput = findViewById(R.id.email_input)
        genderGroup = findViewById(R.id.gender_group)
        agreeCheckbox = findViewById(R.id.agree_checkbox)
        selectButton = findViewById(R.id.select_button)
        registerButton = findViewById(R.id.register_button)
        calendarView = findViewById(R.id.calendarView)

        calendarView.visibility = CalendarView.GONE

        selectButton.setOnClickListener {
            if (calendarView.isGone)
                calendarView.visibility = CalendarView.VISIBLE
            else
                calendarView.visibility = CalendarView.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            birthdayInput.setText(selectedDate)
            calendarView.visibility = CalendarView.GONE
        }

        registerButton.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        var isValid = true

        resetBackground(firstName, lastName, birthdayInput, addressInput, emailInput)

        if (firstName.text.isNullOrBlank()) {
            firstName.setBackgroundColor("#FFCDD2".toColorInt())
            isValid = false
        }

        if (lastName.text.isNullOrBlank()) {
            lastName.setBackgroundColor("#FFCDD2".toColorInt())
            isValid = false
        }

        if (birthdayInput.text.isNullOrBlank()) {
            birthdayInput.setBackgroundColor("#FFCDD2".toColorInt())
            isValid = false
        }

        if (addressInput.text.isNullOrBlank()) {
            addressInput.setBackgroundColor("#FFCDD2".toColorInt())
            isValid = false
        }

        if (emailInput.text.isNullOrBlank()) {
            emailInput.setBackgroundColor("#FFCDD2".toColorInt())
            isValid = false
        }

        if (genderGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please choose gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!agreeCheckbox.isChecked) {
            Toast.makeText(this, "You have to agree with Term of Use", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!isValid) {
            Toast.makeText(this, "Please fill full information", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetBackground(vararg fields: EditText) {
        for (field in fields) {
            field.setBackgroundColor("#EEEEEE".toColorInt())
        }
    }
}
