package poroto.po.monopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * Esta clase no sirve para nada
 * @author Grupo de Arquitectura
 * @version 1.1.1
 * 
 */
@SpringBootApplication
public class MonopatinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonopatinApplication.class, args);
	}

	
	/** 
	 * Este metodo es un bean para los servicios
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
