package pe.edu.upc.bunker.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BunkerDBHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createSpaceTable = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_HEIGHT + " DECIMAL," +
                COLUMN_WIDTH + " DECIMAL," +
                COLUMN_AREA + " DECIMAL," +
                COLUMN_SPACE_TYPE + " INTEGER," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_LATITUDE + " DECIMAL," +
                COLUMN_LONGITUDE + " DECIMAL," +
                COLUMN_PHOTO1 + " TEXT," +
                COLUMN_PHOTO2 + " TEXT," +
                COLUMN_PHOTO3 + " TEXT," +
                COLUMN_PHOTO4 + " TEXT," +
                COLUMN_PHOTO5 + " TEXT," +
                COLUMN_PHOTO6 + " TEXT" +
                ")")
        db?.execSQL(createSpaceTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun startSpace() {
        deleteSpace()
        val values = ContentValues()
        values.put(COLUMN_HEIGHT, 0.0)
        values.put(COLUMN_WIDTH, 0.0)
        values.put(COLUMN_AREA, 0.0)
        values.put(COLUMN_SPACE_TYPE, 0)
        values.put(COLUMN_TITLE, "")
        values.put(COLUMN_DESCRIPTION, "")
        values.put(COLUMN_ADDRESS, "")
        values.put(COLUMN_LATITUDE, "")
        values.put(COLUMN_LONGITUDE, "")
        values.put(COLUMN_PHOTO1, "")
        values.put(COLUMN_PHOTO2, "")
        values.put(COLUMN_PHOTO3, "")
        values.put(COLUMN_PHOTO4, "")
        values.put(COLUMN_PHOTO5, "")
        values.put(COLUMN_PHOTO6, "")
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    private fun deleteSpace() {
        val cursor = getAllSpaces()
        cursor!!.moveToFirst()
        val db = this.writableDatabase
        var count = 1
        db.delete(TABLE_NAME, "id = $count", null)
        while (cursor.moveToNext()) {
            count++
            db.delete(TABLE_NAME, "id = $count", null)
        }
        cursor.close()
    }

    fun getAllSpaces(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun addFirstStep(
        height: Double,
        width: Double,
        area: Double,
        spaceType: Int,
        title: String,
        description: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_HEIGHT, height)
        values.put(COLUMN_WIDTH, width)
        values.put(COLUMN_AREA, area)
        values.put(COLUMN_SPACE_TYPE, spaceType)
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addSecondStep(address: String, latitude: Double, longitude: Double) {
        val values = ContentValues()
        values.put(COLUMN_ADDRESS, address)
        values.put(COLUMN_LATITUDE, latitude)
        values.put(COLUMN_LONGITUDE, longitude)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addFirstPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO1, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addSecondPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO2, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addThirdPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO3, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addFourthPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO4, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addFifthPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO5, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    fun addSixthPhoto(
        url: String
    ) {
        val values = ContentValues()
        values.put(COLUMN_PHOTO6, url)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "bunker.db"
        const val TABLE_NAME = "space"
        const val COLUMN_ID = "id"
        const val COLUMN_AREA = "area"
        const val COLUMN_HEIGHT = "height"
        const val COLUMN_WIDTH = "width"
        const val COLUMN_SPACE_TYPE = "spaceType"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
        const val COLUMN_PHOTO1 = "photo1"
        const val COLUMN_PHOTO2 = "photo2"
        const val COLUMN_PHOTO3 = "photo3"
        const val COLUMN_PHOTO4 = "photo4"
        const val COLUMN_PHOTO5 = "photo5"
        const val COLUMN_PHOTO6 = "photo6"

    }
}