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

        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter = RVAdapter()
        recyclerView.adapter = rvAdapter

        val dbHelper = BooksDbHelper(this)
        db = dbHelper.writableDatabase
        val sel = db.rawQuery("SELECT * FROM Books", null)
        if(sel.count <= 0){
            dbHelper.reset(db)
            dbHelper.insertRecords(db)
        }
        else{
            dbHelper.onCreate(db)
        }

        book_list.updateList(db)

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

        db.execSQL("INSERT INTO \"Books\" (\"title\",\"author\",\"genre\") VALUES (\"${n.toString()}\",\"${a.toString()}\",\"${g.toString()}\");")
        book_list.updateList(db)
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
        val selection = arrayOf<String>(selected_book.getName().toString())
        db.execSQL("DELETE FROM Books WHERE title = ?", selection)
        book_list.updateList(db)

        rvAdapter!!.notifyDataSetChanged()
        infoBtn.isClickable = false
        deleteBtn.isClickable = false
        selectionTv.text = ""
    }

    fun doQueryBtn(){
        var a = queryPlainText.text
        book_list.emptyList()

        if(a.toString() == "All"){
            book_list.updateList(db)
        }
        else{
            val selectionArgs = arrayOf<String>(a.toString())
            val querySQL = "SELECT * FROM Books WHERE genre = ?;"
            val cursor = db.rawQuery(querySQL, selectionArgs)
            with(cursor) {
                while (moveToNext()) {
                    val dbtitle = cursor.getString(1)
                    val dbauthor = cursor.getString(2)
                    val dbgenre = cursor.getString(3)
                    book_list.addBook(dbtitle, dbauthor, dbgenre)
                }
            }
        }

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
    var items_list: MutableList<Book> = mutableListOf()

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

    fun emptyList(){
        items_list = mutableListOf()
    }

    fun updateList(db: SQLiteDatabase){
        items_list = mutableListOf()

        val cursor_sel = db.rawQuery("SELECT * FROM Books", null)
        with(cursor_sel) {
            while (moveToNext()) {
                val sel_title = cursor_sel.getString(1)
                val sel_author = cursor_sel.getString(2)
                val sel_genre = cursor_sel.getString(3)
                items_list.add(Book(sel_title, sel_author, sel_genre))
            }
        }
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
