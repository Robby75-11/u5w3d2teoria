package it.epicode.u5w3d2teoria.service;

import it.epicode.u5w3d2teoria.dto.UniversitaDto;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.model.Universita;
import it.epicode.u5w3d2teoria.repository.UniversitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversitaService {

    @Autowired
    private UniversitaRepository universitaRepository;

    public Universita saveUniversita(UniversitaDto universitaDto){

        Universita universita = new Universita();
        universita.setNome(universitaDto.getNome());
        universita.setCitta(universitaDto.getCitta());

        return universitaRepository.save(universita);
    }


    public List<Universita> getAllUniversita(){
        return universitaRepository.findAll();

    }

    public Universita getUniversita(int id) throws NotFoundException{
        return universitaRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Universita con id " + " non trovata"));
    }

    public Universita updateUniversita(int id, UniversitaDto universitaDto) throws NotFoundException{
        Universita universitaDaAggiornare = getUniversita(id);

        universitaDaAggiornare.setNome(universitaDto.getNome());
        universitaDaAggiornare.setCitta(universitaDto.getCitta());

        return universitaRepository.save(universitaDaAggiornare);
    }

    public void
    deleteUniversita(int id) throws NotFoundException {
        Universita universitaDaCancellare = getUniversita(id);

        universitaRepository.delete(universitaDaCancellare);

    }
}
