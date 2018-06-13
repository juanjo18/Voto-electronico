package com.vikos.votoelectronico.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vikos.votoelectronico.RegisterActivity;
import com.vikos.votoelectronico.helpers.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    private RequestQueue requestQueue;
    RegisterActivity parent;

    public RegistroRequest(RegisterActivity parent){
        this.parent = parent;
        requestQueue = Volley.newRequestQueue(parent);
    }

    public void registro(String matricula, String usuario, String contrasenya){
        final JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("user", usuario);
            jsonObject.put("matricula", matricula);
            jsonObject.put("password", contrasenya);
        }catch (JSONException e){
            Log.i("REGISTRO", e.getMessage());
        }

        JsonObjectRequest registroRequest = new JsonObjectRequest(
                Request.Method.PUT,
                Constantes.URL + Constantes.REGISTRO,
                jsonObject,
                this,
                this
        );

        requestQueue.add(registroRequest);
    }

    @Override
    public void onResponse(JSONObject response){
        try{
            Log.i("INICIO SESION RESULT", response.getString("result"));
            if(response.getString("result").equals("OK"))
                parent.onRegistroOK(Integer.parseInt(response.getString("registro_id")));
            else
                parent.onRegistroError(Integer.parseInt(response.getString("error_code")));

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error){
        Log.i("REGISTRO", "ERROR AL INTENTAR COSAS " + error.getMessage());
    }
}
