package org.example.controller;

import org.example.model.Category;
import org.example.service.CategoryService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final CategoryService service = new CategoryService();

    @GET
    public List<Category> getAll(@QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("size") @DefaultValue("10") int size) {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Category category = service.findById(id);
        if (category == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(category).build();
    }

    @POST
    public Response create(Category category) {
        boolean saved = service.save(category);
        if (!saved) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(category).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Category category) {
        category.setId(id);
        boolean updated = service.update(category);
        if (!updated) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.deleteById(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/items")
    public Response getItemsByCategory(@PathParam("id") Long id) {
        List<?> items = service.getItemsByCategory(id);
        return Response.ok(items).build();
    }
}
