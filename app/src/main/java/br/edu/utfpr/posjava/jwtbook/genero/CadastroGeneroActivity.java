package br.edu.utfpr.posjava.jwtbook.genero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import br.edu.utfpr.posjava.jwtbook.R;
import br.edu.utfpr.posjava.jwtbook.model.Genero;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.GeneroService;

public class CadastroGeneroActivity extends AppCompatActivity {

    private TextView tvIdGenero;
    private EditText etNome;
    private Button btExcluir;

    private Long idGenero;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro_genero );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tvIdGenero = ( TextView ) findViewById( R.id.tvIdGenero );
        etNome = ( EditText ) findViewById( R.id.etNomeCadastroGenero );

        btExcluir = ( Button ) findViewById( R.id.btExcluirGenero );

        if (getIntent().getStringExtra( "id" ) != null) {
            buscarGenero( Long.valueOf( getIntent().getStringExtra( "id" ) ) );
            btExcluir.setVisibility( View.VISIBLE );
        }
    }

    public void btSalvarGeneroOnClick( View view ) {
        salvar();
    }

    public void btExcluirGeneroOnClick( View view ) {
        remover();
    }

    private void salvar() {

        Genero genero = new Genero();
        if ( idGenero != null ) {
            genero.setId( idGenero );
        }
        genero.setNome( etNome.getText().toString() );

        GeneroService generoService = ServiceGenerator.createService( GeneroService.class );
        try {
            generoService.save( genero ).execute().body();
            exibirMensagem( "Gênero salvo com sucesso!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível gravar o gênero!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void remover() {

        GeneroService generoService = ServiceGenerator.createService( GeneroService.class );
        try {
            generoService.delete( idGenero ).execute().body();
            exibirMensagem( "Gênero excluído!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível excluir o gênero!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void buscarGenero( Long id ) {

        GeneroService generoService = ServiceGenerator.createService( GeneroService.class );
        try {
            Genero genero = generoService.getOne( id ).execute().body();
            tvIdGenero.setText( genero.getId().toString() );
            etNome.setText( genero.getNome() );

            idGenero = genero.getId();

        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível buscar os dados do gênero!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void exibirMensagem( String mensagem ) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void fecharOk() {
        Intent returnIntent = new Intent();
        setResult( Activity.RESULT_OK,returnIntent );
        finish();
    }

}
