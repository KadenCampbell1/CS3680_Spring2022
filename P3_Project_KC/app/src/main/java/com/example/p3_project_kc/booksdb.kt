package com.example.p3_project_kc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log


private val SQL_CREATE_BOOK =
"""
CREATE TABLE IF NOT EXISTS "Books" (
"id" INTEGER NOT NULL,
"title" TEXT NOT NULL,
"author" TEXT NOT NULL,
"genre" TEXT NOT NULL,
PRIMARY KEY("id" AUTOINCREMENT)
)
"""

val SQL_INSERT_ALL =
"""
INSERT INTO "Books" ("title","author","genre") VALUES ('Here, There Be Dragons','James A. Owen','Fantasy');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Three-Body Problem','Liu Cixin','Sci-Fi');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Fifth Season','N. K. Jemisin','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Only Good Indians','Stephen Graham Jones','Horror');
INSERT INTO "Books" ("title","author","genre") VALUES ('Ready Player One','Ernest Cline','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Bat','Jo Nesbo','Mystery');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Eyes of the Dragon','Stephen King','Fantasy');
INSERT INTO "Books" ("title","author","genre") VALUES ('Do Androids Dream of Electric Sheep','Philip K. Dick','Sci-Fi');
INSERT INTO "Books" ("title","author","genre") VALUES ('Dune','Frank Herbert','Sci-Fi');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Fountainhead','Ayn Rand','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('Frankenstein','Mary Shelley','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('Warm Bodies','Isaac Marion','Romance');
INSERT INTO "Books" ("title","author","genre") VALUES ('Shutter Island','Dennis Lehane','Mystery');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Umbrella Conspiracy','S. D. Perry','Horror');
INSERT INTO "Books" ("title","author","genre") VALUES ('Dracula','Bram Stoker','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('The Perks of Being a Wallflower','Stephen Chbosky','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ('Annihilation','Jeff VanderMeer','Fiction');
INSERT INTO "Books" ("title","author","genre") VALUES ("Harry Potter and the Sorcerer's Stone",'J. K. Rowling','Fiction');
"""

private val SQL_DELETE_BOOK = "DROP TABLE IF EXISTS " + "Books"

class BooksDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_BOOK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_BOOK)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun reset(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_BOOK)
        onCreate(db)
    }

    fun insertRecords(db: SQLiteDatabase) {
        val inserts = SQL_INSERT_ALL.trim().lines()
        for (insert in inserts) {
            db.execSQL(insert)
        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "books.db"
    }
}
