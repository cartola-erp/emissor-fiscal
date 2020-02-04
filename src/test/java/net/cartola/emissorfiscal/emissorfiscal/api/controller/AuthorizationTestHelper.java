package net.cartola.emissorfiscal.emissorfiscal.api.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import net.cartola.emissorfiscal.usuario.Usuario;

/**
 * 3 de fev de 2020
 * @author robson.costa
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthorizationTestHelper<T> {
	
	private String token;
	
	private String gerarToken(TestRestTemplate restTemplate) {
		Usuario user = new Usuario();
		user.setLogin("root");
		user.setSenha("root");
		HttpEntity<Usuario> httpEntity = new HttpEntity<Usuario>(user);
		ParameterizedTypeReference<String> tipoRetorno = new ParameterizedTypeReference<String>() {
		};
		ResponseEntity<String> responseToken = restTemplate.exchange("/autenticacao/obter-token", HttpMethod.POST, httpEntity, tipoRetorno);
		token = "Bearer " + responseToken.getBody();
		return token;
	}
	
	// PARA MÉTODO SEM BODY
	public HttpEntity<String> autorizar(TestRestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,(token == null || token.isEmpty() ? gerarToken(restTemplate) : token));
		HttpEntity<String> request = new HttpEntity<String>(headers);
		return request;
	}
	
	// PARA MÉTODO COM BODY
	public HttpEntity<T> autorizar(T entity, TestRestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, (token == null || token.isEmpty() ? gerarToken(restTemplate) : token));
		HttpEntity<T> request = new HttpEntity<>(entity, headers);
		return request;
	}



}

