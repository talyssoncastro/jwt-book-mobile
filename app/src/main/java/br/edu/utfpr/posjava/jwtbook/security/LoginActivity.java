package br.edu.utfpr.posjava.jwtbook.security;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import br.edu.utfpr.posjava.jwtbook.HomeActivity;
import br.edu.utfpr.posjava.jwtbook.R;
import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.model.Usuario;
import br.edu.utfpr.posjava.jwtbook.service.LoginService;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        etEmail = ( EditText ) findViewById( R.id.etEmail );
        etPassword = ( EditText ) findViewById( R.id.etPassword );

        btSignIn = ( Button ) findViewById( R.id.btSignIn );
        btSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                efetuarLogin();
            }
        } );

    }

    public void abirHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void efetuarLogin(){

        /*
        LoginService loginService = ServiceGenerator.createService(LoginService.class, "");
        Usuario usuario = new Usuario();
        usuario.setUsername( etEmail.getText().toString() );
        usuario.setPassword(  etPassword.getText().toString() );

        Call<TokenResponse > call = loginService.getLogin(usuario);
        try {
            TokenResponse token = call.execute().body();
            if (token != null) {
                token.setTokenSharedPreferences(token.getToken(), this);
                abirHome();
            }else{
                etEmail.setError(getString(R.string.error_invalid_email));
                etEmail.requestFocus();
                etPassword.setError(getString(R.string.error_incorrect_password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        new RetrieveFeedTask().execute( etEmail.getText().toString(), etPassword.getText().toString() );

    }

    private void tratarRetorno(TokenResponse token) {

        if ( token != null ) {
            token.setTokenSharedPreferences( token.getToken(), this );
            abirHome();
        } else {
            etEmail.setError( getString( R.string.error_invalid_email ) );
            etEmail.requestFocus();
            etPassword.setError( getString( R.string.error_incorrect_password ) );
        }

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, TokenResponse> {

        @Override
        protected TokenResponse doInBackground(String... params) {

            String username = params[ 0 ];
            String password = params[ 1 ];

            LoginService loginService = ServiceGenerator.createService( LoginService.class, "" );
            Usuario usuario = new Usuario();
            usuario.setUsername( username );
            usuario.setPassword( password );

            Call<TokenResponse > call = loginService.getLogin(usuario);
            try {
                TokenResponse token = call.execute().body();
                return token;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute( TokenResponse token ) {
            tratarRetorno( token );
        }
    }
}
