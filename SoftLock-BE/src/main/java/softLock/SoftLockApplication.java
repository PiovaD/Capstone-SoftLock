package softLock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SoftLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftLockApplication.class, args);
		System.out.println("Ready");
	}

}
