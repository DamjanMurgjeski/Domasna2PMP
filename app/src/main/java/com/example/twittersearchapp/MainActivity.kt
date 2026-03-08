package com.example.twittersearchapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // Листа за приказ во RecyclerView
    private val displayList = mutableListOf<String>()
    // Мапа за чување на речникот од текстуалниот фајл
    private val dictionary = mutableMapOf<String, String>()
    private lateinit var adapter: TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Вчитај го речникот од assets/dictionary.txt
        loadDictionaryFromFile()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Го користиме постоечкиот TagAdapter за приказ на резултатите
        adapter = TagAdapter(displayList)
        recyclerView.adapter = adapter

        val btnSave = findViewById<Button>(R.id.btnSave) // Ова сега е "Search" копче
        val etQuery = findViewById<EditText>(R.id.etQuery) // Поле за внес на збор
        val etTag = findViewById<EditText>(R.id.etTag) // Ова поле можеш да го сокриеш во XML

        btnSave.setOnClickListener {
            val inputWord = etQuery.text.toString().trim().lowercase()

            if (inputWord.isNotEmpty()) {
                // Пребарување: прво провери како клуч (English), па како вредност (Macedonian)
                val translation = dictionary[inputWord] ?:
                dictionary.entries.find { it.value == inputWord }?.key

                if (translation != null) {
                    // Ако е најден, додај го во листата за приказ
                    val resultString = "$inputWord = $translation"
                    displayList.add(0, resultString)
                    adapter.notifyItemInserted(0)
                    recyclerView.scrollToPosition(0)

                    etQuery.text.clear()
                    etTag.text.clear()
                } else {
                    Toast.makeText(this, "Зборот не е пронајден во речникот", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Внесете збор за пребарување", Toast.LENGTH_SHORT).show()
            }
        }

        val btnClear = findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener {
            displayList.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadDictionaryFromFile() {
        try {
            // Читање на dictionary.txt од assets папката
            assets.open("dictionary.txt").bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split(",")
                    if (parts.size == 2) {
                        // Чистење на празни места и додавање во мапата
                        val eng = parts[0].trim().lowercase()
                        val mkd = parts[1].trim().lowercase()
                        dictionary[eng] = mkd
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Грешка при вчитување на речникот", Toast.LENGTH_LONG).show()
        }
    }
}