package pe.edu.upc.bunker.modelViews.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import io.fotoapparat.view.CameraView
import pe.edu.upc.bunker.R
import kotlin.math.min


class CreateSpaceStepThreeActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private lateinit var fabCamera: FloatingActionButton
    private lateinit var fabSwitchCamera: FloatingActionButton
    private lateinit var fabFlash: FloatingActionButton
    private lateinit var previewImageButton: ImageButton
    private var fotoapparat: Fotoapparat? = null

    private var fotoapparatState: FotoapparatState? = null
    private var cameraStatus: CameraState? = null
    private var flashState: FlashState? = null

    private var bitmapThumbnail: Bitmap? = null
    private var bitmapOriginal: Bitmap? = null
    private var rotationDegree: Float? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_three)

        fabCamera = findViewById(R.id.fab_camera)
        fabSwitchCamera = findViewById(R.id.fab_switch_camera)
        fabFlash = findViewById(R.id.fab_flash)
        previewImageButton = findViewById(R.id.preview_photo_image_button_view)

        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF
        pressedShoot()
        pressedSwitchCamera()
        pressedFlash()

        previewImageButton.setOnClickListener {
            Log.d("CameraDebug", "previewButton Pressed")
            val intent = Intent(this, CreateSpaceStepThreeDot1Activity::class.java)
            Log.d("CameraDebug", "photoThumbnail saved")
            intent.putExtra("photoThumbnail", bitmapThumbnail)
            //Log.d("CameraDebug", "photoOriginal saved")
            //intent.putExtra("photoOriginal", bitmapOriginal)
            Log.d("CameraDebug", "rotation saved")
            intent.putExtra("rotationDegree", rotationDegree)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
    private fun pressedShoot() {
        fabCamera.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {
        if (hasNoPermissions()) {
            requestPermission()
        } else {
            fotoapparat
                ?.takePicture()?.toBitmap()?.whenAvailable {
                    Log.d("CameraDebug", "Scaling thumbnail")
                    val photoThumbnail = scalePhoto(it!!.bitmap, 360, 360)
                    Log.d("CameraDebug", "Scaling original photo")

                    previewImageButton.setImageBitmap(photoThumbnail)
                    previewImageButton.visibility = View.VISIBLE
                    previewImageButton.rotation = -it.rotationDegrees.toFloat()

                    rotationDegree = -it.rotationDegrees.toFloat()
                    bitmapThumbnail = photoThumbnail
                    bitmapOriginal = it.bitmap
                }
        }
    }

    private fun scalePhoto(bitmap: Bitmap, maxHeight: Int, maxWidth: Int): Bitmap {
        var photoBm = bitmap
        val bmOriginalWidth = photoBm.width
        val bmOriginalHeight = photoBm.height
        val originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight
        val originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth
        val maxHeight1 = maxHeight
        val maxWidth1 = maxWidth
        photoBm = getScaledBitmap(
            photoBm, bmOriginalWidth, bmOriginalHeight,
            originalWidthToHeightRatio, originalHeightToWidthRatio,
            maxHeight1, maxWidth1
        )
        return photoBm
    }

    private fun getScaledBitmap(
        bm: Bitmap,
        bmOriginalWidth: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double,
        originalHeightToWidthRatio: Double,
        maxHeight: Int,
        maxWidth: Int
    ): Bitmap {
        var modifiableBM = bm
        if (bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {
            modifiableBM = if (bmOriginalWidth > bmOriginalHeight) {
                scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight, originalHeightToWidthRatio)
            } else {
                scaleDeminsFromHeight(
                    bm,
                    maxHeight,
                    bmOriginalHeight,
                    originalWidthToHeightRatio
                )
            }
        }
        return modifiableBM
    }

    private fun scaleDeminsFromHeight(
        bm: Bitmap,
        maxHeight: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double
    ): Bitmap {
        val newHeight = min(maxHeight.toDouble(), bmOriginalHeight * .55).toInt()
        val newWidth = (newHeight * originalWidthToHeightRatio).toInt()
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true)
    }

    private fun scaleDeminsFromWidth(
        bm: Bitmap,
        maxWidth: Int,
        bmOriginalWidth: Int,
        originalHeightToWidthRatio: Double
    ): Bitmap {
        //scale the width
        val newWidth = min(maxWidth.toDouble(), bmOriginalWidth * .75).toInt()
        val newHeight = (newWidth * originalHeightToWidthRatio).toInt()
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true)
    }

    private fun pressedSwitchCamera() {
        fabSwitchCamera.setOnClickListener {
            switchCamera()
        }
    }

    private fun switchCamera() {
        fotoapparat?.switchTo(
            lensPosition = if (cameraStatus == CameraState.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration()
        )

        cameraStatus = if (cameraStatus == CameraState.BACK) {
            CameraState.FRONT
        } else {
            CameraState.BACK
        }
    }

    private fun pressedFlash() {
        fabFlash.setOnClickListener {
            changeFlashState()
        }
    }

    private fun changeFlashState() {
        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                flashMode = if (flashState == FlashState.TORCH) off() else torch()
            )
        )

        flashState = if (flashState == FlashState.TORCH) FlashState.OFF
        else FlashState.TORCH
    }

    private fun createFotoapparat() {
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        val cameraConfiguration = CameraConfiguration(
            pictureResolution = highestResolution(),
            previewResolution = highestResolution(),
            previewFpsRange = highestFps(),
            focusMode = firstAvailable(
                continuousFocusPicture(),
                autoFocus(),
                fixed()
            ),
            flashMode = firstAvailable(
                autoRedEye(),
                autoFlash(),
                torch(),
                off()
            ),
            antiBandingMode = firstAvailable(
                auto(),
                hz50(),
                hz60(),
                none()
            ),
            jpegQuality = manualJpegQuality(100),
            sensorSensitivity = lowestSensorSensitivity()
        )

        fotoapparat = Fotoapparat(
            context = this,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
            cameraConfiguration = cameraConfiguration,
            logger = loggers(
                logcat()
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )

    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onStart() {
        super.onStart()
        if (hasNoPermissions()) {
            requestPermission()
        } else {
            fotoapparat?.start()
            fotoapparatState = FotoapparatState.ON
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasNoPermissions() && fotoapparatState == FotoapparatState.OFF) {
            val intent = Intent(baseContext, CreateSpaceStepThreeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun hasNoPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 0)
    }
}

enum class CameraState {
    FRONT, BACK
}

enum class FlashState {
    TORCH, OFF
}

enum class FotoapparatState {
    ON, OFF
}
