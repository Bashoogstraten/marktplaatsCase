package org.example.resources;

import org.example.domain.Gebruiker;
import org.example.domain.GebruikerDao;
import org.example.domain.GebruikerDto;
import org.example.utils.PasswordUtils;

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

    @GET @Path("basic")
    public Collection<GebruikerDto> getAllBasic() {

        return dao.getAllBasic();
    }

    @POST
    public Gebruiker post(Gebruiker c) {
        if (dao.add(c)) {
            return c;
        } else {
            throw new RuntimeException("Post contact " + c + " failed.");
        }
    }

    @POST @Path("login")
    public GebruikerDto login(GebruikerDto c) {
        String hashedWachtwoord = PasswordUtils.digestPassword(c.getWachtwoord());
        GebruikerDto e = dao.getMetGebruikersnaam(c.getGebruikersnaam());

        if (hashedWachtwoord.equals(e.getWachtwoord())) {
            return e;
        } else {
            throw new RuntimeException("Login contact " + c + "failed.");
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
