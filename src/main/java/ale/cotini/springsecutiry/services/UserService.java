package ale.cotini.springsecutiry.services;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.records.UserDTO;
import ale.cotini.springsecutiry.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDTO body){
      if(this.userRepository.existsByEmail(body.email()))
          throw new RuntimeException("Email già utilizzata");
      User nuovo = new User(body.name(), body.email(), body.password());
      User salvato = userRepository.save(nuovo);
      return salvato;
    }

    public User findById (UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Utente non trovato"));
    }

    public User findByEmail (String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Email non trovata"));
    }
}
