package br.edu.utfpr.posjava.jwtbook.editora;

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
import br.edu.utfpr.posjava.jwtbook.model.Editora;
import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.EditoraService;
import retrofit2.Call;

public class ListaEditoraActivity extends AppCompatActivity {

    private static int REQUEST_CODE_CADASTRO = 1;

    private ListView lvListaEditora;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_editora );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lvListaEditora = ( ListView ) findViewById( R.id.lvListaEditora );

        lvListaEditora.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {

                Editora editora = ( Editora ) parent.getItemAtPosition( position );
                abrirCadastro( editora.getId() );

            }

        } );

        carregarRegistros();

    }

    private void carregarRegistros() {

        EditoraService editoraService = ServiceGenerator.createService(EditoraService.class, TokenResponse.getTokenSharedPreferences(this));
        Call<List<Editora> > call = editoraService.getAll();
        try {
            List< Editora > editoras = call.execute().body();
            ArrayAdapter<Editora> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, editoras);
            lvListaEditora.setAdapter(adapter);
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void btCadastrarEditoraOnClick( View view ) {
        abrirCadastro( null );
    }

    private void abrirCadastro( Long idEditora ) {

        Intent i = new Intent( this, CadastroEditoraActivity.class );
        i.putExtra( "id", idEditora != null ? idEditora.toString() : null );
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
