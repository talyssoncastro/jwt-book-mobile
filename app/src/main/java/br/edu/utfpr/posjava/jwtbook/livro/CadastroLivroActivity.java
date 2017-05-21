package br.edu.utfpr.posjava.jwtbook.livro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.edu.utfpr.posjava.jwtbook.R;
import br.edu.utfpr.posjava.jwtbook.model.Editora;
import br.edu.utfpr.posjava.jwtbook.model.Genero;
import br.edu.utfpr.posjava.jwtbook.model.Livro;
import br.edu.utfpr.posjava.jwtbook.model.TokenResponse;
import br.edu.utfpr.posjava.jwtbook.security.ServiceGenerator;
import br.edu.utfpr.posjava.jwtbook.service.EditoraService;
import br.edu.utfpr.posjava.jwtbook.service.GeneroService;
import br.edu.utfpr.posjava.jwtbook.service.LivroService;
import retrofit2.Call;

public class CadastroLivroActivity extends AppCompatActivity {

    private TextView tvIdLivro;
    private EditText etTitulo;
    private EditText etAno;
    private EditText etPaginas;
    private EditText etISBN;
    private Spinner spEditora;
    private Spinner spGenero;
    private Button btExcluir;

    private ArrayAdapter<Editora> adapterEditora;
    private ArrayAdapter<Genero> adapterGenero;

    private Long idLivro;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro_livro );

        // Código para permitir request ao backend na thread Main
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tvIdLivro = ( TextView ) findViewById( R.id.tvIdLivro );
        etTitulo = ( EditText ) findViewById( R.id.etTituloCadastroLivro );
        etAno = ( EditText ) findViewById( R.id.etAnoCadastroLivro );
        etPaginas = ( EditText ) findViewById( R.id.etPaginasCadastroLivro );
        etISBN = ( EditText ) findViewById( R.id.etISBNCadastroLivro );
        spEditora = ( Spinner ) findViewById( R.id.spEditoraCadastroLivro );
        spGenero = ( Spinner ) findViewById( R.id.spGeneroCadastroLivro );

        carregarEditoras();
        carregarGeneros();

        btExcluir = ( Button ) findViewById( R.id.btExcluirLivro );

        if (getIntent().getStringExtra( "id" ) != null) {
            buscarLivro( Long.valueOf( getIntent().getStringExtra( "id" ) ) );
            btExcluir.setVisibility( View.VISIBLE );
        }

    }

    public void btSalvarLivroOnClick( View view ) {
        salvar();
    }

    public void btExcluirLivroOnClick( View view ) {
        remover();
    }

    private void salvar() {

        Livro livro = new Livro();
        if ( idLivro != null ) {
            livro.setId( idLivro );
        }
        livro.setTitulo( etTitulo.getText().toString() );
        livro.setAno( Integer.valueOf( etAno.getText().toString() ) );
        livro.setPaginas( Integer.valueOf( etPaginas.getText().toString() ) );
        livro.setIsbn(  etISBN.getText().toString() );
        livro.setEditora( ( Editora ) spEditora.getSelectedItem() );
        livro.setGenero( ( Genero ) spGenero.getSelectedItem() );

        LivroService livroService = ServiceGenerator.createService( LivroService.class );
        try {
            livroService.save( livro ).execute().body();
            exibirMensagem( "Livro salvo com sucesso!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível gravar o livro!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void remover() {

        LivroService livroService = ServiceGenerator.createService( LivroService.class );
        try {
            livroService.delete( idLivro ).execute().body();
            exibirMensagem( "Livro excluído!" );
            fecharOk();
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível excluir o livro!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void buscarLivro( Long id ) {

        LivroService livroService = ServiceGenerator.createService( LivroService.class );
        try {
            Livro livro = livroService.getOne( id ).execute().body();
            tvIdLivro.setText( livro.getId().toString() );
            etTitulo.setText( livro.getTitulo() );
            etAno.setText( livro.getAno().toString() );
            etPaginas.setText( livro.getPaginas().toString() );
            etISBN.setText( livro.getIsbn().toString() );
            spEditora.setSelection( adapterEditora.getPosition( livro.getEditora() ) );
            spGenero.setSelection( adapterGenero.getPosition( livro.getGenero() ) );

            idLivro = livro.getId();

        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível buscar os dados do livro!" + e.getMessage() );
            e.printStackTrace();
        }

    }

    private void exibirMensagem( String mensagem ) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void carregarEditoras() {

        EditoraService editoraService = ServiceGenerator.createService(EditoraService.class, TokenResponse.getTokenSharedPreferences(this));
        Call<List<Editora> > call = editoraService.getAll();
        try {
            List< Editora > editoras = call.execute().body();
            adapterEditora = new ArrayAdapter(this, android.R.layout.simple_list_item_1, editoras);
            spEditora.setAdapter( adapterEditora );
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível carregar editoras! " + e.getMessage() );
        }

    }

    private void carregarGeneros() {

        GeneroService generoService = ServiceGenerator.createService(GeneroService.class, TokenResponse.getTokenSharedPreferences(this));
        Call<List<Genero> > call = generoService.getAll();
        try {
            List< Genero > generos = call.execute().body();
            adapterGenero = new ArrayAdapter(this, android.R.layout.simple_list_item_1, generos);
            spGenero.setAdapter( adapterGenero );
        } catch ( IOException e ) {
            exibirMensagem( "Não foi possível carregar gêneros! " + e.getMessage() );
        }

    }

    private void fecharOk() {
        Intent returnIntent = new Intent();
        setResult( Activity.RESULT_OK,returnIntent );
        finish();
    }
    
}
