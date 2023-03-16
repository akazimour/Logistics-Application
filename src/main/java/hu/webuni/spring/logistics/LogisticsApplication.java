package hu.webuni.spring.logistics;

import hu.webuni.spring.logistics.configuration.DelayConfiguration;
import hu.webuni.spring.logistics.service.InitDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(DelayConfiguration.class)
public class LogisticsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}


	@Autowired
	InitDbService initDbService;

	//Megjegyzés: a lenti insertAddress metódust ki kell kommentelni az Integrációs tesztek futtatásához
	//initDbService.insertAddress(); -> hozzáad Transport-planeket és adresseket a db-hez. Listázó végponton az összes látható


	@Override
	public void run(String... args) throws Exception {
	initDbService.insertAddress();

	}
}
