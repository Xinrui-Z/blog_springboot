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
public class Article {

    @Id
    @CreatedBy
    private Long id;

    private String articleLabel;

    private String articleTitle;

    private String articleImg;

    private String articleContent;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;
}
