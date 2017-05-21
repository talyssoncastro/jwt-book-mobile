package br.edu.utfpr.posjava.jwtbook.genero;

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
import br.edu.utfpr.posjava.jwtbook.model.Genero;
import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.GeneroService;
import retrofit2.Call;

public class ListaGeneroActivity extends AppCompatActivity {

    private static int REQUEST_CODE_CADASTRO = 1;

    private ListView lvListaGenero;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_genero );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lvListaGenero = ( ListView ) findViewById( R.id.lvListaGenero );

        lvListaGenero.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {

                Genero genero = ( Genero ) parent.getItemAtPosition( position );
                abrirCadastro( genero.getId() );

            }

        } );

        carregarRegistros();

    }

    private void carregarRegistros() {

        GeneroService generoService = ServiceGenerator.createService(GeneroService.class, TokenResponse.getTokenSharedPreferences(this));
        Call<List<Genero> > call = generoService.getAll();
        try {
            List< Genero > generos = call.execute().body();
            ArrayAdapter<Genero> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, generos);
            lvListaGenero.setAdapter(adapter);
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void btCadastrarGeneroOnClick( View view ) {
        abrirCadastro( null );
    }

    private void abrirCadastro( Long idGenero ) {

        Intent i = new Intent( this, CadastroGeneroActivity.class );
        i.putExtra( "id", idGenero != null ? idGenero.toString() : null );
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
