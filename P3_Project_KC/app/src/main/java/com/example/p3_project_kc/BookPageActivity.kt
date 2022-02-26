package com.example.p3_project_kc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class BookPageActivity : AppCompatActivity() {
    lateinit var authorTv: TextView
    lateinit var nameTv: TextView
    lateinit var genreTv: TextView
    lateinit var backBtn: Button
    var book_holder: Book? = Book("", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = getIntent()
        val book: Book? = intent.getSerializableExtra("obj") as Book?
        book_holder = book
        var author = book?.getAuthor()
        var name = book?.getName()
        var genre = book?.getGenre()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_page)

        authorTv = findViewById(R.id.author_tv)
        nameTv = findViewById(R.id.name_tv)
        genreTv = findViewById(R.id.genre_tv)
        backBtn = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {doDeleteBtn()}

        authorTv.text = "Author: $author"
        nameTv.text = "Title: $name"
        genreTv.text = "Genre: $genre"
    }

    fun doDeleteBtn(){
        finish()
    }
}