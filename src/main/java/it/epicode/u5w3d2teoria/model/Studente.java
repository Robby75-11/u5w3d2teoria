package it.epicode.u5w3d2teoria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Studente {
    @Id
    @GeneratedValue
    private int matricola;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String urlImmagine;

    @ManyToOne
    @JoinColumn(name = "universita_id")
    private  Universita universita;

}
