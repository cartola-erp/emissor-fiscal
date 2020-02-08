package net.cartola.emissorfiscal.usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class UsuarioController {
	
//	@GetMapping({"", "login"})
	@GetMapping("login")
//	public ModelAndView loadLoginUsuario() {
	public String loadLoginUsuario() {
//		ModelAndView mv = new ModelAndView("usuario/login");
//		Usuario usuario = new Usuario();
//		mv.addObject("usuario", usuario);
		// mv.addObject("textBtnCadastrarEditar", "Cadastrar");
//		return mv;
		return "usuario/login";
	}

	
	@GetMapping("logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("usuario/logout");
		return mv;
//		return "/logout";
	}
	
	
	
	
	
	
	
	
}
