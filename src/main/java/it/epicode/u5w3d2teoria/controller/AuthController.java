package it.epicode.u5w3d2teoria.controller;

import it.epicode.u5w3d2teoria.dto.LoginDto;
import it.epicode.u5w3d2teoria.dto.UserDto;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.exception.ValidationException;
import it.epicode.u5w3d2teoria.model.User;
import it.epicode.u5w3d2teoria.service.AuthService;
import it.epicode.u5w3d2teoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new NotFoundException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (String s, String e)->s+e));
        }

      return userService.saveUser(userDto);

    }
    @GetMapping
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {


        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)-> s+e));
        }
        return authService.login(loginDto);
    }
}
