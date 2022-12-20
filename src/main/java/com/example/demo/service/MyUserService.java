package com.example.demo.service;

import com.example.demo.cnfiguration.dto.LoginDto;
import com.example.demo.cnfiguration.dto.RegisterDto;

import com.example.demo.entity.Address;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("yoqakan"));
    }

    public HttpEntity<?> register(RegisterDto registerDto) throws FileNotFoundException {
        User user=new User();

        Optional<User> byEmail = userRepository.findByEmail(registerDto.getEmail());
        if(byEmail.isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("BU TAKRORLANDI");
        }
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Address address = addressRepository.findById(registerDto.getAddressId()).orElseThrow(() -> new FileNotFoundException("address not found"));
        user.setAddress(address);
        Set<Role> roleSet=new HashSet<>();
        for (Long roleId : registerDto.getRoleSetId()) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new FileNotFoundException("Role nt found"));
            roleSet.add(role);
        }
        user.setRoleSet(roleSet);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail()+" user created in system");
    }

    public HttpEntity<?> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        String s = jwtProvider.generatedToken(principal.getEmail(), principal.getRoleSet());
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }
}
