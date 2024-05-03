package br.com.fiap.brindes.service;

import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.CategoriaResponse;
import br.com.fiap.brindes.dto.response.ProdutoResponse;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.repository.Produtorepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse> {


   @Autowired
   private Produtorepository repo;

   @Autowired
   private CategoriaService categoriaService;


   /* @Override
    public Collection<Produto> findAll() {
        return List.of();
    }
    */
    @Override
    public Collection<Produto> findAll(Example<Produto> example) {
        return repo.findAll(example);
    }

    @Override
    public Produto findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Produto save(Produto e) {
        return repo.save(e);
    }

    @Override
    public Produto toEntity(ProdutoRequest dto) {

        var categoria = categoriaService.findById(dto.categoria().id());


        return Produto.builder()
                .categoria(categoria)
                .nome(dto.nome())
                .preco(dto.preco())
                .build();
    }

    @Override
    public ProdutoResponse toResponse(Produto e) {
        var categoria = categoriaService.toResponse(e.getCategoria());

        return ProdutoResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .categoria(categoria)
                .build();

    }
}
