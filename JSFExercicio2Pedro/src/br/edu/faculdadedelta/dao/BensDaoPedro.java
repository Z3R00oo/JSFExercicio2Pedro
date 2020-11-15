package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.modelo.BemPedro;
import br.edu.faculdadedelta.util.ConexaoPedro;

public class BensDaoPedro {

	
	private String incluirQuery = "INSERT INTO bens (nome_bem, "+
            "						 especificacao_bem, "+
            "						 desc_departamento, "+
            "						 valor_bem, "+
            "						 data_cadastro_bem) "+
            "VALUES (?, ?, ?, ?, ?)";

	private String alterarQuery = "UPDATE bens SET nome_bem = ?, "+
				  "                    especificacao_bem = ?, "+
				  "                    desc_departamento = ?, "+
				  "                    valor_bem = ?, "+
				  "                    data_cadastro_bem = ? "+
			      "WHERE id_bem = ?";
	
	private String excluirQuery = "DELETE FROM bens WHERE id_bem = ?";
	
	private String listarQuery  = "SELECT id_bem, "+
				  "       nome_bem, "+
				  "       especificacao_bem, "+
				  "       desc_departamento, "+
				  "       valor_bem, "+
				  "       data_cadastro_bem "+
				  "FROM bens";
	
	
	public void incluir(BemPedro bem) throws ClassNotFoundException, SQLException 
	{
		ConexaoPedro conexao = new ConexaoPedro();
		Connection conn = conexao.conectarNoBanco();
		PreparedStatement ps = conn.prepareStatement(incluirQuery);
		
		ps.setString(1, bem.getBem());
		ps.setString(2, bem.getEspecificacao());
		ps.setString(3, bem.getDepartamento());
		ps.setDouble(4, bem.getValor());
		ps.setDate(5, new java.sql.Date(bem.getDataCadastro().getTime()));
		
		Execute(ps);
		Close(ps, conn);
	}
	public void alterar(BemPedro bem) throws ClassNotFoundException, SQLException 
	{
		ConexaoPedro conexao = new ConexaoPedro();
		Connection conn = conexao.conectarNoBanco();
		PreparedStatement ps = conn.prepareStatement(alterarQuery);
		
		ps.setString(1, bem.getBem());
		ps.setString(2, bem.getEspecificacao());
		ps.setString(3, bem.getDepartamento());
		ps.setDouble(4, bem.getValor());
		ps.setDate(5, new java.sql.Date(bem.getDataCadastro().getTime()));
		ps.setLong(6, bem.getId());
		
		Execute(ps);
		Close(ps, conn);
	}
	public void excluir(BemPedro bem) throws ClassNotFoundException, SQLException 
	{
		ConexaoPedro conexao = new ConexaoPedro();
		Connection conn = conexao.conectarNoBanco();
		PreparedStatement ps = conn.prepareStatement(excluirQuery);
		
		ps.setLong(1, bem.getId());
		
		Execute(ps);
		Close(ps, conn);
	}
	
	public List<BemPedro> listar() throws ClassNotFoundException, SQLException
	{
		ConexaoPedro conexao = new ConexaoPedro();
		Connection conn = conexao.conectarNoBanco();
		PreparedStatement ps = conn.prepareStatement(listarQuery);
		
		ResultSet rs = ps.executeQuery();
		
		List<BemPedro> listaRetorno = new ArrayList<BemPedro>();
		
		while(rs.next()) 
		{
			BemPedro bem = new BemPedro();
			
			bem.setId(rs.getLong("id_bem"));
			bem.setBem(rs.getString("nome_bem").trim());
			bem.setEspecificacao(rs.getString("especificacao_bem").trim());
			bem.setDepartamento(rs.getString("desc_departamento").trim());
			bem.setValor(rs.getDouble("valor_bem"));
			bem.setDataCadastro(rs.getDate("data_cadastro_bem"));
			
			listaRetorno.add(bem);
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return listaRetorno;
	}
	
	
	public void Execute(PreparedStatement ps) throws SQLException 
	{
		ps.executeUpdate();
	}	
	public void Close(PreparedStatement ps, Connection conn) throws SQLException 
	{
		ps.close();
		conn.close();
	}
}
