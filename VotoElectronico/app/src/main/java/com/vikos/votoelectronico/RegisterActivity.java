package com.vikos.votoelectronico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vikos.votoelectronico.helpers.Validadores;
import com.vikos.votoelectronico.request.RegistroRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText matriculaEdit;
    private EditText usuarioEdit;
    private EditText contrasenyaEdit;
    private EditText contrasenyaConfirmacionEdit;
    private Button btnRegistro;
    private Button btnCancelar;

    RegistroRequest registroRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        matriculaEdit = findViewById(R.id.matricula);
        usuarioEdit = findViewById(R.id.usuario);
        contrasenyaEdit = findViewById(R.id.contrasenya);
        contrasenyaConfirmacionEdit = findViewById(R.id.contrasenya_confirmacion);

        btnRegistro = findViewById(R.id.registrarse);
        btnCancelar = findViewById(R.id.cancelar);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regitrarse();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registroRequest = new RegistroRequest(this);
    }

    public void regitrarse(){
        String matricula   = matriculaEdit.getText().toString();
        String usuario     = usuarioEdit.getText().toString();
        String contrasenya = contrasenyaEdit.getText().toString();
        String contrasenyaConfirmacion = contrasenyaConfirmacionEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(!TextUtils.isEmpty(contrasenya) && !Validadores.isContrasenyaValida(contrasenya)){
            contrasenyaEdit.setError("Contraseña invalida");
            focusView = contrasenyaEdit;
            cancel = true;
        }

        if(!TextUtils.isEmpty(contrasenyaConfirmacion) && !Validadores.isContrasenyaValida(contrasenyaConfirmacion)){
            contrasenyaConfirmacionEdit.setError("Contraseña invalida");
            focusView = contrasenyaConfirmacionEdit;
            cancel = true;
        }

        if(!contrasenya.equals(contrasenyaConfirmacion)){
            contrasenyaConfirmacionEdit.setError("Las contraseñas no coinciden");
            focusView = contrasenyaConfirmacionEdit;
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

        if(TextUtils.isEmpty(matricula)){
            matriculaEdit.setError("La matricula no puede estar vacia");
            focusView = matriculaEdit;
            cancel = true;
        }else if(!Validadores.isMatriculaValida(matricula)){
            matriculaEdit.setError("Error en la matricula");
            focusView = matriculaEdit;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            registroRequest.registro(matricula,usuario,contrasenya);
        }
    }

    public void onRegistroOK(Integer response){
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void onRegistroError(Integer response){
        if(response == -1){
            matriculaEdit.setError("La matricula ingresada no existe");
            matriculaEdit.requestFocus();
        }else if(response == -2){
            matriculaEdit.setError("La matricula ya esta registrada");
            matriculaEdit.requestFocus();
        }else if(response == -3) {
            usuarioEdit.setError("El nombre de usuario ya esta ocupado");
            usuarioEdit.requestFocus();
        }
    }
}
