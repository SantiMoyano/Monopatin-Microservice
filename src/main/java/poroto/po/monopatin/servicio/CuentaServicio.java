package poroto.po.monopatin.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class CuentaServicio {
    private final String direccion = "http://localhost:8083/tieneSaldo";

    private final RestTemplate rest;

    @Autowired
    public CuentaServicio(RestTemplate rest) {
        this.rest = rest;
    }

    @GetMapping
    public Float tieneSaldo(Long id) {
        return rest.getForEntity(direccion+"/"+id, Float.class).getBody();
    }

}