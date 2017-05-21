package br.edu.utfpr.posjava.jwtbook.service;

import java.util.List;

import br.edu.utfpr.posjava.jwtbook.model.Genero;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by talyssondecastro on 20/05/17.
 */

public interface GeneroService {

    @GET("/genero/")
    Call<List<Genero> > getAll();

    @GET("/genero/{id}")
    Call<Genero> getOne(@Path("id") Long id);

    @POST("/genero/")
    Call<Void> save( @Body Genero genero);

    @PUT("/genero/")
    Call<Void> update(@Body Genero genero);

    @DELETE("/genero/{id}")
    Call<Void> delete(@Path("id") Long id);
}
