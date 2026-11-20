package com.ctvgrk.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.ctvgrk.app.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cameraAdapter = CameraAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start Python
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        binding.rvCameras.layoutManager = LinearLayoutManager(this)
        binding.rvCameras.adapter = cameraAdapter

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString().orEmpty()
        val platform = if (binding.rbEzviz.isChecked) "ezviz" else "hikconnect"

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnLogin.isEnabled = false
        binding.tvStatus.text = getString(R.string.status_logging_in)

        lifecycleScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val py = Python.getInstance()
                    val module = py.getModule("cloudcam_android")
                    module.callAttr(
                        "list_cameras",
                        email,
                        password,
                        platform
                    ).toString()
                }

                // Expected format: JSON array of objects
                val cameras = parseCameras(result)
                cameraAdapter.submitList(cameras)

                binding.tvStatus.text = "Berhasil. ${cameras.size} kamera ditemukan."
            } catch (e: Exception) {
                binding.tvStatus.text = "Error: ${e.message}"
                Toast.makeText(this@MainActivity, e.message ?: "Login gagal", Toast.LENGTH_LONG).show()
            } finally {
                binding.btnLogin.isEnabled = true
            }
        }
    }

    private fun parseCameras(jsonStr: String): List<CameraItem> {
        return try {
            val arr = JSONArray(jsonStr)
            val list = mutableListOf<CameraItem>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(
                    CameraItem(
                        serial = obj.optString("serial", "-"),
                        name = obj.optString("name", "Camera"),
                        channel = obj.optInt("channel", 1),
                        status = obj.optString("status", "unknown")
                    )
                )
            }
            list
        } catch (e: Exception) {
            // Fallback: treat whole string as error message
            listOf(CameraItem(serial = "-", name = jsonStr, channel = 0, status = "error"))
        }
    }
}

data class CameraItem(
    val serial: String,
    val name: String,
    val channel: Int,
    val status: String
)
