package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {
    private void validar(Long isbn, String titulo, Integer ejemplares, Date alta, Autor autor, Editorial editorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El ISBN no puede ser nulo");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MiException("El título no puede ser nulo o estar vacío");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new MiException("El número de ejemplares debe ser mayor a cero");
        }
        if (alta == null) {
            throw new MiException("La fecha de alta no puede ser nula");
        }
        if (autor == null) {
            throw new MiException("El autor no puede ser nulo");
        }
        if (editorial == null) {
            throw new MiException("La editorial no puede ser nula");
        }
    }

    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, UUID autorID, UUID editorialID) throws MiException {
        Date alta = new Date();
        
        Autor autor = autorRepositorio.findById(autorID)
                .orElseThrow(() -> new MiException("No se encontró el autor con el ID proporcionado"));
        Editorial editorial = editorialRepositorio.findById(editorialID)
                .orElseThrow(() -> new MiException("No se encontró la editorial con el ID proporcionado"));
    
        validar(isbn, titulo, ejemplares, alta, autor, editorial);
    
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
    
        libroRepositorio.save(libro); 
    }
    

    @Transactional(readOnly = true)
    public List<Libro> listarLibros () {
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, Date alta, UUID autorID, UUID editorialID) throws MiException {
    
        Autor autor = autorRepositorio.findById(autorID)
                .orElseThrow(() -> new MiException("No se encontró el autor con el ID proporcionado"));
        Editorial editorial = editorialRepositorio.findById(editorialID)
                .orElseThrow(() -> new MiException("No se encontró la editorial con el ID proporcionado"));
    
            validar(isbn, titulo, ejemplares, alta, autor, editorial);
    
            Libro libro = libroRepositorio.findById(isbn)
                    .orElseThrow(() -> new MiException("No se encontró el libro con el ISBN proporcionado"));
            
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAlta(alta);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libroRepositorio.save(libro);
    }

    @Transactional (readOnly = true)
    public Libro getOne (Long isbn) {
        return libroRepositorio.getReferenceById(isbn);
    }
}
