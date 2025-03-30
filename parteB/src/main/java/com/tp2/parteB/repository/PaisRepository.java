package com.tp2.parteB.repository;

import com.tp2.parteB.model.Pais;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaisRepository extends MongoRepository<Pais, String> {
    Optional<Pais> findByCodigoPais(String codigoPais);
}
