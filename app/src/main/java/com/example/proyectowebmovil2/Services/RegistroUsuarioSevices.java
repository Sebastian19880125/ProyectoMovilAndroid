package com.example.proyectowebmovil2.Services;



import com.example.proyectowebmovil2.models.RegistroUsuarioModel;
import com.example.proyectowebmovil2.models.RegistroUsuarioRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistroUsuarioSevices {

    @POST("feature_login/registro_usuario.php")
    Call<RegistroUsuarioModel> registrousuario(@Body RegistroUsuarioRequest registrousuarioRequest);
}
