package model;

import java.io.Serializable;


public class Livro implements Serializable {
	public static final String DESCRICAO_PADRAO = "Novo Livro";
	private int id;
    private String titulo;
	private String autor;
	private int paginas;
	private float preco;
	
	public Livro() {
		this.id = -1;
		this.titulo = "";
		this.autor = "";
		this.paginas = '*';
	}
	
	public Livro(int id, String titulo,float preco, String autor, int paginas) {
		this.id = id;
		this.preco = preco;
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", paginas=" + paginas + "]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Livro) obj).getId());
	}	
}