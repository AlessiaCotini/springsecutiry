package ale.cotini.springsecutiry.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final JWTTools jwtTools;

    public TokenFilter(JWTTools jwtTools) {
        this.jwtTools = jwtTools;
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
