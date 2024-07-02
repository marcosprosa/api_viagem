package com.example.api_viagem.servico;

import com.example.api_viagem.modelo.Destino;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DestinoServico {

    private List<Destino> destinos = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    public Destino salvarDestino(Destino destino) {
        destino.setId(counter.incrementAndGet());
        destinos.add(destino);
        return destino;
    }

    public List<Destino> listarDestinos() {
        return new ArrayList<>(destinos);
    }

    public List<Destino> pesquisarDestinos(String nome, String localizacao) {
        return destinos.stream()
                .filter(d -> (nome == null || d.getNome().toLowerCase().contains(nome.toLowerCase())) &&
                             (localizacao == null || d.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Optional<Destino> obterDestino(Long id) {
        return destinos.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public void excluirDestino(Long id) {
        destinos.removeIf(d -> d.getId().equals(id));
    }

    public Destino avaliarDestino(Long id, int nota) {
        Optional<Destino> destinoOpt = obterDestino(id);
        if (destinoOpt.isPresent()) {
            Destino destino = destinoOpt.get();
            if (nota >= 1 && nota <= 10) {
                double novaMedia = ((destino.getAvaliacaoMedia() * destino.getNumAvaliacoes()) + nota) / (destino.getNumAvaliacoes() + 1);
                destino.setAvaliacaoMedia(novaMedia);
                destino.setNumAvaliacoes(destino.getNumAvaliacoes() + 1);
                return destino;
            } else {
                throw new IllegalArgumentException("A nota deve estar entre 1 e 10.");
            }
        }
        return null;
    }
}
