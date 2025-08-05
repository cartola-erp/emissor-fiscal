package net.cartola.emissorfiscal.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class UsuarioSenhaGerador {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void gerarSenhar() {

        assertNotNull(usuarioService);
        assertNotNull(bCryptPasswordEncoder);


        String login = "murilo.tuvani";
        String senha = "senha";
        Optional<Usuario> opUser = usuarioService.findByLogin(login);

        assertTrue(opUser.isPresent());
        String senhaEncriptada = bCryptPasswordEncoder.encode(senha);
        System.out.println("Senhe criptografada : "+senhaEncriptada);

        assertNotNull(senhaEncriptada);
        Usuario user = opUser.get();
        user.setSenha(senhaEncriptada);
        usuarioService.save(user);

    }
}
