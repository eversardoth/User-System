package com.cursojava.curso.dao;

import com.cursojava.curso.model.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> findAll();

    void delete(Long id);

    void save(Usuario usuario);

    Usuario findByCredentials(Usuario usuario);
}
