package testes;

import DAO.DAO;
import entities.Estoque;
import entities.PedidoDeProducao;
import entities.StatusPedido;

public class CriarPedido {

	public static void main(String[] args) {
		
		DAO<Estoque> estoque = new DAO<>();
		DAO<PedidoDeProducao> pedido = new DAO<>();
		PedidoDeProducao pedidos = new PedidoDeProducao();
		Estoque estoque1 = new Estoque();
		
		//pedidos.registrarPedido(3L, 20, estoque, pedido);
		
		
		pedidos.atualizarPedido(1L, StatusPedido.CONCLUIDO);
	}
}
