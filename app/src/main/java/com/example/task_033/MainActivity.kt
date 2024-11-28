package com.example.task_033

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar
    var db: ContactDatabase? = null
    private lateinit var lastnameET: EditText
    private lateinit var telephoneET: EditText
    private lateinit var textView: TextView
    private lateinit var saveBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setSupportActionBar(toolbarMain)
        title = "Контакты."

        db = ContactDatabase.getDatabase(this)
        asyncReadDatabase(db!!)
    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        lastnameET = findViewById(R.id.lastnameET)
        telephoneET = findViewById(R.id.telephoneET)
        textView = findViewById(R.id.textView)
        saveBTN = findViewById(R.id.saveBTN)
    }

    override fun onResume() {
        super.onResume()
        saveBTN.setOnClickListener{
            textView.text = ""
            val contact = Contact(lastnameET.text.toString(), telephoneET.text.toString())
            lastnameET.text.clear()
            telephoneET.text.clear()
            addContact(db!!, contact)
        }
    }

    private fun addContact(db: ContactDatabase, contact: Contact) = CoroutineScope(Dispatchers.IO).async {
        db.getContactDao().insert(contact)
        readDatabase(db!!)
    }

    private fun asyncReadDatabase(db: ContactDatabase) = CoroutineScope(Dispatchers.IO).async{
        readDatabase(db!!)
    }

    private fun readDatabase(db: ContactDatabase) {
        val list = db.getContactDao().getAllContacts()
        list.forEach{i -> textView.append(i.lastname + " " + i.telephone + "\n")}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain->{
                moveTaskToBack(true);
                exitProcess(-1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}