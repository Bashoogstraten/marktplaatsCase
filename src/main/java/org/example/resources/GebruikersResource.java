package org.example.resources;

import org.example.domain.Gebruiker;
import org.example.domain.GebruikerDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;

@Path("/gebruikers")
public class GebruikersResource implements JsonResource {

    @Inject
    private GebruikerDao dao;

    @GET
    public Collection<Gebruiker> getAll(@QueryParam("q") String q) {

        return q == null ? dao.getAll() : dao.get(q);
    }

    @POST
    public Gebruiker post(Gebruiker c) {
        if (dao.add(c)) {
            return c;
        } else {
            throw new RuntimeException("Post contact " + c + " failed.");
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        if (!dao.remove(id)) {
            throw new RuntimeException("Delete contact with id " + id + " failed.");
        }
    }

    @PUT
    @Path("{id}")
    public Gebruiker put(@PathParam("id") long id, Gebruiker c) {
        return dao.update(id, c);

    }

}
