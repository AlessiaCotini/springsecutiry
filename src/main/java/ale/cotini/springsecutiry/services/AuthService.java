package ale.cotini.springsecutiry.services;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.records.LoginDTO;
import ale.cotini.springsecutiry.security.JWTTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTTools jwtTools;

    public AuthService(UserService userService, JWTTools jwtTools) {
        this.userService = userService;
        this.jwtTools = jwtTools;
    }

    public String checkCredentialAndGenerateToken(LoginDTO body){

        // CONTROLLO LE CREDENZIALI
        User trovato = this.userService.findByEmail(body.email());
        if (trovato.getPassword().equals(body.password())){
            // GENERO TOKEN
            return this.jwtTools.generoToken(trovato);
        } else {
            // SE NON E' TUTTO APPOSTO 401
            throw new RuntimeException("Credenziali errate");
        }







    }
}
