package org.example.controller;

import org.example.model.Item;
import org.example.service.ItemService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemService service = new ItemService();

    @GET
    public List<Item> getAll(@QueryParam("page") @DefaultValue("0") int page,
                             @QueryParam("size") @DefaultValue("10") int size,
                             @QueryParam("categoryId") Long categoryId) {
        if (categoryId != null) {
            return service.getItemsByCategory(categoryId);
        }
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Item item = service.findById(id);
        if (item == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(item).build();
    }

    @POST
    public Response create(Item item) {
        boolean saved = service.save(item);
        if (!saved) return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Item item) {
        item.setId(id);
        boolean updated = service.update(item);
        if (!updated) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(item).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.deleteById(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}
