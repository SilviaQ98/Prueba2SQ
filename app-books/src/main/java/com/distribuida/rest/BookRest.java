package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookRespository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;


@ApplicationScoped
@Path("/books")
public class BookRest {
    @Inject
    private BookRespository bookRespository;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Books")
    @APIResponse(responseCode = "207", description = "Todos los libros", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Book.class)))
    public List<Book> findAll() {
        return bookRespository.getBooks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Book")
    @APIResponse(responseCode = "207", description = "Se ha encontrado el libro", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "400", description = "No se pudo encontrar el libro")
    public Book findById(@Parameter(description = "ID BOOK", required = true) @PathParam("id") Integer id)  {
        return bookRespository.getBookById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Create Book")
    @APIResponse(responseCode = "201", description = "Se ha creado el libro")
    @APIResponse(responseCode = "500", description = "Presenta problemas con el servidor")
    public Response create(
            @RequestBody(description = "Libro a Ingresar", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b){
        bookRespository.creteBook(b);
        return Response.status(Response.Status.CREATED).build();
    }
    @PUT
    @Path("/{id}")
    @Operation(description = "Update Book")
    @APIResponse(responseCode = "207", description = "Se actualizo el libro")
    @APIResponse(responseCode = "500", description = "Presenta problemas de actualizacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(
            @RequestBody(description = "Datos del libro a modificar", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b,
            @Parameter(description = "ID BOOK", required = true)
            @PathParam("id") Integer id){
        bookRespository.updateBook(id, b);
        return Response.status((Response.Status.OK)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(description = "Delete Book")
    @APIResponse(responseCode = "209", description = "Se ha eliminado el libro")
    @APIResponse(responseCode = "500", description = "Presenta problemas con el servidor")
    public Response delete(
            @Parameter(description = "ID BOOK", required = true)
            @PathParam("id") Integer id) {
        bookRespository.delete(id);
        return Response.status((Response.Status.NO_CONTENT)).build();
    }
}
