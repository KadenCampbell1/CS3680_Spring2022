package com.example.p3_project_kc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RVAdapter
    var book_list = ItemList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        book_list.createList()
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter = RVAdapter()
        recyclerView.adapter = rvAdapter
    }

    inner class RVAdapter : RecyclerView.Adapter<RVAdapter.ItemHolder>() {
        var listItems = book_list

        inner class ItemHolder(itemView: View) : ViewHolder(itemView) {
            var itemTextView: TextView

            init {
                itemTextView = itemView as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return ItemHolder(v)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val b_item = listItems.getPostition(position)
            val item = b_item.getName()
            holder.itemTextView.text = item
        }

        override fun getItemCount(): Int {
            return listItems.getListSize()
        }
    }
}

class ItemList(){
    val items_list: MutableList<Book> = mutableListOf()

    fun addBook(b_name: String, b_author: String, b_genre: String){
        items_list.add(Book(b_name, b_author, b_genre))
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

class Book(book_name: String, book_author: String, book_genre: String){
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