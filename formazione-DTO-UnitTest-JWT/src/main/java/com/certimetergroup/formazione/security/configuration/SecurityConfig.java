package com.certimetergroup.formazione.security.configuration;

import com.certimetergroup.formazione.security.filter.AuthorizationFilter;
import com.certimetergroup.formazione.security.handler.CustomAuthenticationFailureResponseHandler;
import com.certimetergroup.formazione.security.service.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${security.prefix}")
    private String prefix;

    @Value("${security.param}")
    private String param;



    @Bean
    public CustomAuthenticationFailureResponseHandler customAuthenticationFailureResponseHandler() {
        return new CustomAuthenticationFailureResponseHandler();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilter( new AuthorizationFilter(authenticationManager(), jwtProvider, prefix, param) )
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()      //esclusi endpoint /public/** dalla catena di filtri di sicurezza
                .anyRequest().authenticated()

                .and()
                .exceptionHandling().authenticationEntryPoint( this.customAuthenticationFailureResponseHandler() );
    }
}
