package br.edu.utfpr.posjava.jwtbook.model;

/**
 * Created by talyssondecastro on 18/05/17.
 */

public class Livro {

    private Long id;

    private String titulo;

    private Integer ano;

    private Integer paginas;

    private String isbn;

    private Genero genero;

    private Editora editora;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo( String titulo ) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno( Integer ano ) {
        this.ano = ano;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas( Integer paginas ) {
        this.paginas = paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn( String isbn ) {
        this.isbn = isbn;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero( Genero genero ) {
        this.genero = genero;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora( Editora editora ) {
        this.editora = editora;
    }

    @Override
    public String toString() {
        return getId() + " - " + getTitulo();
    }
}
