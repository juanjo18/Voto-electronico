package com.vikos.votoelectronico.helpers;

import android.text.TextUtils;


public class Validadores {
    public static boolean isContrasenyaValida(String contrasenya){
        return contrasenya.length() > 4;
    }

    public static boolean isUsuarioValido(String usuario){
        return usuario.length() > 4;
    }

    public static boolean isMatriculaValida(String matricula){
        return matricula.length() == 9 && TextUtils.isDigitsOnly(matricula);
    }
}
