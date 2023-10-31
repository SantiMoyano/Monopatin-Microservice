package poroto.po.monopatin.servicio;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class ViajeServicio {
    private final String direccion = "http://localhost:8081/iniciar";

    private final RestTemplate rest;

    @Autowired
    public ViajeServicio(RestTemplate rest) {
        this.rest = rest;
    }

    // @PostMapping("/{}
    public Boolean iniciar(Long idMono, Long idCuenta) {
        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("idMono", idMono);
        requestBody.put("idCuenta", idCuenta);
        return  rest.postForEntity(direccion, requestBody, Boolean.class).getBody();
    }

}