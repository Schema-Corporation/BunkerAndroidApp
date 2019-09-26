package pe.edu.upc.bunker.modelViews.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.fotoapparat.selector.off
import io.fotoapparat.selector.torch
import io.fotoapparat.view.CameraView
import pe.edu.upc.bunker.R
import java.io.File

class CreateSpaceStepThreeActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private lateinit var fabCamera: FloatingActionButton
    private lateinit var fabSwitchCamera: FloatingActionButton
    private lateinit var fabFlash: FloatingActionButton
    private var fotoapparat: Fotoapparat? = null

    private val filename = "test.png"
    private val sd = Environment.getExternalStorageDirectory().absolutePath
    private val dest = File(sd, filename)

    private var fotoapparatState: FotoapparatState? = null
    private var cameraStatus: CameraState? = null
    private var flashState: FlashState? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_space_step_three)

        fabCamera = findViewById(R.id.fab_camera)
        fabSwitchCamera = findViewById(R.id.fab_switch_camera)
        fabFlash = findViewById(R.id.fab_flash)

        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF
        pressedShoot()
        pressedSwitchCamera()
        pressedFlash()
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
                ?.takePicture()?.saveToFile(dest)
        }
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

        fotoapparat = Fotoapparat(
            context = this,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
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