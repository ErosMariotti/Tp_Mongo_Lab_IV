package com.tp2.parteB.controller;

import com.tp2.parteB.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paises")
public class PaisController {

    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    /*@GetMapping("/cargar")
    public String cargarPaises() {
        paisService.cargarPaises(); // Ahora llamamos al método correcto
        return "Proceso de carga iniciado. Verifica en MongoDB.";
    }*/
}