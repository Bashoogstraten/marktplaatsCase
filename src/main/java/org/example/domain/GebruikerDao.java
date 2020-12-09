package org.example.domain;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

// @Alternative
// @Default
@Stateless
public class GebruikerDao {

    @PersistenceContext // Container managed persistence context
    private EntityManager em;

    public List<Gebruiker> getAll() {
        return em.createNamedQuery("Gebruiker.findAll", Gebruiker.class).getResultList();
    }

    public Gebruiker getById(String id) { return null; }

    public Collection<Gebruiker> get(String q) {
        return null;
    }

    public boolean add(Gebruiker c) {
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
