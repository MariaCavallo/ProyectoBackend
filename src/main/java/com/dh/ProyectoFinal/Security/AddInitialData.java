package com.dh.ProyectoFinal.Security;

import com.dh.ProyectoFinal.Entity.Usuario;
import com.dh.ProyectoFinal.Entity.UsuarioRole;
import com.dh.ProyectoFinal.Repository.UsuarioRepository;
import com.dh.ProyectoFinal.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AddInitialData implements ApplicationRunner {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public AddInitialData (UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //cargar un usuario para probar
        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder();
        String passCifrada = cifrador.encode("Maria123");
        Usuario usuario = new Usuario("Maria", "Maria", "mariacavallom@gmail.com", passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuario);
        usuario=new Usuario("Magu","Magu","magucavallo@gmail.com",
                passCifrada, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);
    }

}
