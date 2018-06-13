package com.vikos.votoelectronico.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vikos.votoelectronico.LoginActivity;
import com.vikos.votoelectronico.helpers.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InicioSesionRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    private RequestQueue requestQueue;
    LoginActivity parent;

    public InicioSesionRequest(LoginActivity parent){
        this.parent = parent;
        requestQueue = Volley.newRequestQueue(parent);
    }

    public void iniciarSesion(String usuario, String contrasenya){
        final JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("user", usuario);
            jsonObject.put("password", contrasenya);
        }catch (JSONException e){
            Log.i("INICIAR SESION", e.getMessage());
        }

        JsonObjectRequest inicioSesionRequest = new JsonObjectRequest(
                Request.Method.PUT,
                Constantes.URL + Constantes.LOGIN,
                jsonObject,
                this,
                this
        );
        Log.i("INICIO SESION", "SE AGREGO A LA COLA");
        requestQueue.add(inicioSesionRequest);
    }

    @Override
    public void onResponse(JSONObject response){
        try{
            Log.i("INICIO SESION RESULT", response.getString("result"));
            if(response.getString("result").equals("OK"))
                parent.onSesionIniciadaOK(Integer.parseInt(response.getString("registro_id")));
            else
                parent.onSesionIniciadaError(Integer.parseInt(response.getString("error_code")));

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error){
        Log.i("INICIO SESION", "ERROR AL INTENTAR COSAS " + error.getMessage());
    }
}
