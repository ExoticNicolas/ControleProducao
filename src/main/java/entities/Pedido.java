package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import DAO.DAO;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeProduto;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private String dataAbertura;
	
	private String dataFechamento;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@ManyToOne
	private Estoque estoque;

	
	
	public Pedido(){
		
	}
	public Pedido(String nomeProduto, Integer quantidade){
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.dataAbertura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		this.status = StatusPedido.ABERTO;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nomeProduto;
	}
	public void setNome(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public String getDataFechamento() {
		return dataFechamento;
	}
	
	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	public StatusPedido getStatus() {
		return status;
	}
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	public void registrarPedido(Long id, Integer quantidade, DAO<Estoque> DAOEstoque,DAO<Pedido>DAO) {
		Estoque estoque = DAOEstoque.em.find(Estoque.class, id);
		
		if(estoque == null) {
			System.out.println("Produto nao existe em estoque");
		} else {
			Pedido pedido = new Pedido(estoque.getNome(),quantidade);
			pedido.setEstoque(estoque);
			DAO.abrirTransacao().incluir(pedido).fecharTransacao().fecharConexao();
			System.out.println("Pedido Gerado com Suceso ID: " + pedido.getId() + " ITEM: " + pedido.getNome() + " QTD: " + pedido.getQuantidade() + " DATA PEDIDO: " + pedido.getDataAbertura());
		}
	}
	
	
	public void atualizarPedido(Long id, StatusPedido status) {
		
		DAO<Estoque> DAOEstoque = new DAO<Estoque>();
		DAO<Pedido> DAO = new DAO<Pedido>();
		Estoque estoque = new Estoque();
		
		try {
		Pedido pedido = DAO.em.find(Pedido.class, id);
		estoque = DAOEstoque.em.find(Estoque.class, pedido.getEstoque().getId());
		
		if(pedido != null) {
		switch (pedido.getStatus()) {
		case ABERTO: {
			if(status == StatusPedido.CONCLUIDO) {
				Integer qtdAtualizada = estoque.getQuantidade() + pedido.getQuantidade();
				estoque.setQuantidade(qtdAtualizada);
				dataFechamento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
				pedido.setStatus(status);
				pedido.setDataFechamento(dataFechamento);
				DAOEstoque.abrirTransacao().atualizar(estoque).fecharTransacao().fecharConexao();
				DAO.abrirTransacao().atualizar(pedido).fecharTransacao().fecharConexao();
		
				System.out.println("Pedido Concluido com Sucesso");	
				System.out.println("ID: " + pedido.getId() + " -ITEM: " + pedido.getNome() + " -QTD: " + pedido.getQuantidade() + " -ABERTURA: " + pedido.getDataAbertura() + " -FECHAMENTO: " + pedido.getDataFechamento());
				
			}else if(status == StatusPedido.CANCELADO) {
				pedido.setStatus(status);
				dataFechamento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
				pedido.setDataFechamento(dataFechamento);
				DAO.abrirTransacao().atualizar(pedido).fecharTransacao().fecharConexao();
				System.out.println("Pedido Cancelado Com sucesso");
				System.out.println("ID: " + pedido.getId() + " -ITEM: " + pedido.getNome() + " -QTD: " + pedido.getQuantidade() + " -ABERTURA: " + pedido.getDataAbertura() + " -FECHAMENTO: " + pedido.getDataFechamento());
				}
			break;
			}

		case CONCLUIDO: {
			System.out.println("Pedido concluido no dia " + pedido.getDataFechamento() + " nao é possivel alterar Status");
			System.out.println("ID: " + pedido.getId() + " -ITEM: " + pedido.getNome() + " -QTD: " + pedido.getQuantidade() + " -ABERTURA: " + pedido.getDataAbertura() + " -FECHAMENTO: " + pedido.getDataFechamento());
			break;
		}
		case CANCELADO: {
			System.out.println("Nao é Possivel Alterar Status de um Pedido Cancelado");
			System.out.println("Cancelado na data: " + pedido.getDataFechamento());
			System.out.println("ID: " + pedido.getId() + " -ITEM: " + pedido.getNome() + " -QTD: " + pedido.getQuantidade() + " -ABERTURA: " + pedido.getDataAbertura() + " -FECHAMENTO: " + pedido.getDataFechamento());
			break;
		}
		default:
			break;
		
	 }
	}
		
		
		/*if(pedido != null) {
			if(pedido.getStatus() == StatusPedido.ABERTO && status == StatusPedido.CONCLUIDO) {
				Integer qtdAtualizada = estoque.getQuantidade() + pedido.getQuantidade();
				estoque.setQuantidade(qtdAtualizada);
				DAOEstoque.atualizar(estoque);
				pedido.setStatus(status);
				DAOEstoque.abrirTransacao().atualizar(estoque).fecharTransacao().fecharConexao();
				DAO.abrirTransacao().atualizar(pedido).fecharTransacao().fecharConexao();
		
				System.out.println("Pedido Concluido com Sucesso");
				
			} else if(pedido.getStatus() == StatusPedido.ABERTO && status == StatusPedido.CANCELADO) {
				System.out.println("Pedido Cancelado Com sucesso");
				pedido.setStatus(status);
				DAO.abrirTransacao().atualizar(pedido).fecharTransacao().fecharConexao();
				
			} else if(pedido.getStatus() == StatusPedido.CONCLUIDO) {
				System.out.println("Pedido ja foi concluido na Data: " + pedido.getData() + " - nao é possivel Alterar Status");
				
			} else if(pedido.getStatus() == StatusPedido.CANCELADO && status == StatusPedido.CONCLUIDO);
				System.out.println("Nao é Possivel Alterar Status de um Pedido Cancelado");
				System.out.println("Cancelado na data: " + pedido.getData());
		  } */
		} catch (Exception e) {
			System.out.println("Pedido nao existente");
			
		}
		
		
	}
}
		
