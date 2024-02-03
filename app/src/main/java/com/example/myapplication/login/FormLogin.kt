package com.example.myapplication.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityFormLoginBinding
import com.example.myapplication.ecraprincipla.MainActivity
import com.example.myapplication.registro.FormRegistro
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class FormLogin : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()
    lateinit var myPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoregisto.setOnClickListener {view ->

            val email = binding.emailEditInput.text.toString()
            val password = binding.passwordEditInput.text.toString()
            var emailstring: String?=""
            var passwordstring: String?=""

            emailstring = myPreferences?.getString("email", "")
            Log.d("FormLogin", "Valor recuperado do SharedPreferences email: $emailstring")
            passwordstring = myPreferences?.getString("password", "")
            Log.d("FormLogin", "Valor recuperado do SharedPreferences password: $passwordstring")

            binding.emailEditInput.setText(emailstring)
            binding.passwordEditInput.setText(passwordstring)

            if(email.isEmpty() || password.isEmpty()){
                Log.d("FormLogin", "Campos de email e senha vazios")
                //mostra a mensagem de erro
            }else{
                Log.d("FormLogin", "Tentando autenticar com email: $email")
                //tenta autenticar
            }


            if(email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{autenticacao ->
                    if(autenticacao.isSuccessful){
                        Toast.makeText(this, "lOGIN COM Sucesso usando $email", Toast.LENGTH_SHORT).show()

                        with(myPreferences.edit()){
                            putString("email", email)
                            putString("password", password)
                        }
                        acessoEcraPricipal()
                    }

                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view,"Erro ao fazer Login!",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }

        }

        binding.textTelaCriarConta.setOnClickListener{
            val intent = Intent(this,FormRegistro::class.java)
            startActivity(intent)
        }
        setupSharedPreferences()

    }
        private fun acessoEcraPricipal() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    override fun onStart(){
        super.onStart()

        val contaatual = auth.currentUser


        if (contaatual != null)
        {
            Toast.makeText(this, "OnStart ecra Principal", Toast.LENGTH_SHORT).show()
          //  acessoEcraPricipal()
       }
    }
    private fun setupSharedPreferences(){
        myPreferences=PreferenceManager.getDefaultSharedPreferences(this)
        myPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences,key: String){

    }
}