package by.vk.consumer.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
  private Long id;
  private String value;
}
