package hu.webuni.spring.logistics.controller;

import hu.webuni.spring.logistics.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

 /* Comment: name and password attributes should be used as Transport or AddressManager for getting jwt-token to be able to log in
    {
    "name":"TransportManager",
    "password":"password"
    }
    */

@PostMapping("/api/login")

    public String login(@RequestBody SecurityProperties.User user){

inMemoryUserDetailsManager.setAuthenticationManager(authenticationManager);
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
return jwtService.createJwtToken((UserDetails)authentication.getPrincipal());

}
}
