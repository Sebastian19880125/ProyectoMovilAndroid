package com.example.proyectowebmovil2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectowebmovil2.Services.RegistroUsuarioSevices;
import com.example.proyectowebmovil2.models.RegistroUsuarioModel;
import com.example.proyectowebmovil2.models.RegistroUsuarioRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroUsuario extends AppCompatActivity {

    EditText et_nombres, et_apellidos, et_documento, et_correo, et_telefono, et_direccion, et_password;
    Button btnregistrousuario, btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        getSupportActionBar().hide();

        et_nombres = findViewById(R.id.et_nombres);
        et_apellidos = findViewById(R.id.et_apellidos);
        et_documento = findViewById(R.id.et_identificacion);
        et_correo = findViewById(R.id.et_correo);
        et_telefono = findViewById(R.id.et_numtelefono);
        et_direccion = findViewById(R.id.et_direccion);
        et_password = findViewById(R.id.et_password);

        btnregistrousuario = findViewById(R.id.btnregistrousuario);
        btnsalir = findViewById(R.id.btnsalir);

        btnregistrousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser();
            }
        });

    }

    public void CreateUser() {

        String nombres = et_nombres.getText().toString().trim();
        String apellidos = et_apellidos.getText().toString().trim();
        String documen = et_documento.getText().toString().trim();
        String correo = et_correo.getText().toString().trim();
        String telef = et_telefono.getText().toString().trim();
        String direccion = et_direccion.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        int documento = Integer.parseInt(documen);
        int telefono = Integer.parseInt(telef);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.17/ProyectoWebMovil2ApiRest/feature_login/").addConverterFactory(GsonConverterFactory.create())
                .build();
        RegistroUsuarioSevices registroUsuarioSevices = retrofit.create(RegistroUsuarioSevices.class);
        RegistroUsuarioRequest registroUsuarioRequest = new RegistroUsuarioRequest();

        registroUsuarioRequest.setDocumento(documento);
        registroUsuarioRequest.setNombres(nombres);
        registroUsuarioRequest.setApellidos(apellidos);
        registroUsuarioRequest.setDireccion(direccion);
        registroUsuarioRequest.setTelefono(telefono);
        registroUsuarioRequest.setCorreo(correo);
        registroUsuarioRequest.setPassword(password);

        Call<RegistroUsuarioModel> registro = registroUsuarioSevices.registrousuario(registroUsuarioRequest);

        registro.enqueue(new Callback<RegistroUsuarioModel>() {
            @Override
            public void onResponse(Call<RegistroUsuarioModel> call, retrofit2.Response<RegistroUsuarioModel> response) {
                Log.d("Response", response.toString());
                if (response.isSuccessful()) {

                    limpiar();
                    RegistroUsuarioModel model = response.body();
                    Toast.makeText(RegistroUsuario.this, model.getRespuesta(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroUsuarioModel> call, Throwable t) {
                Toast.makeText(RegistroUsuario.this, "No se logro registrar el usuario" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo de limpiar campos
    public  void limpiar(){

        et_nombres.setText("");
        et_apellidos.setText("");
        et_documento.setText("");
        et_correo.setText("");
        et_telefono.setText("");
        et_direccion.setText("");
        et_password.setText("");
    }

    public void Salir(View view) {
        finish();
    }
}
