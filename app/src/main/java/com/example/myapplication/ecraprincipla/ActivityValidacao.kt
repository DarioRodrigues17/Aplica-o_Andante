package com.example.myapplication.ecraprincipla

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter




class ActivityValidacao : AppCompatActivity() {

    private lateinit var qrCodeImage: ImageView
    private lateinit var generateQrCodeButton: Button
    private val data = "1234567890" //exemplo de dados do passe andante

    override fun onCreate(savedInstanceState: Bundle?) {
        generateQrCodeButton = findViewById(R.id.generate_qr_code_button)
        qrCodeImage = findViewById(R.id.qr_code_image)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.consumos -> {
                    val consumos = Intent(this, ActivityConsumos::class.java)
                    startActivity(consumos)
                }
                R.id.pagamentos -> {
                    val pagamentos = Intent(this, ActivityPagamentos::class.java)
                    startActivity(pagamentos)
                }
                R.id.viajar -> {
                    val viajar = Intent(this, MainActivity::class.java)
                    startActivity(viajar)
                }
            }
            return@setOnItemSelectedListener true
        }

        generateQrCodeButton.setOnClickListener {
            try {
                val qrCodeWriter = QRCodeWriter()
                val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val pixels = IntArray(width * height)
                for (y in 0 until height) {
                    val offset = y * width
                    for (x in 0 until width) {
                        pixels[offset + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                    }
                }
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
                qrCodeImage.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validacao)
    }

}