package org.example.resources;

import org.example.domain.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;

@Path("/advertenties")
public class AdvertentiesResource implements JsonResource {

    @Inject
    private AdvertentieDao dao;

    @GET
    public Collection<Advertentie> getAll(@QueryParam("q") String q) {

        return q == null ? dao.getAll() : dao.get(q);
    }

    @GET @Path("basic")
    public Collection<AdvertentieDto> getAllBasic(@QueryParam("q") String q) {

        return q == null ? dao.getAllBasic() : dao.getBasic(q);
    }

    @GET @Path("basic/{id}")
    public Collection<AdvertentieDto> getAllGebruiker(@PathParam("id") long id) {

        return dao.getBasicGebruiker(id);
    }

    @POST
    public Advertentie post(Advertentie a) {
        if (dao.add(a)) {
            return a;
        } else {
            throw new RuntimeException("Post contact " + a + " failed.");
        }
    }

    @DELETE
    @Path("basic/{id}")
    public void delete(@PathParam("id") long id) {
        if (!dao.remove(id)) {
            throw new RuntimeException("Delete contact with id " + id + " failed.");
        }
    }

    @PUT
    @Path("basic/{id}")
    public Advertentie put(@PathParam("id") long id, Advertentie a) {
        return dao.update(id, a);

    }

}
