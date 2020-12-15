package org.example.domain;

import org.example.resources.GebruikerStatus;
import org.example.utils.PasswordUtils;
import sun.security.util.Password;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Stateless
public class GebruikerDao {

    @PersistenceContext
    private EntityManager em;

    public List<Gebruiker> getAll() {
        return em.createNamedQuery("Gebruiker.findAll", Gebruiker.class).getResultList();
    }

    public List<GebruikerDto> getAllBasic() {
        return em.createNamedQuery("Gebruiker.findAllBasic", GebruikerDto.class).getResultList();
    }

    public Gebruiker getById(String id) { return null; }

    public Collection<Gebruiker> get(String q) {
        return null;
    }

    public GebruikerDto getMetGebruikersnaam(String username) {
        TypedQuery<GebruikerDto> query = em.createQuery("SELECT new org.example.domain.GebruikerDto(e.gebruikersnaam, e.email, e.wachtwoord) FROM Gebruiker e WHERE e.gebruikersnaam = :un", GebruikerDto.class);
        query.setParameter("un", username);
        return query.getSingleResult();

    }

    public boolean add(Gebruiker c) {
        c.setStatus(GebruikerStatus.ACTIEF);
        String unhashed = c.getWachtwoord();
        c.setWachtwoord(PasswordUtils.digestPassword(unhashed));
        em.persist(c);
        return true;
    }

    public boolean remove(long id) {
        Gebruiker c = em.find(Gebruiker.class, id);
        em.remove(c);
        return true;
    }

    public Gebruiker update(long id, Gebruiker c) {
        Gebruiker b = em.find(Gebruiker.class, id);
        b.setEmail(c.getEmail());
        b.setGebruikersnaam(c.getGebruikersnaam());
        b.setWachtwoord(c.getWachtwoord());
        em.merge(b);
        return b;
    }

}
