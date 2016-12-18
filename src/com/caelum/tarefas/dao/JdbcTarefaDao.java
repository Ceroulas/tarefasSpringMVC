package com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.caelum.tarefas.jdbc.ConnectionFactory;
import com.caelum.tarefas.modelo.Tarefa;

public class JdbcTarefaDao {
	private Connection connection;
	
	public JdbcTarefaDao(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Tarefa tarefa){
		String sql = "insert into tarefas"+
				"(descricao, finalizado, dataFinalizacao)"+
				"values (?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			System.out.println("date1: "+ tarefa.getDataFinalizacao().getTimeInMillis());
			System.out.println("date2: "+ new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
			stmt.setDate(4, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
			
			stmt.execute();
		stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Tarefa> getLista(){
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from contatos");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Tarefa tarefa = new Tarefa();
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				Calendar date = Calendar.getInstance();
				
				date.setTime(rs.getDate("dataFinalizacao"));
				tarefa.setDataFinalizacao(date);
				
				tarefas.add(tarefa);
			}
			stmt.close();
			rs.close();
			
			return tarefas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Tarefa tarefa){
		String sql = "update tarefas set descricao=?, finaliza=?, dataFinalizacao=? where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			stmt.setDate(4, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));

			stmt.setLong(5, tarefa.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Long id){
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from tarefas where id=?");
			stmt.setLong(1, id);
			
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
