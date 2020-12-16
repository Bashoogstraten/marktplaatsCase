package org.example.domain;

import org.example.resources.AdvertentieStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Stateless
public class AdvertentieDao {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private GebruikerDao gebDao;

    public List<Advertentie> getAll() {
        return em.createNamedQuery("Advertentie.findAll", Advertentie.class).getResultList();
    }

    public List<AdvertentieDto> getAllBasic() {
        return em.createNamedQuery("Advertentie.findAllBasic", AdvertentieDto.class).getResultList();
    }

    public Advertentie getById(String id) {
        return null;
    }

    public Collection<Advertentie> get(String q) {
        return null;
    }

    public List<AdvertentieDto> getBasic(String zoekterm) {

        TypedQuery<AdvertentieDto> namedQuery = em.createNamedQuery("Advertentie.findBySearchterm", AdvertentieDto.class);
        namedQuery.setParameter("titel", "%" + zoekterm + "%");
//        namedQuery.setParameter("omschrijving", "'%" + zoekterm + "%'");
        return namedQuery.getResultList();
    }

    public List<AdvertentieDto> getBasicGebruiker(long id) {

        TypedQuery<AdvertentieDto> namedQuery = em.createNamedQuery("Advertentie.findBasicGebruiker", AdvertentieDto.class);
        namedQuery.setParameter("gb", id);
        return namedQuery.getResultList();

    }

    public boolean add(long id, Advertentie a) {
        a.setAdvertentieStatus(AdvertentieStatus.BESCHIKBAAR);
        a.setAanbieder(gebDao.getById(id));
        em.persist(a);
        return true;
    }

    public boolean remove(long id) {
        Advertentie a = em.find(Advertentie.class, id);
        em.remove(a);
        return true;
    }

    public Advertentie update(long id, Advertentie a) {
        Advertentie b = em.find(Advertentie.class, id);
        b.setTitel(a.getTitel());
        b.setOmschrijving(a.getOmschrijving());
        b.setPrijs(a.getPrijs());
        em.merge(b);
        return b;
    }

}
