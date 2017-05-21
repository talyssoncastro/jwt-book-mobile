package br.edu.utfpr.posjava.jwtbook.service;

import java.util.List;

import br.edu.utfpr.posjava.jwtbook.model.Livro;
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

public interface LivroService {

    @GET("/livro/")
    Call<List<Livro > > getAll();

    @GET("/livro/{id}")
    Call<Livro> getOne(@Path("id") Long id);

    @POST("/livro/")
    Call<Void> save( @Body Livro livro);

    @PUT("/livro/")
    Call<Void> update(@Body Livro livro);

    @DELETE("/livro/{id}")
    Call<Void> delete(@Path("id") Long id);

}
