package com.bibliadiaria.diariobiblico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bibliadiaria.diariobiblico.model.Usuario;
import com.bibliadiaria.diariobiblico.repository.UsuarioRepository;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Tela de Login
    @GetMapping("/login")
    public String loginPage() {
        return "auth";
    }

    // Processo de Login
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (passwordEncoder.matches(password, usuario.getSenha())) {
                return "redirect:/reflexoes";
            }
        }

        model.addAttribute("error", "Email ou senha inválidos!");
        return "auth";
    }

    // Processo de Cadastro
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password, @RequestParam String nome, Model model) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);

        if (usuarioExistente.isPresent()) {
            model.addAttribute("error", "Email já cadastrado!");
            return "auth";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);

        return "redirect:/auth/login";
    }
}
