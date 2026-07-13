package ale.cotini.springsecutiry.controllers;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.records.LoginDTO;
import ale.cotini.springsecutiry.records.LoginResponseDTO;
import ale.cotini.springsecutiry.services.AuthService;
import ale.cotini.springsecutiry.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO login){
       return new LoginResponseDTO(this.authService.checkCredentialAndGenerateToken(login));
    }
}




















