package com.tp2.parteB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "paises")
public class Pais {
    @Id
    private String id;
    private String codigoPais;
    private String nombrePais;
    private String capitalPais;
    private String region;
    private int poblacion;
    private double latitud;
    private double longitud;
    private double superficie;
}