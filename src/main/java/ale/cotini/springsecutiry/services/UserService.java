package ale.cotini.springsecutiry.services;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.records.UserDTO;
import ale.cotini.springsecutiry.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bcrypt;

    public UserService(UserRepository userRepository, PasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    public User save(UserDTO body){
      if(this.userRepository.existsByEmail(body.email()))
          throw new RuntimeException("Email già utilizzata");
      User nuovo = new User(body.name(), body.email(), this.bcrypt.encode(body.password()));
      User salvato = userRepository.save(nuovo);
      return salvato;
    }

    public User findById (UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Utente non trovato"));
    }

    public User findByEmail (String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email non trovata"));
    }

    public List<User> findAll(){
       return this.userRepository.findAll();
    }
}
