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

    @Override
    public String toString() {
        return "Advertentie {" +
                "id = " + id +
                ", titel = '" + titel + '\'' +
                ", omschrijving = '" + omschrijving + '\'' +
                ", prijs = " + prijs +
                ", advertentieStatus= " + advertentieStatus +
                '}';
    }
}
