package br.com.apipagamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.com.apipagamento.dto.AuthDTO;
import br.com.apipagamento.dto.RegistroDTO;
import br.com.apipagamento.dto.TokenDTO;
import br.com.apipagamento.entity.UsuarioEntity;
import br.com.apipagamento.repository.UsuarioRepository;
import br.com.apipagamento.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

   
    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {

        //  verifica se já existe
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        //  cria usuário
        UsuarioEntity user = new UsuarioEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
     
        
     // define role padrão USER se não for informado
        if (dto.getRole() == null || dto.getRole().isBlank()) {
            user.setRole("USER");
        } else {
            user.setRole(dto.getRole().toUpperCase()); // aceita "ADMIN" ou "USER"
        }
        
        repository.save(user);

        return ResponseEntity.ok("Usuário criado com sucesso");
    }
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO dto) {

        //  autentica usuário no banco
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        // gera token
        String token = jwtUtil.generateToken(dto.getUsername());

        return ResponseEntity.ok(new TokenDTO(token));
    }
    
    
    
    
}