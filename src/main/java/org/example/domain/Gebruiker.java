package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.resources.GebruikerStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({@NamedQuery(name = "Gebruiker.findAll", query = "select c from Gebruiker c"),
        @NamedQuery(name = "Gebruiker.findAllBasic", query = "select new org.example.domain.GebruikerDto(c.id, c.gebruikersnaam, c.email, c.wachtwoord) from Gebruiker c")})

public class Gebruiker {

    @Id
    @GeneratedValue
    private long id;

    private String gebruikersnaam;
    private String email;
    private String wachtwoord;

    @Enumerated(value = EnumType.STRING)
    private GebruikerStatus status;

    @OneToMany(mappedBy = "aanbieder", cascade = {PERSIST, MERGE, REMOVE})
    private List<Advertentie> advertenties = new LinkedList<>();

    @OneToMany(mappedBy = "koper", cascade = {PERSIST, MERGE, REMOVE})
    private List<Advertentie> winkelwagen = new LinkedList<>();

}
