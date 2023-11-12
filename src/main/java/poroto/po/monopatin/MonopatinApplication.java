package poroto.po.monopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import poroto.po.monopatin.jwt.JWTAuthorizationFilter;
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

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/monopatin","/monopatin/dameToken").permitAll()
				.anyRequest().authenticated();
		}
	}

}
