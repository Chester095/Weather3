package com.geekbrains.weather.view.activities

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.geekbrains.weather.R

class ContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        checkPermission()
        getContacts()
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                getContacts()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS) -> {
// диалог запроса разрешения
                AlertDialog.Builder(this).setTitle("Дай доступ")
                    .setMessage("очень нада")
                    .setPositiveButton("Дать доступ") { _, _ -> requestPermissions() }
                    .setNegativeButton("Не давать") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
            else -> {
                requestPermissions()
            }
        }
    }

    private fun requestPermissions() {
        TODO("Not yet implemented")
    }

    // стучимся в системный контент провайдер
    private fun getContacts() {
        //объект через который будем общаться с контент провайдером
        val contentResolver: ContentResolver = contentResolver
        val contactsList = findViewById<TextView>(R.id.contacts_list).apply { text = "" }
        // через него получаем курсор. Курсор нужен для получения данных по объектно и не всех сразу.
        val cursor: Cursor? = contentResolver.query(
            // формируем путь для запроса
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC" // пробел обязательно. ASC - по возрастанию
        )


        cursor?.let { cursor ->
            val columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            if (columnIndex >= 0) {
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor.getString(columnIndex)
                        contactsList.text = "${contactsList.text}$name\n"
                    }
                }
            }
            cursor?.close()
        }


    }
}