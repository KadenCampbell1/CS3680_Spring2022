package com.example.p3_project_kc

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RVAdapter
    lateinit var authorPlainText: EditText
    lateinit var namePlainText: EditText
    lateinit var genrePlainText: EditText
    lateinit var queryPlainText: EditText
    lateinit var addBtn: Button
    lateinit var infoBtn: Button
    lateinit var deleteBtn: Button
    lateinit var queryBtn: Button
    lateinit var selectionTv: TextView
    lateinit var db: SQLiteDatabase
    var selected_book = Book("", "", "")
    var book_list = ItemList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        book_list.createList()
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter = RVAdapter()
        recyclerView.adapter = rvAdapter

        val dbHelper = BooksDbHelper(this)
        db = dbHelper.writableDatabase
        // see if dbHelper has objects otherwise do line 50 and 51
        dbHelper.reset(db)
        dbHelper.insertRecords(db)
        // val querySQL = "SELECT * FROM Maintenance;"
        // val selectionArgs = arrayOf<String>()
        val selectionArgs = arrayOf<String>("Fiction")
        val querySQL = "SELECT * FROM Books WHERE genre = ?;"
        val cursor = db.rawQuery(querySQL, selectionArgs)
        with(cursor) {
            Log.i("CS3680", "${cursor.getCount()} rows in query result")
            while (moveToNext()) {
                val dbtitle = cursor.getString(1)
                val dbauthor = cursor.getString(2)
                val dbgenre = cursor.getString(3)
                Log.i("CS3680", "$dbtitle $dbauthor $dbgenre")
            }
        }

        authorPlainText = findViewById(R.id.author_plain_text)
        namePlainText = findViewById(R.id.name_plain_text)
        genrePlainText = findViewById(R.id.genre_plain_text)
        queryPlainText = findViewById(R.id.query_plain_text)
        addBtn = findViewById(R.id.add_btn)
        addBtn.setOnClickListener { doAddBtn() }
        infoBtn = findViewById(R.id.info_btn)
        infoBtn.isClickable = false
        infoBtn.setOnClickListener { doInfoBtn() }
        deleteBtn = findViewById(R.id.delete_main_btn)
        deleteBtn.isClickable = false
        deleteBtn.setOnClickListener { doDeleteBtn() }
        queryBtn = findViewById(R.id.query_btn)
        queryBtn.setOnClickListener { doQueryBtn() }
        selectionTv = findViewById(R.id.selection_tv)

    }

    fun doAddBtn(){
        var a = authorPlainText.text
        var n = namePlainText.text
        var g = genrePlainText.text
        book_list.addBook("$n", "$a", "$g")
        rvAdapter!!.notifyDataSetChanged()
    }

    fun doInfoBtn(){
        val intent = Intent(this@MainActivity, BookPageActivity::class.java)
        intent.putExtra("obj", selected_book as Serializable)
        startActivity(intent)
        infoBtn.isClickable = false
        deleteBtn.isClickable = false
        selectionTv.text = ""
    }

    fun doDeleteBtn(){
        book_list.popBook(selected_book)
        rvAdapter!!.notifyDataSetChanged()
        infoBtn.isClickable = false
        deleteBtn.isClickable = false
        selectionTv.text = ""
    }

    fun doQueryBtn(){
        var a = queryPlainText.text
//        book_list.addBook("$n", "$a", "$g")
        rvAdapter!!.notifyDataSetChanged()
    }

    inner class RVAdapter : RecyclerView.Adapter<RVAdapter.ItemHolder>() {
        var listItems = book_list

        inner class ItemHolder(itemView: View) : ViewHolder(itemView) {
            var itemTextView: TextView
            var obj: Book = Book("","","")

            init {
                itemTextView = itemView as TextView
            }

            fun doClick(){
                selected_book = obj
                infoBtn.isClickable = true
                deleteBtn.isClickable = true
                selectionTv.text = "Selection: ${selected_book.getName()}"
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            val holder = ItemHolder(v)
            v.setOnClickListener {holder.doClick()}
            return holder
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val b_item = listItems.getPostition(position)
            val item = b_item.getName()
            holder.itemTextView.text = item
            holder.obj = b_item
        }

        override fun getItemCount(): Int {
            return listItems.getListSize()
        }
    }
}


class ItemList(): Serializable {
    val items_list: MutableList<Book> = mutableListOf()

    fun addBook(b_name: String, b_author: String, b_genre: String){
        items_list.add(Book(b_name, b_author, b_genre))
    }

    fun popBook(book: Book?){
        items_list.remove(book)
    }

    fun getListSize(): Int {
        return items_list.size
    }

    fun getPostition(pos: Int): Book {
        return items_list[pos]
    }

    fun createList(){
        items_list.add(Book("Here, There Be Dragons", "James A. Owen", "Fantasy"))
        items_list.add(Book("The Three-Body Problem", "Liu Cixin", "Sci-Fi"))
        items_list.add(Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Sci-Fi"))
        items_list.add(Book("The Fifth Season", "N. K. Jemisin", "Fiction"))
        items_list.add(Book("The Only Good Indians", "Stephen Graham Jones", "Horror"))
        items_list.add(Book("Ready Player One", "Ernest Cline", "Fiction"))
        items_list.add(Book("The Bat", "Jo Nesbo", "Mystery"))
        items_list.add(Book("The Eyes of the Dragon", "Stephen King", "Fantasy"))
        items_list.add(Book("Prey", "Michael Crichton", "Fiction"))
        items_list.add(Book("Do Androids Dream of Electric Sheep", "Philip K. Dick", "Sci-Fi"))
        items_list.add(Book("Dune", "Frank Herbert", "Sci-Fi"))
        items_list.add(Book("The Fountainhead", "Ayn Rand", "Fiction"))
        items_list.add(Book("Frankenstein", "Mary Shelley", "Fiction"))
        items_list.add(Book("Warm Bodies", "Isaac Marion", "Romance"))
        items_list.add(Book("Shutter Island", "Dennis Lehane", "Mystery"))
        items_list.add(Book("The Umbrella Conspiracy", "S. D. Perry", "Horror"))
        items_list.add(Book("Dracula", "Bram Stoker", "Fiction"))
        items_list.add(Book("The Perks of Being a Wallflower", "Stephen Chbosky", "Fiction"))
        items_list.add(Book("Annihilation", "Jeff VanderMeer", "Fiction"))
        items_list.add(Book("Harry Potter and the Sorcerer's Stone", "J. K. Rowling", "Fiction"))
    }
}

data class Book(val book_name: String, val book_author: String, val book_genre: String): Serializable {
    var b_name = book_name
    var b_author = book_author
    var b_genre = book_genre

    fun getName(): String {
        return b_name
    }

    fun getAuthor(): String {
        return b_author
    }

    fun getGenre(): String {
        return b_genre
    }
}