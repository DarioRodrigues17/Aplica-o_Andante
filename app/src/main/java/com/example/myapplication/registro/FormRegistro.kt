package com.example.myapplication.registro

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFormRegistroBinding
import com.example.myapplication.login.FormLogin
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class FormRegistro : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegistroBinding
    private val auth = FirebaseAuth.getInstance()


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoregisto.setOnClickListener {view ->

            val nome = binding.nome.text.toString()
            val apelido = binding.apelido.text.toString()
            val email = binding.emailEditInput.text.toString()
            val password = binding.passwordEditInput.text.toString()

            if (nome.isEmpty() ||apelido.isEmpty() ||email.isEmpty() || password.isEmpty()){
                    val snackbar = Snackbar.make(view,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {registo ->
                    if (registo.isSuccessful) {
                        val snackbar =
                            Snackbar.make(view, "Sucesso ao registar conta!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        binding.nome.setText("")
                        binding.apelido.setText("")
                        binding.emailEditInput.setText("")
                        binding.passwordEditInput.setText("")
                    }
                }.addOnFailureListener { exception ->
                    val mensagemErro = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Escreva uma password com no mínimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Escreva um email válido!"
                        is FirebaseAuthUserCollisionException -> "Esta conta já foi Registada!"
                        is FirebaseNetworkException -> "Sem ligação à internet!"
                        else -> "Erro ao registar utilizador!"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
            binding.textTelaJaTemConta.setOnClickListener{
                val intent = Intent(this,FormLogin::class.java)
                startActivity(intent)
            }
        }
    }
}