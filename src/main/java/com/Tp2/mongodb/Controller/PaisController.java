package com.Tp2.mongodb.Controller;

import com.Tp2.mongodb.Service.PaisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping("/migrar")
    public String migrarPaises() {
        paisService.migrarPaises();
        return "Migración completada";
    }
}