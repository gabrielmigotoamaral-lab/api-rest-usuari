package com.gabrielMA.usuarios_api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    // GET /usuarios → lista todos
    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // GET /usuarios/1 → busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /usuarios → cria novo
    @PostMapping
    public Usuario criar(@RequestBody @Valid Usuario usuario) {
        return repository.save(usuario);
    }

    // PUT /usuarios/1 → atualiza
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid Usuario
            dados) {
        return repository.findById(id).map(usuario -> {
            usuario.setNome(dados.getNome());
            usuario.setEmail(dados.getEmail());
            return ResponseEntity.ok(repository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /usuarios/1 → deleta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}