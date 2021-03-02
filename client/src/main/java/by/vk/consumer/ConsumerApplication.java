package by.vk.consumer;

import by.vk.consumer.message.Message;
import java.net.URI;
import java.time.Duration;
import java.util.stream.LongStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@SpringBootApplication
public class ConsumerApplication {

  private static final RetryBackoffSpec BACK_OFF = Retry.backoff(10L, Duration.ofSeconds(10L));
  private static final String SERVER_URI_VALUE = "ws://127.0.0.1:8888/";

  @Bean
  RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
    return builder
        .rsocketConnector(connector -> connector.reconnect(BACK_OFF))
        .websocket(URI.create(SERVER_URI_VALUE));
  }

  @Bean
  ApplicationListener<ApplicationReadyEvent> ready(RSocketRequester client) {

    return event -> {
      LongStream.range(1, 100_000)
          .forEach(
              index ->
                  client
                      .route("messages.create")
                      .data(new Message(null, "[" + index + "] I'm so fucking reactive!"))
                      .retrieveMono(Long.class)
                      .subscribe(it -> System.out.println(it)));

      LongStream.range(1, 100_000)
          .forEach(
              index ->
                  client
                      .route("messages.get.{id}", index)
                      .retrieveMono(Message.class)
                      .subscribe(it -> System.out.println(it)));

      client
          .route("messages.get")
          .retrieveFlux(Message.class)
          .subscribe(it -> System.out.println(it));
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
