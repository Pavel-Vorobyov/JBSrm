package com.pavel.jbsrm.user;

import com.pavel.jbsrm.auth.token.model.JwtUser;
import com.pavel.jbsrm.auth.token.security.JwtTokenGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class UserLoginController {

    JwtTokenGenerator tokenGenerator;

    public UserLoginController(JwtTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping
    public String generate(@RequestBody final JwtUser user) {
        return tokenGenerator.getToken(user);
    }
}
