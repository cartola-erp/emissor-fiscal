package net.cartola.emissorfiscal.adm;

import net.cartola.emissorfiscal.cfop.Cfop;
import net.cartola.emissorfiscal.usuario.Usuario;
import net.cartola.emissorfiscal.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/menu-admin/usuarios")
public class menuAdmController {

    @Autowired
    private UsuarioService usuarioService;

    //Listar todos usuarios
    @GetMapping("/consulta")
    public ModelAndView loadAdm(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String nome) {
        Pageable pageable = PageRequest.of(page, 20); // 20 itens por página
        Page<Usuario> usuariosPage = (nome != null && !nome.isEmpty())
                ? usuarioService.findUsersByName(nome, pageable)
                : usuarioService.findAllUsers(pageable);

        int totalPages = usuariosPage.getTotalPages();
        int startPage = Math.max(0, Math.min(page - 1, totalPages - 3));
        int endPage = Math.min(startPage + 2, totalPages - 1);

        ModelAndView mv = new ModelAndView("adm/consulta");
        mv.addObject("usuariosPage", usuariosPage);
        mv.addObject("paginaAtual", usuariosPage.getNumber());
        mv.addObject("totalPages", totalPages);
        mv.addObject("startPage", startPage);
        mv.addObject("endPage", endPage);
        mv.addObject("nome", nome);
        return mv;
    }
}
