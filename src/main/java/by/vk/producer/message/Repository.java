package by.vk.producer.message;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface Repository extends ReactiveCrudRepository<Message, Long> {}
