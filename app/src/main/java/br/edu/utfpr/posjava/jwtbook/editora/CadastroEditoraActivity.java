package br.edu.utfpr.posjava.jwtbook.editora;

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
import br.edu.utfpr.posjava.jwtbook.model.Editora;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.EditoraService;

public class CadastroEditoraActivity extends AppCompatActivity {

    private TextView tvIdEditora;
    private EditText etNome;
    private Button btExcluir;

    private Long idEditora;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro_editora );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tvIdEditora = ( TextView ) findViewById( R.id.tvIdEditora );
        etNome = ( EditText ) findViewById( R.id.etNomeCadastroEditora );

        btExcluir = ( Button ) findViewById( R.id.btExcluirEditora );

        if (getIntent().getStringExtra( "id" ) != null) {
            buscarEditora( Long.valueOf( getIntent().getStringExtra( "id" ) ) );
            btExcluir.setVisibility( View.VISIBLE );
        }

    }

    public void btSalvarEditoraOnClick( View view ) {
        salvar();
    }

    public void btExcluirEditoraOnClick( View view ) {
        remover();
    }

    private void salvar() {

        Editora editora = new Editora();
        if ( idEditora != null ) {
            editora.setId( idEditora );
        }
        editora.setNome( etNome.getText().toString() );

        EditoraService editoraService = ServiceGenerator.createService( EditoraService.class );
        try {
            editoraService.save( editora ).execute().body();
            exibirMensagem( "Editora salva com sucesso!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível gravar a editora!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void remover() {

        EditoraService editoraService = ServiceGenerator.createService( EditoraService.class );
        try {
            editoraService.delete( idEditora ).execute().body();
            exibirMensagem( "Editora excluída!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível excluir a editora!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void buscarEditora( Long id ) {

        EditoraService editoraService = ServiceGenerator.createService( EditoraService.class );
        try {
            Editora editora = editoraService.getOne( id ).execute().body();
            tvIdEditora.setText( editora.getId().toString() );
            etNome.setText( editora.getNome() );

            idEditora = editora.getId();

        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível buscar os dados da editora!" + e.getMessage() );
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
