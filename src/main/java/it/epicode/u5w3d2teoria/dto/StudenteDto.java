package it.epicode.u5w3d2teoria.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudenteDto {
    @NotEmpty(message ="il nome non può essere nullo e vuoto")
    private String nome;
    @NotEmpty(message = "il cognome nn puo essere nullo e vuoto")
    private String cognome;
    @NotNull(message = "la data non può esse nulla")
    private LocalDate dataNascita;
    private int universitaId;
}
