package com.example.api_viagem.controlador;

import com.example.api_viagem.modelo.Destino;
import com.example.api_viagem.servico.DestinoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinos")
public class DestinoControlador {

    @Autowired
    private DestinoServico destinoServico;

    @PostMapping
    public ResponseEntity<Destino> cadastrarDestino(@RequestBody Destino destino) {
        Destino novoDestino = destinoServico.salvarDestino(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        List<Destino> destinos = destinoServico.listarDestinos();
        return new ResponseEntity<>(destinos, HttpStatus.OK);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisarDestinos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao) {
        List<Destino> destinos = destinoServico.pesquisarDestinos(nome, localizacao);
        return ResponseEntity.ok(destinos);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Destino> visualizarDestino(@PathVariable Long id) {
        Optional<Destino> destinoOpt = destinoServico.obterDestino(id);
        return destinoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/avaliar")
    public ResponseEntity<Destino> avaliarDestino(@PathVariable Long id, @RequestParam int nota) {
        try {
            Destino destinoAvaliado = destinoServico.avaliarDestino(id, nota);
            if (destinoAvaliado != null) {
                return ResponseEntity.ok(destinoAvaliado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        destinoServico.excluirDestino(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

