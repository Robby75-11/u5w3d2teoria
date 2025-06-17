package it.epicode.u5w3d2teoria.service;

import com.cloudinary.Cloudinary;
import it.epicode.u5w3d2teoria.dto.StudenteDto;
import it.epicode.u5w3d2teoria.exception.NotFoundException;
import it.epicode.u5w3d2teoria.model.Studente;
import it.epicode.u5w3d2teoria.model.Universita;
import it.epicode.u5w3d2teoria.repository.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private UniversitaService universitaService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Studente saveStudente(StudenteDto studenteDto) throws NotFoundException {
        //studenteDto contiene solo nome, cognome, dataNascita e universitaId
        //cercare l'universita in base all'id
        Universita universita = universitaService.getUniversita(studenteDto.getUniversitaId());

        //creo uno studente dell'Entità Studente e gli setto tutti i dati del dto e dell'universita
        Studente studente = new Studente();
        studente.setNome(studenteDto.getNome());
        studente.setCognome(studenteDto.getCognome());
        studente.setDataNascita(studenteDto.getDataNascita());
        studente.setUniversita(universita);

        sendMail("ralbergo7@gmail.com");

        return studenteRepository.save(studente);
    }

    public Page<Studente> getAllStudenti(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return studenteRepository.findAll(pageable);
    }

    public Studente getStudente(int matricola) throws NotFoundException {
        return studenteRepository.findById(matricola).
                orElseThrow(() -> new NotFoundException("Studente con matricola " + matricola + " non presente"));
    }

    public Studente updateStudente(int matricola, StudenteDto studenteDto) throws NotFoundException {
        Studente studenteDaAggiornare = getStudente(matricola);

        studenteDaAggiornare.setNome(studenteDto.getNome());
        studenteDaAggiornare.setCognome(studenteDto.getCognome());
        studenteDaAggiornare.setDataNascita(studenteDto.getDataNascita());
        /*
        nel dto io ho anche universitaId, quindi devo verificare se l'id dell'università collegata allo studente
        sul db è diversa dall'id dell'università nel dto. Se sono differenti, vuol dire che lo studente ha
        cambiato università e quindi vado a cercare questa nuova università sul db e se la trovo la setto sullo
        studente da aggiornare
         */
        if(studenteDaAggiornare.getUniversita().getId()!=studenteDto.getUniversitaId()){
            Universita universita = universitaService.getUniversita(studenteDto.getUniversitaId());
            studenteDaAggiornare.setUniversita(universita);
        }

        return studenteRepository.save(studenteDaAggiornare);
    }

    public void deleteStudente(int matricola) throws NotFoundException {
        Studente studenteDaCancellare = getStudente(matricola);

        studenteRepository.delete(studenteDaCancellare);
    }

    public String patchStudente(int matricola, MultipartFile file) throws NotFoundException, IOException {
        Studente studenteDaPatchare = getStudente(matricola);
        //salvo il file su cloudinary e ricevo l'url del file che si trova su cloudinary
        String url= (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        studenteDaPatchare.setUrlImmagine(url);

        studenteRepository.save(studenteDaPatchare);

        return url;
    }
    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
    }



