package com.caelum.tarefas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caelum.tarefas.dao.JdbcTarefaDao;
import com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefaController {
	
	@RequestMapping("novaTarefa")
	public String form(){
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adiciona(Tarefa tarefa){
		System.out.println("Tarefa: "+ tarefa.getDescricao());
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.adiciona(tarefa);
		return "tarefa/adicionada";
	}
}
