package testes;

import DAO.DAO;
import entities.Estoque;
import entities.Pedido;
import entities.StatusPedido;

public class CriarPedido {

	public static void main(String[] args) {
		
		DAO<Estoque> estoque = new DAO<>();
		DAO<Pedido> pedido = new DAO<>();
		Pedido pedidos = new Pedido();
		Estoque estoque1 = new Estoque();
		
		//pedidos.registrarPedido(3L, 20, estoque, pedido);
		
		
		pedidos.atualizarPedido(25L, StatusPedido.CONCLUIDO);
	}
}
