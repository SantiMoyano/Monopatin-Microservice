package poroto.po.monopatin.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import poroto.po.monopatin.entity.Monopatin;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.client.RestTemplate;

// import poroto.po.viaje.entity.Monopatin;

@Service
// @RestController
// @RequestMapping("/dameUno")
public class ParadaServicio {
    // private final String direccion = "http://localhost:8093/parada";
    @Value("${paradaURL}")
    private String paradaURL;
    
    private final RestTemplate rest;

    @Autowired
    public ParadaServicio(RestTemplate rest) {
        this.rest = rest;
    }

    @GetMapping
    public ResponseEntity<String> dameParadas() {
        return rest.getForEntity(paradaURL+"/parada", String.class);
    }

    public Boolean estaEstacionado(Monopatin m){
        Integer lon=m.getLongitud();
        Integer lat=m.getLatitud();
        return rest.getForEntity(paradaURL+"/estacionado/"+lon+"/"+lat, Boolean.class).getBody();
    }

}
