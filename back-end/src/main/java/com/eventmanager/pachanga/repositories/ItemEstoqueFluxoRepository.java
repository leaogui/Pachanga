package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;

public interface ItemEstoqueFluxoRepository extends JpaRepository<ItemEstoqueFluxo, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_historico');", nativeQuery = true)
	public int getNextValMySequence();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM ItemEstoqueFluxo i WHERE i.codFesta = :idFesta")
	public void deleteByCodFesta(int idFesta);
	
	@Query(value = "SELECT i FROM ItemEstoqueFluxo i WHERE i.codEstoque = :codEstoque AND i.codProduto = :codProduto")
	public List<ItemEstoqueFluxo> getFluxoEstoqueProduto(int codEstoque, int codProduto);

	@Query(value = "SELECT i FROM ItemEstoqueFluxo i WHERE i.codFesta = :codFesta")
	public List<ItemEstoqueFluxo> getFluxoEstoqueFesta(int codFesta);
	
	@Query(value = "SELECT DISTINCT i.codEstoque, i.codProduto FROM ItemEstoqueFluxo i WHERE i.codFesta = :codFesta ORDER BY i.codEstoque")
	public List<Integer[]> getEstoqueProdutoFluxoFesta(int codFesta);

	@Query(value = "SELECT DISTINCT i.codProduto, i.nomeProduto FROM ItemEstoqueFluxo i WHERE i.codFesta = :codFesta")
	public List<Object[]> getProdutoEstoqueFesta(int codFesta);

	@Query(value = "SELECT i.quantidadeEstoque, i.codEstoque, i.quantPerda FROM ItemEstoqueFluxo i WHERE i.codFesta = :codFesta AND i.codProduto = :codProduto ORDER BY i.codEstoque, i.dataHorario")
	public List<Integer[]> getQuantidadeProdutoConsumidoFesta(int codProduto, int codFesta);
	
	@Query(value = "SELECT i FROM ItemEstoqueFluxo i WHERE i.codFesta = :codFesta AND i.codProduto = :codProduto AND i.codEstoque = :codEstoque ORDER BY i.dataHorario DESC")
	public List<ItemEstoqueFluxo> getItemEstoqueFluxoByCodFestaCodProdutoCodEstoque(int codProduto, int codFesta, int codEstoque);

}
