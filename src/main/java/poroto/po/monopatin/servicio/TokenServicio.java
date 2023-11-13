package poroto.po.monopatin.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenServicio {

    @Value("${token}")
    private String token;

    private final RestTemplate rest;
    
    
    
    
    @Autowired
    public TokenServicio(RestTemplate rest) {
        this.rest = rest;
    }
    
    public Boolean autorizado(String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
       // Extrae el token después de "Bearer "
    //    String token = authorizationHeader.substring(7);
    //    System.out.println("Token Bearer: " + token);
         return rest.getForEntity(token+"/"+authorizationHeader, Boolean.class).getBody();

       } else {
           // return "No se proporcionó un token Bearer válido en el encabezado Authorization.";
       }
       return false;
   }
    // public Float tieneSaldo(Long id) {
    //     return rest.getForEntity(direccion+"/"+id, Float.class).getBody();
    // }
    
}
