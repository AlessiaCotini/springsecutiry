package ale.cotini.springsecutiry.security;

import ale.cotini.springsecutiry.entities.User;
import ale.cotini.springsecutiry.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final JWTTools jwtTools;
    private final UserService userService;

    public TokenFilter(JWTTools jwtTools, UserService userService) {
        this.jwtTools = jwtTools;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // ATTRAVERSO LA RICHIESTA GESTISCO IL TOKEN

            String authorizationHeader = request.getHeader("Authorization");

            // CHE NON SIA NULLO, CHE INIZI CON IL FORMATO CORRETTO
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
                throw new RuntimeException("Controlla il formato : Bearer ");

            // ESTRAPOLO IL TOKEN
            String accessToken = authorizationHeader.replace("Bearer ", "");

            // VERIFICO IL TOKEN
            this.jwtTools.verificoToken(accessToken);

            //AUTHORIZATION - associo user a context

            UUID userId = this.jwtTools.checkIdDalToken(accessToken);
            User autenticato = this.userService.findById(userId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(autenticato, null , autenticato.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ARRIVO AL CONTROLLER

            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e + "ERRORE NEL FILTRO DEL TOKEN");
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // SPECIFICO QUANDO IL FILTRO NON DEVE INTERVENIRE QUINDI SIA LOGIN CHE REGISTRAZIONE
        // GENERALE SU CIO' CHE E' DOPO AUTORIZZAZZIONE

        return new AntPathMatcher().match("/authorization/**", request.getServletPath());
    }
}
