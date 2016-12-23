package com.caelum.tarefas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.caelum.tarefas.dao.JdbcTarefaDao;
import com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefaController {
	
	@RequestMapping("novaTarefa")
	public String form(){
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result){

		if(result.hasFieldErrors("descricao"))
			return "tarefa/formulario";
		
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.adiciona(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("listaTarefas")
	public String lista(Model model){
		JdbcTarefaDao dao = new JdbcTarefaDao();
		List<Tarefa> tarefas = dao.getLista();
		
		model.addAttribute("tarefas", tarefas);
		return "tarefa/lista";
	}
	
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa){
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.remove(tarefa.getId());
		
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model){
		JdbcTarefaDao dao = new JdbcTarefaDao();
		model.addAttribute("tarefa",dao.buscaPorId(id));
				
		return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa){
		System.out.println("Controller: descricao "+ tarefa.getDescricao());
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model){
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/dataFinalizada";
	}
}
