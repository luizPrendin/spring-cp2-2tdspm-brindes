package br.com.fiap.brindes.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ResourceDTO<Request, Response> {

    ResponseEntity<Response> findById(Long id);

    ResponseEntity<Response> save(Request r);
    
    ResponseEntity<List<Response>> findAll();


}
