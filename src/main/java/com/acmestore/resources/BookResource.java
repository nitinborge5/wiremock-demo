package com.acmestore.resources;

import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 5/15/17.
 */
@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private Map<String, Map<String, Object>> books = new HashMap<>();
    private Map<String, Integer> bookViews = new HashMap<>();
    private final String endpoint;

    public BookResource(String endpoint) {
        this.endpoint = endpoint;
    }

    @GET
    @Path("/get")
    public Response getBook(@NotNull @QueryParam("isbn") String isbn) {
        Map<String, Object> book = books.get(isbn);

        if (book == null) {
            System.out.println("MISS: " + isbn);

            Client client = ClientBuilder.newClient();
            Response response = client.target(this.endpoint + "/api/books")
                    .queryParam("bibkeys", "ISBN:" + isbn)
                    .queryParam("format", "json")
                    .queryParam("jscmd", "data")
                    .request().accept(MediaType.APPLICATION_JSON).buildGet().invoke();
            book = response.readEntity(Map.class);
            books.put(isbn, book);
        } else {
            System.out.println("HIT: " + isbn);
        }

        Integer viewCount = bookViews.getOrDefault(isbn, 0);
        viewCount += 1;
        bookViews.put(isbn, viewCount);

        Map<String, Object> data = new HashMap<>();
        data.put("viewCount", viewCount.toString());
        data.put("book", book);

        return Response.ok().entity(data).build();
    }
}
