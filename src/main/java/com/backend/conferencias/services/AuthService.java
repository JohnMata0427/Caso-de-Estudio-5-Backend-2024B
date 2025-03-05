package com.backend.conferencias.services;

import com.backend.conferencias.dto.LoginUsuarioDTO;
import com.backend.conferencias.dto.RegistroUsuarioDTO;
import com.backend.conferencias.models.Usuario;
import com.backend.conferencias.repositories.UsuarioRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.backend.conferencias.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Service
public class AuthService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  public Map<String, Object> registro(RegistroUsuarioDTO usuario) {
    String email = usuario.getEmail();

    if (usuarioRepository.existsByEmail(email))
      throw new RuntimeException("Ya existe un usuario con el email " + email);

    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    Usuario usuarioToSave = modelMapper.map(usuario, Usuario.class);

    usuarioRepository.save(usuarioToSave);

    return Map.of("response", "Usuario registrado exitosamente", "usuario", usuarioToSave);
  }

  @Transactional
  public Map<String, String> iniciarSesion(LoginUsuarioDTO request) {
    String email = request.getEmail();

    if (!usuarioRepository.existsByEmail(email))
      throw new RuntimeException("Usuario con email " + email + " no registrado");

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));

    String token = jwtService.generateToken(email);
    return Map.of("response", "El usuario ha iniciado sesión exitosamente", "token", token);
  }

  @Transactional(readOnly = true)
  public Map<String, Object> perfil() {
    @SuppressWarnings("null")
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    String email = (String) request.getAttribute("email");

    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuario con email " + email + " no encontrado"));

    return Map.of("response", "Perfil del usuario", "usuario", Map.of(
        "nombre", usuario.getNombre(),
        "apellido", usuario.getApellido(),
        "email", usuario.getEmail()));
  }
}