package com.example.myapplication.ecraprincipla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityConsumos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumos)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.consumos -> {
                    val validacao = Intent(this, ActivityValidacao::class.java)
                    startActivity(validacao)
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
    }
}