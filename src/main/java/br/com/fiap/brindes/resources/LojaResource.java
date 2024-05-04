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

import br.com.fiap.brindes.dto.request.LojaRequest;
import br.com.fiap.brindes.dto.request.ProdutoRequest;
import br.com.fiap.brindes.dto.response.LojaResponse;
import br.com.fiap.brindes.entity.Loja;
import br.com.fiap.brindes.entity.Produto;
import br.com.fiap.brindes.service.LojaService;
import br.com.fiap.brindes.service.ProdutoService;

@RestController
@RequestMapping("/lojas")
public class LojaResource implements ResourceDTO<LojaRequest, LojaResponse> {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private ProdutoService produtoService;

    @Override
    @PostMapping
    public ResponseEntity<LojaResponse> save(@RequestBody LojaRequest request) {
        Loja loja = lojaService.toEntity(request);
        Loja novaLoja = lojaService.save(loja);
        LojaResponse response = lojaService.toResponse(novaLoja);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LojaResponse>> findAll() {
        // Crie um exemplo vazio de Loja para obter todas as lojas
        Example<Loja> example = Example.of(new Loja());
        Collection<Loja> lojas = lojaService.findAll(example);
        // Mapeie as lojas para suas representações de resposta
        List<LojaResponse> responses = lojas.stream()
                .map(lojaService::toResponse)
                .collect(Collectors.toList());
        // Retorne a resposta com as lojas encontradas
        return ResponseEntity.ok(responses);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LojaResponse> findById(@PathVariable Long id) {
        Loja loja = lojaService.findById(id);
        if (loja != null) {
            LojaResponse response = lojaService.toResponse(loja);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/produtos-comercializados")
    public ResponseEntity<Void> adicionarProdutoComercializado(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        Loja loja = lojaService.findById(id);
        if (loja != null) {
            Produto produto = produtoService.toEntity(request);
            loja.getProdutosComercializados().add(produto);
            lojaService.save(loja);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
