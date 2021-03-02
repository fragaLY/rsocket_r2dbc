package by.vk.producer.message;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class Api {

  private final Repository repository;

  @MessageMapping("messages.create")
  Mono<Long> create(Message payload) {
    return repository.save(payload).map(Message::getId);
  }

  @MessageMapping("messages.get.{id}")
  Mono<Message> get(@DestinationVariable Long id) {
     return repository.findById(id);
  }

  @MessageMapping("messages.get")
  Flux<Message> get() {
    return repository.findAll();
  }
}
