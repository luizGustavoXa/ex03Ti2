package service;

import java.io.IOException;


import dao.LivroDAO;
import model.Livro;
import spark.Request;
import spark.Response;


public class LivroService {

	private LivroDAO livroDAO;

	public LivroService() {
		try {
			livroDAO = new LivroDAO("livro.dat");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		float preco = Float.parseFloat(request.queryParams("preco"));
		String autor = request.queryParams("autor");
		int paginas = Integer.parseInt(request.queryParams("paginas"));


		int id = livroDAO.getMaxId() + 1;

		Livro livro = new Livro(id, titulo, preco, autor, paginas);

		livroDAO.add(livro);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Livro livro = (Livro) livroDAO.get(id);
		
		if (livro != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<livro>\n" + 
            		"\t<id>" + livro.getId() + "</id>\n" +
            		"\t<titulo>" + livro.getTitulo() + "</titulo>\n" +
            		"\t<preco>" + livro.getPreco() + "</preco>\n" +
            		"\t<autor>" + livro.getAutor() + "</autor>\n" +
            		"\t<paginas>" + livro.getPaginas() + "</paginas>\n" +
            		"</livro>\n";
        } else {
            response.status(404); // 404 Not found
            return "livro " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Livro livro = (Livro) livroDAO.get(id);

        if (livro != null) {
        	livro.setTitulo(request.queryParams("titulo"));
        	livro.setPreco(Float.parseFloat(request.queryParams("preco")));
        	livro.setAutor(request.queryParams("autor"));
        	livro.setPaginas(Integer.parseInt(request.queryParams("paginas")));
        	livroDAO.update(livro);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "livro não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Livro livro = (Livro) livroDAO.get(id);

        if (livro != null) {

            livroDAO.remove(livro);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "livro não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<livros type=\"array\">");
		for (Livro livro : livroDAO.getAll()) {
			returnValue.append("\n<livro>\n" + 
            		"\t<id>" + livro.getId() + "</id>\n" +
            		"\t<titulo>" + livro.getTitulo() + "</titulo>\n" +
            		"\t<preco>" + livro.getPreco() + "</preco>\n" +
            		"\t<autor>" + livro.getAutor() + "</autor>\n" +
            		"\t<paginas>" + livro.getPaginas() + "</paginas>\n" +
            		"</livro>\n");
		}
		returnValue.append("</livros>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}