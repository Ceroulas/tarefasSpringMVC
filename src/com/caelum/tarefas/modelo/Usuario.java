package com.caelum.tarefas.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {
	private Long id;
	
	@NotNull @Size(min=6)
	private String login;
	@NotNull @Size(min=4)
	private String senha;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
