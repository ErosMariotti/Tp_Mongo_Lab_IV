package com.tp2.parteB;

import com.tp2.parteB.model.Pais;
import com.tp2.parteB.service.PaisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@SpringBootApplication
public class Tp2ParteBApplication implements CommandLineRunner {

	private final PaisService paisService;

	public Tp2ParteBApplication(PaisService paisService) {
		this.paisService = paisService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Tp2ParteBApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("🔹 Ejecutando funciones...");

		paisService.getPaisesDeAmerica();
		paisService.getPaisesDeAmericaConAltaPoblacion();
		paisService.getPaisesNoAfrica();
		paisService.actualizarEgypt();
		paisService.eliminarPais258();
		paisService.getPaisesPorRangoPoblacion();
		paisService.getPaisesOrdenadosPorNombre();
		paisService.crearIndiceCodigoPais();

		System.out.println("✅ Todas las funciones fueron ejecutadas.");
	}
}

