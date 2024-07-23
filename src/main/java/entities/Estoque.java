package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import DAO.DAO;

@Entity
@Table(name = "estoque")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_produto" , referencedColumnName = "id")
	private Produto produtos;
	
	@OneToMany(mappedBy = "estoque")
	private List<PedidoDeProducao> pedidos;
	
	@OneToMany(mappedBy = "estoque")
	private List<PedidoDeVenda> vendas;
	
	public Produto getProdutos() {
		return produtos;
	}
	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}
	public List<PedidoDeProducao> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<PedidoDeProducao> pedidos) {
		this.pedidos = pedidos;
	}
	public Estoque() {
		
	}
	public Estoque(String nome, Integer quantidade) {
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	@SuppressWarnings("unchecked")
	public void cadastrarProduto(String nome, Integer quantidade, DAO<Object>DAO) {
		
		boolean encontrado = false;
	
		DAO.abrirTransacao();
		
		Query query = DAO.em.createQuery("select e from Estoque e");
		List<Estoque> estoqueItem = query.getResultList();

		for(Estoque itens : estoqueItem) {
			if(itens.getNome().equalsIgnoreCase(nome)) {
				System.out.println("Produto ja cadastrado, quantidade Atualizada");
				Integer qtdAtualizada = itens.getQuantidade() + quantidade;
				itens.setQuantidade(qtdAtualizada);
				DAO.atualizar(itens);
				encontrado = true;
			}
		}
		   if (!encontrado){
			Produto produto = new Produto(nome);
			Estoque estoque = new Estoque(nome, quantidade);
			estoque.setProdutos(produto);
			DAO.incluir(produto).incluir(estoque);	
			encontrado = false;
			
		}
		
	
		DAO.fecharTransacao().fecharConexao();
			}
		}
	
