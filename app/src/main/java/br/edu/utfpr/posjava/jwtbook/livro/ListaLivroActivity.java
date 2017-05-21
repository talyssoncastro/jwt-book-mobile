package br.edu.utfpr.posjava.jwtbook.livro;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import br.edu.utfpr.posjava.jwtbook.R;
import br.edu.utfpr.posjava.jwtbook.model.Livro;
import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.LivroService;
import retrofit2.Call;

public class ListaLivroActivity extends AppCompatActivity {

    private static int REQUEST_CODE_CADASTRO = 1;

    private ListView lvListaLivro;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_livro );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lvListaLivro = ( ListView ) findViewById( R.id.lvListaLivro );

        lvListaLivro.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {

                Livro livro = ( Livro ) parent.getItemAtPosition( position );
                abrirCadastro( livro.getId() );

            }

        } );

        carregarRegistros();
        
    }

    private void carregarRegistros() {

        LivroService livroService = ServiceGenerator.createService(LivroService.class, TokenResponse.getTokenSharedPreferences(this));
        Call<List<Livro> > call = livroService.getAll();
        try {
            List< Livro > livros = call.execute().body();
            ArrayAdapter<Livro> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, livros);
            lvListaLivro.setAdapter(adapter);
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void btCadastrarLivroOnClick( View view ) {
        abrirCadastro( null );
    }

    private void abrirCadastro( Long idLivro ) {

        Intent i = new Intent( this, CadastroLivroActivity.class );
        i.putExtra( "id", idLivro != null ? idLivro.toString() : null );
        startActivityForResult( i, REQUEST_CODE_CADASTRO );

    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {

        if ( requestCode == REQUEST_CODE_CADASTRO ) {

            if ( resultCode == RESULT_OK ) {
                carregarRegistros();
            }

        }

    }

}
