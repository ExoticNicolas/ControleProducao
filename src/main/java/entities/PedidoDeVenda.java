package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "pedido_venda")
@Table(name = "pedido_venda")
public class PedidoDeVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_produto", nullable = false)
	private String nomeProduto;
	
	@Column(name = "nome_cliente", nullable = false)
	private String nomeCliente;
	
	@Column(name = "data_venda", nullable = false)
	private String dataDeVenda;
	
	@Column(name = "quantidade_produto", nullable = false)
	private Integer quantidadeProduto;
	
	@ManyToOne
	@JoinColumn(name = "estoque_id", referencedColumnName = "id")
	private Estoque estoque;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id" , referencedColumnName = "id")
	private Cliente cliente;
	
	public PedidoDeVenda () {
		
	}
	
	public PedidoDeVenda(Long id, String nomeProduto, String nomeCliente, String dataDeVenda, Integer quantidadeProduto,
			Estoque estoque, Cliente cliente) {
		this.id = id;
		this.nomeProduto = nomeProduto;
		this.nomeCliente = nomeCliente;
		this.dataDeVenda = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		this.quantidadeProduto = quantidadeProduto;
		this.estoque = estoque;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDataDeVenda() {
		return dataDeVenda;
	}

	public void setDataDeVenda(String dataDeVenda) {
		this.dataDeVenda = dataDeVenda;
	}

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

}
