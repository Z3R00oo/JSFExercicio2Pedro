package br.edu.faculdadedelta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.BensDaoPedro;
import br.edu.faculdadedelta.modelo.BemPedro;

@ManagedBean
@SessionScoped
public class BemControllerPedro {

	
	private BemPedro bem = new BemPedro();
	private BensDaoPedro dao = new BensDaoPedro();
	
	public BemPedro getBem() 
	{
		return bem;
	}
	public void setBem(BemPedro bem) 
	{
		this.bem = bem;
	}
	
	
	public String salvar() 
	{
		try 
		{
			if(bem.getId() == null) 
			{
				dao.incluir(bem);
				novoBem();
				GerarMensagem("Inclusão Realizada com Sucesso!");
			}
			else 
			{
				dao.alterar(bem);
				novoBem();
				GerarMensagem("Alteração Realizada com Sucesso!"); 
			}
		}
		catch(ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			GerarMensagem("Erro ao realizar Operação. Tente Novamente. " + e.getMessage());
		}
		return "Cadastro.xhtml";
	}
	public List<BemPedro> getLista()
	{
		List<BemPedro> listaRetorno = new ArrayList<BemPedro>();
		
		try 
		{
			listaRetorno = dao.listar();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			GerarMensagem("Erro ao realizar Operação. Tente Novamente. " + e.getMessage());
		}
		return listaRetorno;
	}
	public String editar() 
	{
		return "CadastroBens.xhtml";
	}
	public String excluir() 
	{
		try 
		{
			dao.excluir(bem);
			novoBem();
			GerarMensagem("Exclusão Realizada com Sucesso!");
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			GerarMensagem("Erro ao realizar Operação. Tente Novamente. " + e.getMessage());
		}
		return "MostrarOrdemServico.xhtml";
	}

	
	public void novoBem() 
	{
		bem = new BemPedro();
	}	
	public String limparCampos() 
	{	
		return "CadastroBens.xhtml";
	}	
	public void GerarMensagem(String texto) 
	{
		FacesMessage mensagem = new FacesMessage(texto);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
	}
}
