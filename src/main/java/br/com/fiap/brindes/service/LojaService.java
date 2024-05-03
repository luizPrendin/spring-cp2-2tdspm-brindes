package br.com.fiap.brindes.service;

import br.com.fiap.brindes.dto.request.LojaRequest;
import br.com.fiap.brindes.dto.response.LojaResponse;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
        return Loja.builder()
                .nome(dto.nome())
                .build();
    }

    @Override
    public LojaResponse toResponse(Loja e) {
        var produtos = e.getProdutosComercializados().stream().map(produtoService::toResponse).toList();

        return LojaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .produtosComercializados(produtos)
                .build();
    }
}
