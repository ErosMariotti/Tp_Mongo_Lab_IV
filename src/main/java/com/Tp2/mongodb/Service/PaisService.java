package com.Tp2.mongodb.Service;

import com.Tp2.mongodb.Entity.Pais;
import com.Tp2.mongodb.Repository.PaisRepository;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.json.JSONObject;
import java.util.Optional;

@Service
public class PaisService {
    private final PaisRepository paisRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public void migrarPaises() {
        for (int codigo = 1; codigo <= 300; codigo++) {
            String url = "https://restcountries.com/v3.1/alpha/" + String.format("%03d", codigo);
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                String jsonResponse = response.getBody();

                if (jsonResponse == null || jsonResponse.isEmpty()) {
                    System.out.println("❌ Código " + codigo + " no tiene JSON válido.");
                    continue;
                }

                // Convertir JSON en array
                JSONArray jsonArray = new JSONArray(jsonResponse);
                if (jsonArray.isEmpty()) {
                    System.out.println("⚠️ Código " + codigo + " no tiene datos.");
                    continue;
                }

                JSONObject json = jsonArray.getJSONObject(0); // Extraer el primer objeto

                String nombrePais = json.getJSONObject("name").optString("common", "Desconocido");
                String capitalPais = json.has("capital") ? json.getJSONArray("capital").optString(0, "No tiene") : "No tiene";
                String region = json.optString("region", "No especificado");
                String subregion = json.optString("subregion", "No especificado");
                long poblacion = json.optLong("population", 0);
                double latitud = json.has("latlng") ? json.getJSONArray("latlng").optDouble(0, 0.0) : 0.0;
                double longitud = json.has("latlng") ? json.getJSONArray("latlng").optDouble(1, 0.0) : 0.0;

                Pais nuevoPais = new Pais(codigo, nombrePais, capitalPais, region, subregion, poblacion, latitud, longitud);
                paisRepository.save(nuevoPais);
                System.out.println("✅ Guardado: " + nombrePais);

            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("⚠️ Código " + codigo + " no encontrado (404).");
            } catch (Exception e) {
                System.out.println("❌ Error al procesar código " + codigo + ": " + e.getMessage());
            }
        }
    }
}