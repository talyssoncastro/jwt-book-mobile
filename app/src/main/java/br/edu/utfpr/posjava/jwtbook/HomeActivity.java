package br.edu.utfpr.posjava.jwtbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.edu.utfpr.posjava.jwtbook.editora.ListaEditoraActivity;
import br.edu.utfpr.posjava.jwtbook.genero.ListaGeneroActivity;
import br.edu.utfpr.posjava.jwtbook.livro.ListaLivroActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
    }

    public void btEditoraOnClick( View view ) {
        Intent intent = new Intent(this, ListaEditoraActivity.class);
        startActivity(intent);
    }

    public void btGeneroOnClick( View view ) {
        Intent intent = new Intent(this, ListaGeneroActivity.class);
        startActivity(intent);
    }

    public void btLivroOnClick( View view ) {
        Intent intent = new Intent(this, ListaLivroActivity.class);
        startActivity(intent);
    }
}
