package net.cartola.emissorfiscal.security.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.cartola.emissorfiscal.usuario.Perfil;

/**
 * 26 de dez de 2019
 * 
 * @author robson.costa
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	/**
	 * OBS: A ordem é da mais restritiva, para as que são menos restrititvas
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/autenticacao/*", "/", "/login", "/home", "/ressucita-me", "/api/v1/usuario/**", "/api/v1/tributacao-estadual/gnre-aliquotas").permitAll()
				
				.antMatchers("/menu-admin/**").hasRole(Perfil.ADMIN.name())
				.antMatchers("/api/v1/**").hasAnyRole(Perfil.ADMIN.name(), Perfil.API_ACESS.name())
				.antMatchers("/sped/**").hasAnyRole(Perfil.ADMIN.name(), Perfil.ESCRITURADOR.name())
				.antMatchers("/**/consulta").hasAnyRole(Perfil.ADMIN.name(), Perfil.CONTADOR.name(), Perfil.WEB_ACESS.name())
				.antMatchers("/**").hasAnyRole(Perfil.ADMIN.name(), Perfil.CONTADOR.name())
				
//				.antMatchers("/api/v1/**").hasAnyRole(Perfil.ADMIN.name(), Perfil.API_ACESS.name())
//				.antMatchers("/api/**").authenticated()
				.anyRequest().authenticated()
				.and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				// SE a SessionPolicy, for STATELESS, não salvará o cookie de session no HTML
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/home")
				.and().logout().logoutUrl("/logout");
//				.exceptionHandling()
		
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
//	public void configure(WebSecurity web) throws Exception {
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**");
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
