package pe.edu.upc.bunker.modelViews.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getBlobOrNull
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import pe.edu.upc.bunker.R
import pe.edu.upc.bunker.dbHelper.BunkerDBHelper


class CreateSpaceStepThreeDot1Activity : AppCompatActivity() {

    private lateinit var photo1Button: ImageButton
    private lateinit var photo2Button: ImageButton
    private lateinit var photo3Button: ImageButton
    private lateinit var photo4Button: ImageButton
    private lateinit var photo5Button: ImageButton
    private lateinit var photo6Button: ImageButton
    private lateinit var nextButton: Button

    private var bitmap1Thumbnail: Bitmap? = null
    private var bitmap2Thumbnail: Bitmap? = null
    private var bitmap3Thumbnail: Bitmap? = null
    private var bitmap4Thumbnail: Bitmap? = null
    private var bitmap5Thumbnail: Bitmap? = null
    private var bitmap6Thumbnail: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_three_dot1)
        photo1Button = findViewById(R.id.photo_1_image_photo)
        photo2Button = findViewById(R.id.photo_2_image_photo)
        photo3Button = findViewById(R.id.photo_3_image_photo)
        photo4Button = findViewById(R.id.photo_4_image_photo)
        photo5Button = findViewById(R.id.photo_5_image_photo)
        photo6Button = findViewById(R.id.photo_6_image_photo)
        nextButton = findViewById(R.id.step_3_next_button)

        photo1Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        photo2Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        photo3Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        photo4Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        photo5Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        photo6Button.setOnClickListener {
            val intent = Intent(this, CreateSpaceStepThreeActivity::class.java)
            val bitmapThumbnail: Bitmap? = null
            val bitmapOriginal: Bitmap? = null
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            intent.putExtra("photoOriginal", bitmapOriginal)
            startActivityForResult(intent, 1)
        }
        nextButton.setOnClickListener {
            val dbHandler = BunkerDBHelper(this, null)
            val cursor = dbHandler.getAllSpaces()
            cursor!!.moveToFirst()
            if (cursor.getBlobOrNull(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO1)) == null) {
                Snackbar.make(nextButton, "Por favor tome una fotografía", Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            startActivity(Intent(this, CreateSpaceStepFourActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val rotationDegree: Float = data?.getFloatExtra("rotationDegree", 0f) as Float
                    val dbHandler = BunkerDBHelper(this, null)
                    val cursor = dbHandler.getAllSpaces()
                    cursor!!.moveToFirst()
                    val photoURL1 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO1))
                    val photoURL2 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO2))
                    val photoURL3 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO3))
                    val photoURL4 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO4))
                    val photoURL5 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO5))
                    val photoURL6 =
                        cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO6))
                    if (photoURL1.isNotBlank()) {
                        Picasso.get().load(photoURL1).fit().into(photo1Button)
                            photo1Button.rotation = rotationDegree
                        }
                    if (photoURL2.isNotBlank()) {
                        Picasso.get().load(photoURL2).fit().into(photo2Button)
                            photo2Button.rotation = rotationDegree
                        }
                    if (photoURL3.isNotBlank()) {
                        Picasso.get().load(photoURL3).fit().into(photo3Button)
                            photo3Button.rotation = rotationDegree
                        }
                    if (photoURL4.isNotBlank()) {
                        Picasso.get().load(photoURL4).fit().into(photo4Button)
                            photo4Button.rotation = rotationDegree
                        }
                    if (photoURL5.isNotBlank()) {
                        Picasso.get().load(photoURL5).fit().into(photo5Button)
                            photo5Button.rotation = rotationDegree
                        }
                    if (photoURL6.isNotBlank()) {
                        Picasso.get().load(photoURL6).fit().into(photo6Button)
                            photo6Button.rotation = rotationDegree
                        }

                }
            }
        }
    }
}
