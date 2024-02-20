package fr.btn.SpingDbProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
//@AutoConfigureTestDatabase
//@TestPropertySource(locations = "classpath:application.properties")
//@ContextConfiguration(classes = DatabaseConfig.class, loader = AnnotationConfigContextLoader.class)
class BookApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
