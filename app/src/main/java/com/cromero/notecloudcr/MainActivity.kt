package com.cromero.notecloudcr

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null;
    val RC_GOOGLE_SIGNIN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fAuth = FirebaseAuth.getInstance()

        //Login google

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        botonGoogle.setOnClickListener{
            var signInIntent = mGoogleSignInClient?.signInIntent

            startActivityForResult(signInIntent, RC_GOOGLE_SIGNIN)
        }
    }

    fun navegar(){
        var int = Intent(this, App_Activity::class.java)

        startActivity(int)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_GOOGLE_SIGNIN){
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            val account: GoogleSignInAccount? =
                task.getResult<ApiException>(ApiException::class.java)
            if(account != null){
                println(account.idToken)
                FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(account.idToken,null))

                if(FirebaseAuth.getInstance().currentUser != null){
                    navegar()
                } else{
                    Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

/**
 * TODO Fases del proyecto
 * 0. Configurar proyecto:            ------ HECHO
 *      0.1 - Conectar a firebase                   -- Hecho
 *      0.2 - Activar login contraseña y google     -- Hecho
 *      0.3 - Añadir clave SHA1                     -- Hecho
 *      0.4 - Activar base de datos                 -- Hecho
 *      0.5 - Importar librerias                    -- Hecho
 *
 * 1. - Login activity:               ------- HECHO
 *      1.1 - Interfaz                              -- Hecho
 *      1.2 - Login y registro de correo            -- Hecho
 *      1.3 - Login Google                          -- Hecho
 *      1.4 - Llamar siguiente activity             -- Hecho
 *
 * --App activity
 * 2. - Fragment Principal:     -------
 *      2.1 - Interfaz (crear recyclerview)                 --
 *      2.2 - Boton flotante para añadir notas              --
 *      2.3 - Al pulsar una nota se pasa al fragment nota   --
 *
 * 3. - Fragment nota:          -------
 *      3.1 - Mostrar titulo y contenido de la nota     --
 *      3.2 - Boton para borrar nota en el menu         --
 *      3.3 - Boton para guardar nota (modificar)       --
 * */