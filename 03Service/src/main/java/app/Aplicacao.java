package app;

import static spark.Spark.*;

import service.LivroService;

public class Aplicacao {
	
	private static LivroService livroService = new LivroService();
	
    public static void main(String[] args) {
        port(6789);

        post("/livro", (request, response) -> livroService.add(request, response));

        get("/livro/:id", (request, response) -> livroService.get(request, response));

        get("/livro/update/:id", (request, response) -> livroService.update(request, response));

        get("/livro/delete/:id", (request, response) -> livroService.remove(request, response));

        get("/livro", (request, response) -> livroService.getAll(request, response));
               
    }
}