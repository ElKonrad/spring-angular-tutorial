package pl.pollub.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.pollub.service.UserService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AppAuthenticationEntryPoint entryPoint;
    private AppLogoutSuccessHandler logoutSuccessHandler;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Bean
    AppAuthenticationFilter authenticationFilter() throws Exception {
        AppAuthenticationFilter appAuthenticationFilter = new AppAuthenticationFilter();
        appAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        appAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        return appAuthenticationFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                /*Login only with prefix api*/
                .authorizeRequests().antMatchers(HttpMethod.POST, "/login").denyAll()

                /*Register as anonymous user */
                .antMatchers(HttpMethod.POST, "/api/users").anonymous()

                /*Login as anonymous*/
                .antMatchers(HttpMethod.POST, "/api/dev/login").anonymous()
                .antMatchers(HttpMethod.POST, "/api/login").anonymous()

                .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/**").authenticated()

                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)

                .and()
                .csrf().disable()
                .userDetailsService(userService)
                .headers()
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler);
    }
}