package dao;

import model.Livro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LivroDAO{
	private List<Livro> livros;
	private int maxId = 0;

	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public int getMaxId() {
		return maxId;
	}

	public LivroDAO(String filename) throws IOException {
		file = new File(filename);
		livros = new ArrayList<Livro>();
		if (file.exists()) {
			readFromFile();
		}

	}

	public void add(Livro livro) {
		try {
			livros.add(livro);
			this.maxId = (livro.getId() > this.maxId) ? livro.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o livro '" + livro.getTitulo() + "' no disco!");
		}
	}

	public Livro get(int id) {
		for (Livro livro : livros) {
			if (id == livro.getId()) {
				return livro;
			}
		}
		return null;
	}

	public void update(Livro p) {
		int index = livros.indexOf(p);
		if (index != -1) {
			livros.set(index, p);
			this.saveToFile();
		}
	}

	public void remove(Livro p) {
		int index = livros.indexOf(p);
		if (index != -1) {
			livros.remove(index);
			this.saveToFile();
		}
	}

	public List<Livro> getAll() {
		return livros;
	}

	private List<Livro> readFromFile() {
		livros.clear();
		Livro livro = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				livro = (Livro) inputFile.readObject();
				livros.add(livro);
				maxId = (livro.getId() > maxId) ? livro.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Livro no disco!");
			e.printStackTrace();
		}
		return livros;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Livro livro : livros) {
				outputFile.writeObject(livro);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Livro no disco!");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			this.saveToFile();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao salvar a base de dados no disco!");
			e.printStackTrace();
		}
	}
}