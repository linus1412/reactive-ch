package uk.co.smitek.reactivech;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

@SpringBootApplication
public class ReactiveChApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactiveChApplication.class, args);
  }

  @Component
  public class AppStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

      final WebClient webClient = WebClient.builder()
        .baseUrl("https://stream.companieshouse.gov.uk/insolvency-cases?timepoint=39100")
        .defaultHeader(
          "Authorization",
          "Basic " + Base64.getEncoder().encodeToString( "api-key-here".concat(":").getBytes()))
        .build();

      final WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec = webClient.get();
      final WebClient.ResponseSpec retrieve = requestHeadersUriSpec.retrieve();
      final Flux<String> flux = retrieve.bodyToFlux(String.class).log();

      flux.subscribe();

    }
  }
  


}
