package it.epicode.u5w3d2teoria.controller;


import it.epicode.u5w3d2teoria.dto.UniversitaDto;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.exception.ValidationException;
import it.epicode.u5w3d2teoria.model.Universita;
import it.epicode.u5w3d2teoria.service.UniversitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/universita")
public class UniversitaController{

    @Autowired
    private UniversitaService universitaService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Universita saveUniversita(@RequestBody @Validated UniversitaDto universitaDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return universitaService.saveUniversita(universitaDto);
    }

    @GetMapping("")
    public List<Universita> getAllUniversita(){
        return universitaService.getAllUniversita();
    }

    @GetMapping("/{id}")
    public Universita getUniversita(@PathVariable int id) throws NotFoundException{
        return universitaService.getUniversita(id);
    }
    @PutMapping("/{matricola}")
    public Universita updateUniversita(@PathVariable int id, @RequestBody @Validated UniversitaDto universitaDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }
        return  universitaService.updateUniversita(id, universitaDto);
    }

    @DeleteMapping("/{id}")
    public void  deleteUniversita(@PathVariable int id) throws  NotFoundException {
        universitaService.deleteUniversita(id);
    }
}
