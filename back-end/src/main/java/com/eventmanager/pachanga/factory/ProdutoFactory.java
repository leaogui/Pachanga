package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ProdutoBuilder;
import com.eventmanager.pachanga.builder.ProdutoTOBuilder;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ProdutoTO;

@Component(value = "produtoFactory")
public class ProdutoFactory {
	ProdutoFactory() {
	}
	
	public ProdutoTO getProdutoTO(Produto produto) {	
		return ProdutoTOBuilder.getInstance()
				.codProduto(produto.getCodProduto())
				.codFesta(produto.getCodFesta())
				.precoMedio(produto.getPrecoMedio())
				.marca(produto.getMarca())
				.dose(produto.getDose().booleanValue())
				.quantDoses(produto.getQuantDoses())
				.build();
	}
	
	public Produto getProduto(ProdutoTO produtoTO) {
		return ProdutoBuilder.getInstance()
				  .codProduto(produtoTO.getCodProduto())
				  .codFesta(produtoTO.getCodFesta())
				  .precoMedio(produtoTO.getPrecoMedio())
				  .marca(produtoTO.getMarca())
				  .dose(produtoTO.isDose())
				  .quantDoses(produtoTO.getQuantDoses())
				  .build();
	}
	
	public List<ProdutoTO> getProdutosTO(List<Produto> produtos){
		return produtos.stream().map(p -> this.getProdutoTO(p)).collect(Collectors.toList());
	}
}
