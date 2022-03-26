package com.example.proyectowebmovil2.Services;

import com.example.proyectowebmovil2.models.LoginModel;
import com.example.proyectowebmovil2.models.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginServices {

    @POST("feature_login/validar_usuario.php")
    Call<LoginModel> Login(@Body LoginRequest loginRequest);
}
