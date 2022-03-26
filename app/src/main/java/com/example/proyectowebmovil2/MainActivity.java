package com.example.proyectowebmovil2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectowebmovil2.Services.LoginServices;
import com.example.proyectowebmovil2.models.LoginModel;
import com.example.proyectowebmovil2.models.LoginRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    String[] paises={"Colombia","Argentina","Estados Unidos","Mexico"};
    TextView hyperlinkregistro;
    EditText edtusuario,edtpassword;
    Button btningresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        spinner = findViewById(R.id.ListaPaises);
        hyperlinkregistro = findViewById(R.id.textView5);
        edtusuario = findViewById(R.id.editTextTextEmailAddress);
        edtpassword = findViewById(R.id.editTextTextPassword);
        btningresar = findViewById(R.id.button);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, R.layout.item_file,paises);
        adapter.setDropDownViewResource(R.layout.item_file);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void RegistroUsuario (View view){
        Intent miIntent = new Intent(MainActivity.this,RegistroUsuario.class);
        startActivity(miIntent);
    }

    public  void logins (View view){

        String usuario = edtusuario.getText().toString().trim();
        String password = edtpassword.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.17/ProyectoWebMovil2ApiRest/").addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginServices loginServices = retrofit.create(LoginServices.class);
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsuario(usuario);
        loginRequest.setPassword(password);

        Call<LoginModel> login = loginServices.Login(loginRequest);

        login.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                if(response.isSuccessful()){

                    Intent miIntent = new Intent(MainActivity.this,RegistroUsuario.class);
                    startActivity(miIntent);
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}