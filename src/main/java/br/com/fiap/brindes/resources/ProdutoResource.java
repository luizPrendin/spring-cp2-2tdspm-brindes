package br.com.fiap.brindes.resources;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.ProdutoResponse;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoService produtoService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        if (produto != null) {
            ProdutoResponse response = produtoService.toResponse(produto);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody ProdutoRequest request) {
        Produto produto = produtoService.toEntity(request);
        Produto novoProduto = produtoService.save(produto);
        ProdutoResponse response = produtoService.toResponse(novoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {
        Example<Produto> example = Example.of(new Produto()); 
        Collection<Produto> produtos = produtoService.findAll(example);
        List<ProdutoResponse> responses = produtos.stream()
                .map(produtoService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
