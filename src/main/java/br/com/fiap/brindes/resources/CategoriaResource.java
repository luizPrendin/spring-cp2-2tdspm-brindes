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

import br.com.fiap.brindes.dto.request.CategoriaRequest;
import br.com.fiap.brindes.dto.response.CategoriaResponse;
import br.com.fiap.brindes.entity.Categoria;
import br.com.fiap.brindes.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource implements ResourceDTO<CategoriaRequest, CategoriaResponse> {

    @Autowired
    private CategoriaService categoriaService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria != null) {
            CategoriaResponse response = categoriaService.toResponse(categoria);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoriaResponse> save(@RequestBody CategoriaRequest request) {
        Categoria categoria = categoriaService.toEntity(request);
        Categoria novaCategoria = categoriaService.save(categoria);
        CategoriaResponse response = categoriaService.toResponse(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        Example<Categoria> example = Example.of(new Categoria()); 
        Collection<Categoria> categorias = categoriaService.findAll(example);
        List<CategoriaResponse> responses = categorias.stream()
                .map(categoriaService::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

}
