import com.myluffy.springboot.WebfluxFunctionalApplication;
import com.myluffy.springboot.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Basic integration tests for WebFlux application.
 *
 * @author Brian Clozel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = WebfluxFunctionalApplication.class)
public class SampleWebFluxApplicationTests {

	@Autowired
	private WebTestClient webClient;


	@Test
	public void testEcho() {
		this.webClient.post().uri("/echo").contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.TEXT_PLAIN)
				.body(Mono.just("Hello WebFlux!"), String.class).exchange()
				.expectBody(String.class).isEqualTo("Hello WebFlux!");
	}



}