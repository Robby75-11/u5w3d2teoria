package it.epicode.u5w3d2teoria.service;


import it.epicode.u5w3d2teoria.dto.UniversitaDto;
import it.epicode.u5w3d2teoria.dto.UserDto;
import it.epicode.u5w3d2teoria.enumeration.Role;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.model.Universita;
import it.epicode.u5w3d2teoria.model.User;
import it.epicode.u5w3d2teoria.repository.UniversitaRepository;
import it.epicode.u5w3d2teoria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto){
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        //la password in chiaro che si trova nel dto, verrà passata come parametro al metodo encode dell'encoder
        //Bcrypt codificherà la password e generà un codice criptato
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);


        return userRepository.save(user);

    }

    public List<User> getAllUser(){

        return userRepository.findAll();
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setUsername(userDto.getUsername());
        if(!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);

        userRepository.delete(userDaCancellare);
    }
}