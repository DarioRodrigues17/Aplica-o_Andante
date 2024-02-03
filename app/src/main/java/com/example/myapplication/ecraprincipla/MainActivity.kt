package com.example.myapplication.ecraprincipla

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
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
                R.id.validacao -> {
                    val validacao = Intent(this, ActivityValidacao::class.java)
                    startActivity(validacao)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}




