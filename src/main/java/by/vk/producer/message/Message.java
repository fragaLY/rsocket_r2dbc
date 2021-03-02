package by.vk.producer.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
public class Message {
  @Id @EqualsAndHashCode.Exclude private Long id;
  private String value;
}
