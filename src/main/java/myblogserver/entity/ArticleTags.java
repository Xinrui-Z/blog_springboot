package myblogserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleTags {

    @Id
    @CreatedBy
    private Long id;

    private String tagName;

    private String tagValue;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;
}
