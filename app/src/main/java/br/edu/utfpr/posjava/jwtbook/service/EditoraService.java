package br.edu.utfpr.posjava.jwtbook.service;

import java.util.List;

import br.edu.utfpr.posjava.jwtbook.model.Editora;
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

public interface EditoraService {

    @GET("/editora/")
    Call<List<Editora> > getAll();

    @GET("/editora/{id}")
    Call<Editora> getOne(@Path("id") Long id);

    @POST("/editora/")
    Call<Void> save( @Body Editora editora);

    @PUT("/editora/")
    Call<Void> update(@Body Editora editora);

    @DELETE("/editora/{id}")
    Call<Void> delete(@Path("id") Long id);

}
