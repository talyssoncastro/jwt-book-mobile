package br.edu.utfpr.posjava.jwtbook.model;

/**
 * Created by talyssondecastro on 18/05/17.
 */

public class Genero {

    private Long id;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof Genero ) ) return false;

        Genero genero = ( Genero ) o;

        return getId() != null ? getId().equals( genero.getId() ) : genero.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
