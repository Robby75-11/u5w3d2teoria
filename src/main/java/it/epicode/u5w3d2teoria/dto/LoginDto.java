package it.epicode.u5w3d2teoria.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "lo username non può essere vuoto")
    private String username;
    @NotEmpty(message = "la password non può essere vuota")
    private String password;
}
