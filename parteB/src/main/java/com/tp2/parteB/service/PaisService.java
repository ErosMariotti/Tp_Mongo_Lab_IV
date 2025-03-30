package com.tp2.parteB.service;

import com.tp2.parteB.model.Pais;
import com.tp2.parteB.repository.PaisRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Map;

@Service
public class PaisService {
    private final PaisRepository paisRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PaisService(PaisRepository paisRepository, MongoTemplate mongoTemplate) {
        this.paisRepository = paisRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // Renombrar el método a cargarPaises() para que coincida con el controlador
    /*public void cargarPaises() {
        for (int codigo = 1; codigo <= 999; codigo++) {
            String url = "https://restcountries.com/v3.1/alpha/" + String.format("%03d", codigo);
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                String jsonResponse = response.getBody();

                if (jsonResponse == null || jsonResponse.isEmpty()) {
                    System.out.println("❌ Código " + codigo + " no tiene JSON válido.");
                    continue;
                }

                JSONArray jsonArray = new JSONArray(jsonResponse);
                if (jsonArray.isEmpty()) {
                    System.out.println("⚠️ Código " + codigo + " no tiene datos.");
                    continue;
                }

                JSONObject json = jsonArray.getJSONObject(0);
                String nombrePais = json.getJSONObject("name").optString("common", "Desconocido");
                String capitalPais = json.has("capital") ? json.getJSONArray("capital").optString(0, "No tiene") : "No tiene";
                String region = json.optString("region", "No especificado");
                String subregion = json.optString("subregion", "No especificado");
                int poblacion = json.optInt("population", 0);
                double latitud = json.has("latlng") ? json.getJSONArray("latlng").optDouble(0, 0.0) : 0.0;
                double longitud = json.has("latlng") ? json.getJSONArray("latlng").optDouble(1, 0.0) : 0.0;
                double superficie = json.optDouble("area", 0.0);
                String codigoPais = json.optString("cca3", "No especificado");

                Pais nuevoPais = new Pais();
                nuevoPais.setCodigoPais(codigoPais);
                nuevoPais.setNombrePais(nombrePais);
                nuevoPais.setCapitalPais(capitalPais);
                nuevoPais.setRegion(region);
                nuevoPais.setPoblacion(poblacion);
                nuevoPais.setLatitud(latitud);
                nuevoPais.setLongitud(longitud);
                nuevoPais.setSuperficie(superficie);

                paisRepository.findByCodigoPais(codigoPais)
                        .ifPresentOrElse(
                                existingPais -> {
                                    nuevoPais.setId(existingPais.getId());
                                    paisRepository.save(nuevoPais);
                                    System.out.println("✅ Actualizado: " + nombrePais);
                                },
                                () -> {
                                    paisRepository.save(nuevoPais);
                                    System.out.println("✅ Guardado: " + nombrePais);
                                }
                        );
            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("⚠️ Código " + codigo + " no encontrado (404).");
            } catch (Exception e) {
                System.out.println("❌ Error al procesar código " + codigo + ": " + e.getMessage());
            }
        }
    } */

    // 5.1 Seleccionar países de la región Americas
    public void getPaisesDeAmerica() {
        Query query = new Query().addCriteria(Criteria.where("region").is("Americas"));
        List<Pais> paises = mongoTemplate.find(query, Pais.class);
        paises.forEach(System.out::println);
    }

    // 5.2 Seleccionar países de Americas con población mayor a 100 millones
    public void getPaisesDeAmericaConAltaPoblacion() {
        Query query = new Query().addCriteria(
                Criteria.where("region").is("Americas").and("poblacion").gt(100000000)
        );
        List<Pais> paises = mongoTemplate.find(query, Pais.class);
        paises.forEach(System.out::println);
    }

    // 5.3 Seleccionar países donde la región no sea Africa
    public void getPaisesNoAfrica() {
        Query query = new Query().addCriteria(Criteria.where("region").ne("Africa"));
        List<Pais> paises = mongoTemplate.find(query, Pais.class);
        paises.forEach(System.out::println);
    }

    // 5.4 Actualizar nombre y población de Egypt
    public void actualizarEgypt() {
        Query query = new Query().addCriteria(Criteria.where("nombrePais").is("Egypt"));
        Update update = new Update().set("nombrePais", "Egipto").set("poblacion", 95000000);
        mongoTemplate.updateFirst(query, update, Pais.class);
        System.out.println("✅ País actualizado: Egypt -> Egipto");
    }

    // 5.5 Eliminar país con código 258
    public void eliminarPais258() {
        Query query = new Query().addCriteria(Criteria.where("codigoPais").is("258"));
        mongoTemplate.remove(query, Pais.class);
        System.out.println("✅ País con código 258 eliminado");
    }

    // 5.6 ¿Qué sucede con drop()?
    // El método drop() sobre una colección elimina todos los documentos dentro de la colección y la colección misma.
    // Si se ejecuta sobre una base de datos, elimina todas las colecciones y la base de datos en sí.

    // 5.7 Seleccionar países con población entre 50 y 150 millones
    public void getPaisesPorRangoPoblacion() {
        Query query = new Query().addCriteria(Criteria.where("poblacion").gt(50000000).lt(150000000));
        List<Pais> paises = mongoTemplate.find(query, Pais.class);
        paises.forEach(System.out::println);
    }

    // 5.8 Seleccionar países ordenados por nombre en forma ascendente
    public void getPaisesOrdenadosPorNombre() {
        Query query = new Query().with(Sort.by(Sort.Direction.ASC, "nombrePais"));
        List<Pais> paises = mongoTemplate.find(query, Pais.class);
        paises.forEach(System.out::println);
    }

    // 5.9 ¿Qué hace skip()?
    // El método skip() omite un número específico de documentos en una consulta.
    // Ejemplo: mongoTemplate.find(new Query().skip(10), Pais.class) omitiría los primeros 10 documentos.

    // 5.10 Expresiones regulares en MongoDB como alternativa a LIKE
    // MongoDB permite usar regex en las consultas. Ejemplo:
    // Query query = new Query(Criteria.where("nombrePais").regex("^A.*")); // Selecciona países que comienzan con 'A'
    // Esto equivale a "LIKE 'A%'" en SQL.

    // 5.11 Crear índice en el campo códigoPais
    public void crearIndiceCodigoPais() {
        mongoTemplate.getCollection("pais").createIndex(new org.bson.Document("codigoPais", 1));
        System.out.println("✅ Índice creado en códigoPais");
    }

    // 5.12 ¿Cómo hacer backup de una base MongoDB?
    // Se puede usar el comando: mongodump --db paises_db --out /ruta/del/backup
    // Esto crea un respaldo de la base en la carpeta especificada.
}