package devinc.dwitter.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TweetDto {

    @Size(min = 1, max = 200)
    private String content;

    private UUID repostedTweetId;
    private String topic;
}
