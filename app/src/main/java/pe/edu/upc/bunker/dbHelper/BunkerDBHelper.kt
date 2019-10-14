package pe.edu.upc.bunker.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pe.edu.upc.bunker.dto.createSpace.*

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
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
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
                COLUMN_PHOTO6 + " TEXT," +
                COLUMN_SERVICE1 + " INTEGER," +
                COLUMN_SERVICE2 + " INTEGER," +
                COLUMN_SERVICE3 + " INTEGER," +
                COLUMN_SERVICE4 + " INTEGER," +
                COLUMN_SERVICE5 + " INTEGER," +
                COLUMN_SERVICE6 + " INTEGER" +
                COLUMN_LESSOR_ID + " INTEGER" +
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
        values.put(COLUMN_SERVICE1, 0)
        values.put(COLUMN_SERVICE2, 0)
        values.put(COLUMN_SERVICE3, 0)
        values.put(COLUMN_SERVICE4, 0)
        values.put(COLUMN_SERVICE5, 0)
        values.put(COLUMN_SERVICE6, 0)
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
        description: String,
        lessorId: Int
    ) {
        val values = ContentValues()
        values.put(COLUMN_HEIGHT, height)
        values.put(COLUMN_WIDTH, width)
        values.put(COLUMN_AREA, area)
        values.put(COLUMN_SPACE_TYPE, spaceType)
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_LESSOR_ID, lessorId)
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

    fun addServices(
        lstServices:List<Int>
    ) {
        val values = ContentValues()
        for (service in lstServices)
        {
            if(service==1)
            {
                values.put(COLUMN_SERVICE1, service)
            }
            if(service==2)
            {
                values.put(COLUMN_SERVICE2, service)
            }
            if(service==3)
            {
                values.put(COLUMN_SERVICE3, service)
            }
            if(service==4)
            {
                values.put(COLUMN_SERVICE4, service)
            }
            if(service==5)
            {
                values.put(COLUMN_SERVICE5, service)
            }
            if(service==6)
            {
                values.put(COLUMN_SERVICE6, service)
            }
        }
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "id = 1", null)
        db.close()
    }

    private fun addPhotosToArray(
        photo1: String,
        photo2: String,
        photo3: String,
        photo4: String,
        photo5: String,
        photo6: String): ArrayList<PhotoCreateDTO> {

        val listPhotoCreateDTO = ArrayList<PhotoCreateDTO>()

        val photos = ArrayList<String>()

        if (photo1.isNotBlank()) {
            photos.add(photo1)
        }
        if (photo2.isNotBlank()) {
            photos.add(photo2)
        }
        if (photo3.isNotBlank()) {
            photos.add(photo3)
        }
        if (photo4.isNotBlank()) {
            photos.add(photo4)
        }
        if (photo5.isNotBlank()) {
            photos.add(photo5)
        }
        if (photo6.isNotBlank()) {
            photos.add(photo6)
        }

        photos.forEach {
            val photo = PhotoCreateDTO()
            photo.photoUrl = it

            listPhotoCreateDTO.add(photo)
        }
        return listPhotoCreateDTO
    }

    private fun addLocationData(
        address: String,
        latitude: Double,
        longitude: Double
    ) : LocationCreateDTO {
        val locationCreateDTO = LocationCreateDTO()
        locationCreateDTO.address = address
        locationCreateDTO.latitude = latitude
        locationCreateDTO.longitude = longitude

        return locationCreateDTO
    }

    private fun addServiceData(
        service1: Int,
        service2: Int,
        service3: Int,
        service4: Int,
        service5: Int,
        service6: Int
    ): ArrayList<ServiceCreateDTO> {
        val serviceList = ArrayList<ServiceCreateDTO>()
        if (service1 != 0) {
            serviceList.add(ServiceCreateDTO(service1))
        }
        if (service2 != 0) {
            serviceList.add(ServiceCreateDTO(service2))
        }
        if (service3 != 0) {
            serviceList.add(ServiceCreateDTO(service3))
        }
        if (service4 != 0) {
            serviceList.add(ServiceCreateDTO(service4))
        }
        if (service5 != 0) {
            serviceList.add(ServiceCreateDTO(service5))
        }
        if (service6 != 0) {
            serviceList.add(ServiceCreateDTO(service6))
        }

        return serviceList
    }

    val getSpaceFromDb: SpaceCreateDTO
        get() {
            val spaceCreateDTO = SpaceCreateDTO()

            val findSpaceQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(findSpaceQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    // step 1
                    spaceCreateDTO.height = cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT))
                    spaceCreateDTO.width = cursor.getDouble(cursor.getColumnIndex(COLUMN_WIDTH))
                    spaceCreateDTO.area = cursor.getDouble(cursor.getColumnIndex(COLUMN_AREA))
                    spaceCreateDTO.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                    spaceCreateDTO.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))

                    val lessorId = cursor.getInt(cursor.getColumnIndex(COLUMN_LESSOR_ID))
                    val lessorCreateDTO = LessorCreateDTO(lessorId)
                    spaceCreateDTO.lessor = lessorCreateDTO

                    // step 2
                    val address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
                    val latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))
                    val longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))

                    val locationDTO = addLocationData(address, latitude, longitude)

                    spaceCreateDTO.location = locationDTO

                    // step 3
                    val photo1 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO1))
                    val photo2 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO2))
                    val photo3 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO3))
                    val photo4 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO4))
                    val photo5 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO5))
                    val photo6 = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO6))

                    val photosCreateDTO = addPhotosToArray(photo1, photo2, photo3, photo4, photo5, photo6)

                    spaceCreateDTO.photos = photosCreateDTO

                    // Step 4

                    val service1 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE1))
                    val service2 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE2))
                    val service3 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE3))
                    val service4 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE4))
                    val service5 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE5))
                    val service6 = cursor.getInt(cursor.getColumnIndex(COLUMN_SERVICE6))

                    val serviceList =
                        addServiceData(service1, service2, service3, service4, service5, service6)

                    spaceCreateDTO.services = serviceList

                    val lessor : LessorCreateDTO = LessorCreateDTO()
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return spaceCreateDTO
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
        const val COLUMN_SERVICE1 = "service1"
        const val COLUMN_SERVICE2 = "service2"
        const val COLUMN_SERVICE3 = "service3"
        const val COLUMN_SERVICE4 = "service4"
        const val COLUMN_SERVICE5 = "service5"
        const val COLUMN_SERVICE6 = "service6"
        const val COLUMN_LESSOR_ID = "lessorId"
    }
}