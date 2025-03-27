package com.Tp2.mongodb.Repository;

import com.Tp2.mongodb.Entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
}