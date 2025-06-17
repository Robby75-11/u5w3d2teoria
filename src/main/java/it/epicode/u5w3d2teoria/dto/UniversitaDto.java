package it.epicode.u5w3d2teoria.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UniversitaDto {
    @NotEmpty(message ="il nome non può essere nullo e vuoto")
    private String nome;
    @NotEmpty(message ="la città non può essere nulla e vuota")
    private String citta;
}
