package com.example.api_viagem.repositorio;

import com.example.api_viagem.modelo.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepositorio extends JpaRepository<Destino, Long> {
}

