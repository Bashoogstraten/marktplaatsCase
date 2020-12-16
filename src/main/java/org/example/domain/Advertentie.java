package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.resources.AdvertentieStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Advertentie.findAll", query = "select c from Advertentie c"),
        @NamedQuery(name = "Advertentie.findAllBasic", query = "select new org.example.domain.AdvertentieDto(c.id, c.titel, c.omschrijving, c.prijs) from Advertentie c"),
@NamedQuery(name = "Advertentie.findBySearchterm", query = "select new org.example.domain.AdvertentieDto(c.id, c.titel, c.omschrijving, c.prijs) from Advertentie c where c.titel like :titel or c.omschrijving like :titel"),
@NamedQuery(name = "Advertentie.findBasicGebruiker", query = "select new org.example.domain.AdvertentieDto(c.id, c.titel, c.omschrijving, c.prijs) from Advertentie c join c.aanbieder g where g.id = :gb")})
public class Advertentie {

    @Id
    @GeneratedValue
    private long id;

    private String titel;
    private String omschrijving;
    private BigDecimal prijs;

    @ManyToOne
    private Gebruiker aanbieder;

    @ManyToOne
    private Gebruiker koper;

    @Enumerated(value = EnumType.STRING)
    private AdvertentieStatus advertentieStatus;

}
