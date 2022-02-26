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
//    var list_holder: ItemList? = ItemList()
//    var b_obj = intent.getSerializableExtra("obj")
//    var b_list = intent.getSerializableExtra("bookList")

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = getIntent()
        val book: Book? = intent.getSerializableExtra("obj") as Book?
//        val b_list: ItemList? = intent.getSerializableExtra("bookList") as ItemList?
        book_holder = book
//        list_holder = b_list
//        var b_obj = intent.getSerializable("obj") as Book?
//        var b_list = intent.getSerializable("bookList")
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
//        list_holder?.popBook(book_holder)
//        val intent = Intent(this@BookPageActivity,MainActivity::class.java)
//        intent.putExtra("delete", true)
        finish()
    }
}