package com.cursojava.curso.controller;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.model.Usuario;
import com.cursojava.curso.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping()
    public List<Usuario> findAll(@RequestHeader(value="Authorization") String token){
        if(!validateToken(token)){
            return null;
        }
        return usuarioDao.findAll();
    }

    @RequestMapping(path="/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id, @RequestHeader(value="Authorization") String token){
        if(!validateToken(token)){
            return;
        }
        usuarioDao.delete(id);
    }

    @RequestMapping(method=RequestMethod.POST)
    public void save(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.save(usuario);
    }

    private boolean validateToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId !=null;
    }
}
