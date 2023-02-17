package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repository.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject AuthorRepository repository;

    @GET
    @Operation(summary = "Listar todos los autores")
    @APIResponse(responseCode = "200", description = "Conseguir autores", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Author.class)))
    public List<Author> findAll() {
        return repository.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = " Author por Id")
    @APIResponse(responseCode = "207", description = "Se ha encontrado el author", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "No se pudo encontrar el author")
    public Author findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    @Operation(description = "Create Author ")
    @APIResponse(responseCode = "201", description = "Se ha creado el author")
    @APIResponse(responseCode = "500", description = "No se pudo crear el author")
    public void insert(Author obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    @Operation(description = "Update Author")
    @APIResponse(responseCode = "207", description = "Se actualizo el Author")
    @APIResponse(responseCode = "500", description = "Presenta problemas con actualizacion")
    public void update(Author obj, @PathParam("id") Long id) {

        var author = repository.findById(id);

        author.setFirst_name(obj.getFirst_name());
        author.setLast_name(obj.getLast_name());
    }
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Author")
    @APIResponse(responseCode = "209",description = "Se ha eliminado el Author")
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
