package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;

@Controller 
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @GetMapping("/") 
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro (@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.addAttribute("exito", "Registro exitoso!!!");
            return "index.html";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());
            return "registrar.html";
        }
    } 

    @GetMapping("/login")
    public String login () {
        return "login.html";
    }


}
