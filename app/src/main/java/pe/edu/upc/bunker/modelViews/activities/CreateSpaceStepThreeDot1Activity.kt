package pe.edu.upc.bunker.modelViews.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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
    private var bitmap1Original: Bitmap? = null
    private var bitmap2Thumbnail: Bitmap? = null
    private var bitmap2Original: Bitmap? = null
    private var bitmap3Thumbnail: Bitmap? = null
    private var bitmap3Original: Bitmap? = null
    private var bitmap4Thumbnail: Bitmap? = null
    private var bitmap4Original: Bitmap? = null
    private var bitmap5Thumbnail: Bitmap? = null
    private var bitmap5Original: Bitmap? = null
    private var bitmap6Thumbnail: Bitmap? = null
    private var bitmap6Original: Bitmap? = null

    private var lstBitmapsThumbnails: List<Bitmap> = ArrayList()
    private var lstBitmapsOriginals: List<Bitmap> = ArrayList()

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
            if (cursor.getString(cursor.getColumnIndex(BunkerDBHelper.COLUMN_PHOTO1)).isNullOrEmpty()) {
                Snackbar.make(nextButton, "Por favor tome una fotografía", Snackbar.LENGTH_SHORT)
                    .show()
            }
            startActivity(Intent(this, CreateSpaceStepFourActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    //val bitmapOriginal = data?.getParcelableExtra<Bitmap>("photoOriginal")
                    val bitmapThumbnail = data?.getParcelableExtra<Bitmap>("photoThumbnail")
                    val rotationDegree: Float = data?.getFloatExtra("rotationDegree", 0f) as Float
                    when {
                        bitmap1Thumbnail == null -> {
                            //bitmap1Original=bitmapOriginal
                            bitmap1Thumbnail = bitmapThumbnail
                            photo1Button.setImageBitmap(bitmap1Thumbnail)
                            photo1Button.rotation = rotationDegree

                            val params = photo1Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo1Button.layoutParams = params
                        }
                        bitmap2Thumbnail == null -> {
                            //bitmap2Original=bitmapOriginal
                            bitmap2Thumbnail = bitmapThumbnail
                            photo2Button.setImageBitmap(bitmap2Thumbnail)
                            photo2Button.rotation = rotationDegree

                            val params = photo2Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo2Button.layoutParams = params
                        }
                        bitmap3Thumbnail == null -> {
                            //bitmap3Original=bitmapOriginal
                            bitmap3Thumbnail = bitmapThumbnail
                            photo3Button.setImageBitmap(bitmap3Thumbnail)
                            photo3Button.rotation = rotationDegree

                            val params = photo3Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo3Button.layoutParams = params
                        }
                        bitmap4Thumbnail == null -> {
                            //bitmap4Original=bitmapOriginal
                            bitmap4Thumbnail = bitmapThumbnail
                            photo4Button.setImageBitmap(bitmap4Thumbnail)
                            photo4Button.rotation = rotationDegree

                            val params = photo4Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo4Button.layoutParams = params
                        }
                        bitmap5Thumbnail == null -> {
                            //bitmap5Original=bitmapOriginal
                            bitmap5Thumbnail = bitmapThumbnail
                            photo5Button.setImageBitmap(bitmap5Thumbnail)
                            photo5Button.rotation = rotationDegree

                            val params = photo5Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo5Button.layoutParams = params
                        }
                        bitmap6Thumbnail == null -> {
                            //bitmap6Original=bitmapOriginal
                            bitmap6Thumbnail = bitmapThumbnail
                            photo6Button.setImageBitmap(bitmap6Thumbnail)
                            photo6Button.rotation = rotationDegree

                            val params = photo6Button.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            photo6Button.layoutParams = params
                        }
                    }
                }
            }
        }
    }
}
