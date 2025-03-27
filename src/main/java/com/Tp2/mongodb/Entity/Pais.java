package com.Tp2.mongodb.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pais {

    @Id
    private Integer codigoPais;

    @Column(nullable = false, length = 50)
    private String nombrePais;

    @Column(nullable = false, length = 50)
    private String capitalPais;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(nullable = false, length = 50)
    private String subregion;

    @Column(nullable = false)
    private Long poblacion;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;
}