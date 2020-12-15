package org.example.resources;

import org.example.App;
import org.example.domain.Advertentie;
import org.example.domain.Gebruiker;
import org.example.domain.GebruikerDao;
import org.example.domain.GebruikerDto;
import org.example.utils.PasswordUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URL;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class ContactsResourceIT {

    @ArquillianResource
    private URL deploymentURL;

    private String contactsResource;
    private String contactsUri = "recycle/gebruikers";

    @Before
    public void setup() {
        contactsResource = deploymentURL + contactsUri;
    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "ContactsResourceIT.war")
                .addPackages(true, App.class.getPackage()) // dont forget!
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");

        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void whenGebruikerIsPostedICanGetIt() {
        Client http = ClientBuilder.newClient();
        GebruikerDto g = GebruikerDto.builder().gebruikersnaam("pietje").wachtwoord("wachtwoord").email("pietje@live.nl").build();

        String postedContact = http
                .target(contactsResource)
                .request().post(entity(g, APPLICATION_JSON), String.class);

        System.out.println(postedContact);

        String allContacts = http
                .target(contactsResource)
                .request().get(String.class);

        System.out.println(allContacts);
        String hashedWachtwoord = PasswordUtils.digestPassword(g.getWachtwoord());

        assertThat(allContacts, containsString("\"gebruikersnaam\":\"pietje\""));
        assertThat(allContacts, containsString("\"wachtwoord\":\"" + hashedWachtwoord + "\""));
        assertThat(allContacts, containsString("\"email\":\"pietje@live.nl\""));
    }

    @Test
    public void get() {
        Client http = ClientBuilder.newClient();
        GebruikerDto g = GebruikerDto.builder().gebruikersnaam("Sammie").wachtwoord("Smith").email("sam.smith@music.com").build();

        String postedContact = http
                .target(contactsResource)
                .request().post(entity(g, APPLICATION_JSON), String.class);

        String message = http
                .target(contactsResource)
                .request().get(String.class);

        assertThat(message, containsString("gebruikersnaam"));
        assertThat(message, containsString("wachtwoord"));
        assertThat(message, containsString("email"));
    }
}
