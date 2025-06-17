package it.epicode.u5w3d2teoria.controller;

import it.epicode.u5w3d2teoria.dto.StudenteDto;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.exception.ValidationException;
import it.epicode.u5w3d2teoria.model.Studente;
import it.epicode.u5w3d2teoria.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/studenti")
public class StudenteController {

    @Autowired
    private StudenteService studenteService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")// hasAuthority autorizza un solo ruolo ad accedere all'endpoint
    @ResponseStatus(HttpStatus.CREATED)
    public Studente saveStudente(@RequestBody @Validated StudenteDto studenteDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return studenteService.saveStudente(studenteDto);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")//autorizza l'utilizzo dell'endpoint
    public Page<Studente> getAllStudenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "matricola") String sortBy){
        return studenteService.getAllStudenti(page, size, sortBy);
    }

    @GetMapping("/{matricola}")
    public Studente getStudente(@PathVariable int matricola)throws NotFoundException{
        return studenteService.getStudente(matricola);
    }

    @PutMapping("/{matricola}")
    public Studente updateStudente(@PathVariable int matricola, @RequestBody @Validated StudenteDto studenteDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return  studenteService.updateStudente(matricola, studenteDto);


    }

    public void  deleteStudente(@PathVariable int matricola) throws  NotFoundException {
        studenteService.deleteStudente(matricola);
    }

    @PatchMapping("/{matricola}")
    public String patchStudente(@PathVariable int matricola, @RequestBody MultipartFile file) throws NotFoundException, IOException{

        return studenteService.patchStudente(matricola, file);
    }
}
