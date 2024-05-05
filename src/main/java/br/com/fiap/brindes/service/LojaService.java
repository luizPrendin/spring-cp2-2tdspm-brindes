package br.com.fiap.brindes.service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.fiap.brindes.dto.request.LojaRequest;
import br.com.fiap.brindes.dto.response.LojaResponse;
import br.com.fiap.brindes.dto.response.ProdutoResponse;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.LojaRepository;

@Service
public class LojaService implements ServiceDTO<Loja, LojaRequest, LojaResponse>{

    @Autowired
    private LojaRepository repo;

    @Autowired
    private ProdutoService produtoService;

    @Override
    public Collection<Loja> findAll(Example<Loja> example) {
        return repo.findAll(example);
    }

    @Override
    public Loja findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Loja save(Loja e) {
        return repo.save(e);
    }

    @Override
    public Loja toEntity(LojaRequest dto) {
        Loja loja = Loja.builder()
                .nome(dto.nome())
                .produtosComercializados(new LinkedHashSet<>()) 
                .build();

        return loja;
    }


    public LojaResponse toResponse(Loja e) {
       
        Set<Produto> produtos = e.getProdutosComercializados() != null ? e.getProdutosComercializados() : new LinkedHashSet<>();

        List<ProdutoResponse> produtoResponses = produtos.stream()
                .map(produtoService::toResponse)
                .collect(Collectors.toList());

        return LojaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .produtosComercializados(produtoResponses)
                .build();
    }

}
