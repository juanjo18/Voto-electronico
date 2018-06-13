package com.vikos.votoelectronico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vikos.votoelectronico.helpers.Validadores;
import com.vikos.votoelectronico.request.InicioSesionRequest;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText usuarioEdit;
    private EditText contrasenyaEdit;
    private Button btnIniciarSesion;
    private Button btnRegistro;
    InicioSesionRequest inicioSesionRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioEdit = findViewById(R.id.usuario);
        contrasenyaEdit = findViewById(R.id.contrasenya);
        btnIniciarSesion = findViewById(R.id.iniciar_sesion);
        btnRegistro = findViewById(R.id.registrarse);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registroIntent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(registroIntent);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
        inicioSesionRequest = new InicioSesionRequest(this);
    }


    public void iniciarSesion(){
        String usuario = usuarioEdit.getText().toString();
        String contrasenya = contrasenyaEdit.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if(!TextUtils.isEmpty(contrasenya) && !Validadores.isContrasenyaValida(contrasenya)){
            contrasenyaEdit.setError("Contraseña invalida");
            focusView = contrasenyaEdit;
            cancel = true;
        }

        if(TextUtils.isEmpty(usuario)){
            usuarioEdit.setError("El usuario no puede estar vacio");
            focusView = usuarioEdit;
            cancel = true;
        }else if(!Validadores.isUsuarioValido(usuario)){
            usuarioEdit.setError("El usuario es debe ser almenos de 5 caracteres");
            focusView = usuarioEdit;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            inicioSesionRequest.iniciarSesion(usuario, contrasenya);
        }
    }

    public void onSesionIniciadaOK(Integer result){
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void onSesionIniciadaError(Integer result){
        if(result == -1){
            usuarioEdit.setError("Usuario no registrado");
            usuarioEdit.requestFocus();
        }else if(result == -2){
            contrasenyaEdit.setError("Contraseña erronea");
            contrasenyaEdit.requestFocus();
        }
    }
}
