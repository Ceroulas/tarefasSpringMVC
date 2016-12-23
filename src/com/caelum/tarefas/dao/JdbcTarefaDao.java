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
		String sql = "insert into tarefas (descricao) " +
				"values (?)";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, tarefa.getDescricao());
			
			stmt.execute();
			stmt.close();
		stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Tarefa> getLista(){
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from tarefas");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				if(rs.getDate("dataFinalizacao") != null)
				{
					//montando data atraves do calendar
					Calendar dataFinalizacao = Calendar.getInstance();
					dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
					
					tarefa.setDataFinalizacao(dataFinalizacao);
				}
				//adicionar objeto a lista
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
		String sql = "update tarefas set descricao=?, finalizado=?, dataFinalizacao=? where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			System.out.println("Descricao: "+ tarefa.getDescricao());
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			if(tarefa.getDataFinalizacao() != null)
			{
				stmt.setDate(3, new Date
					(tarefa.getDataFinalizacao().getTimeInMillis()));
			}
			else
			{
				stmt.setDate(3, null);
			}
			
			stmt.setLong(4, tarefa.getId());
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
	
	public Tarefa buscaPorId(Long id){
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement("select * from tarefas");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				//System.out.println(rs.getLong("id")+ " // " + id);
				if(id == rs.getLong("id"))
				{
					//criando objeto tarefa
					Tarefa tarefa = new Tarefa();
					tarefa.setId(rs.getLong("id"));
					tarefa.setDescricao(rs.getString("descricao"));
					tarefa.setFinalizado(rs.getBoolean("finalizado"));
					
					if(rs.getDate("dataFinalizacao") != null)
					{
						//montando data atraves do calendar
						Calendar dataFinalizacao = Calendar.getInstance();
						dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
					
						tarefa.setDataFinalizacao(dataFinalizacao);
					}
					System.out.println("retornada tarefa:"+tarefa.getId());
					return tarefa;
				}
			}
				return null;
			}catch(SQLException e){
				throw new RuntimeException(e);
			}	
	}
	
	public void finaliza(Long id){
		
		Tarefa tarefa = new JdbcTarefaDao().buscaPorId(id);
		
		String sql = "update tarefas set finalizado=?, dataFinalizacao=? where id=?";
		
		try{
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			
			stmt.setBoolean(1, true);
			
			stmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			
			stmt.setLong(3, tarefa.getId());
				
			stmt.execute();
			stmt.close();
			
			System.out.println("DADOS ALTERADOS COM SUCESSO!");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}



