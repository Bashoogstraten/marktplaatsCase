package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GebruikerDto {

    private long id;
    private String gebruikersnaam;
    private String email;
    private String wachtwoord;

}

