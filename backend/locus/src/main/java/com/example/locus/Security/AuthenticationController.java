package com.example.locus.Security;

import com.example.locus.Security.Dto.CreatePcRequest;
import com.example.locus.Security.Dto.RegisterNewStudents;
import com.example.locus.Security.Model.UserModel;
import com.example.locus.Security.Service.UserManager;
import com.example.locus.Security.jwt.JwtUtil;
import com.example.locus.Security.jwt.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserManager userManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/api/login")
    public Map<String,Object> login(HttpServletResponse response, @RequestBody UserCredentials userCredentials){
        System.out.println("Inside login method");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(),userCredentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authentication.getPrincipal());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.generate(userDetails.getUsername(),authentication.getAuthorities());

        Map<String, Object> payload = new HashMap<>();
        payload.put("jwt",jwtToken);
        payload.put("roles",userDetails.getAuthorities());
        payload.put("username",userDetails.getUsername());
        return payload;
    }

    @PostMapping("/api/tpo/registerNewStudents")
    public boolean registerNewStudents(@RequestBody RegisterNewStudents registerNewStudent){
        return userManager.registerNewStudents(registerNewStudent);
    }

    @PostMapping("/api/tpo/createPc")
    public boolean createPc(@RequestBody CreatePcRequest createPcRequest){
        return userManager.createPc(createPcRequest);
    }

    @GetMapping("/api/tpo/deletePc")
    public boolean deletePc(@RequestParam(name = "username") String pcUsername){
        return userManager.deletePc(pcUsername);
    }

    @GetMapping("/api/tpo/fetchAllPcs")
    public List<UserModel> fetchAllPc(){
       return userManager.fetchAllPc();
    }

    @GetMapping("/api/tpo/fetchAllStudents")
    public List<UserModel> fetchAllStudents(){
        return userManager.fetchAllStudents();
    }

}
