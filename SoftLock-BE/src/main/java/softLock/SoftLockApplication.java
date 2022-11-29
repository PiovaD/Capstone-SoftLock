package softLock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import softLock.entities.posts.Answer;

import java.time.*;

@SpringBootApplication
public class SoftLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftLockApplication.class, args);
		System.out.println("DAJE");

	}

}
