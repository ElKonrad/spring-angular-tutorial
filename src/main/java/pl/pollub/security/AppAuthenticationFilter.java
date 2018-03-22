package pl.pollub.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pollub.dto.request.LoginRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            LoginRequest requestForm = obtainJSONLoginCredentials(request);
            String username = Optional.ofNullable(requestForm).map(e -> e.getUsername()).orElse(null);
            String password = Optional.ofNullable(requestForm).map(e -> e.getPassword()).orElse(null);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    private LoginRequest obtainJSONLoginCredentials(HttpServletRequest request) {

        StringBuilder builder = new StringBuilder();
        char[] charBuffer = new char[128];
        String requestContent;
        int bytesRead;

        try (BufferedReader bufferedReader = request.getReader()) {
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                builder.append(charBuffer, 0, bytesRead);
            }
            requestContent = builder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest requestForm = objectMapper.readValue(requestContent, LoginRequest.class);

            return requestForm;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        if (authResult != null) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        clearAuthenticationAttributes(request);

    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }
    }
}