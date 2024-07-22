package testes;

import DAO.DAO;
import entities.Estoque;

public class InserirProduto {
	
	public static void main(String[] args) {
		
		DAO<Object> dao = new DAO<>();
	
		Estoque estoque = new Estoque();
		
		estoque.cadastrarProduto("Parafuso Cortical 2.0x20", 10, dao);
	
		
		
		
	
		
		
	
	}

}
