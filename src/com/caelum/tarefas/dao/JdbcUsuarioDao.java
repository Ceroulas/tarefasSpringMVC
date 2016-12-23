package com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.caelum.tarefas.jdbc.ConnectionFactory;
import com.caelum.tarefas.modelo.Usuario;

public class JdbcUsuarioDao {
	private Connection connection;
	
	
	public JdbcUsuarioDao(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public boolean existeUsuario(Usuario usuario){
		String sql = "select * from usuarios where login= ?  and senha= ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			
			ResultSet rs =  stmt.executeQuery();
			
			stmt.execute();
			
			if(rs.next())
			{
				stmt.close();
				return true;
			}
			else
			{
				stmt.close();
				return false;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
