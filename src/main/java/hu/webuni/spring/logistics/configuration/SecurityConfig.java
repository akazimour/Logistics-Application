package hu.webuni.spring.logistics.configuration;

import hu.webuni.spring.logistics.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
                 http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/api/addresses").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.PUT,"/api/addresses/{id}").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.DELETE,"/api/addresses/{id}").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.POST,"/api/transportPlans/{id}/delay").hasAuthority("TransportManager")
                .requestMatchers("/api/login/**").permitAll()
                                 .anyRequest().permitAll();

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
       UserDetails userToSend = User.builder()
                .passwordEncoder(passwordEncoder()::encode)
                .username("user")
                .password("password")
                .roles("USER")
                .authorities("USER")
                .build();
        UserDetails AddressManager = User.builder()
                .passwordEncoder(passwordEncoder()::encode)
                .username("AddressManager")
                .password("password")
                .roles("AddressManager")
                .authorities("AddressManager")
                .build();
        UserDetails TransportManager = User.builder()
                .passwordEncoder(passwordEncoder()::encode)
                .username("TransportManager")
                .password("password")
                .roles("TransportManager")
                .authorities("TransportManager")
                .build();
        return new InMemoryUserDetailsManager(userToSend,AddressManager,TransportManager);
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.authenticationProvider(authenticationProvider()).build();
    }

}


