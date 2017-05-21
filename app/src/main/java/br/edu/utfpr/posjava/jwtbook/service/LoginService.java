package br.edu.utfpr.posjava.jwtbook.service;

import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by talyssondecastro on 18/05/17.
 */

public interface LoginService {

    @POST("/auth")
    Call<TokenResponse > getLogin( @Body Usuario usuario);

}
