package myblogserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @CreatedBy
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    private String number;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickName;

    private String avatarUrl;

    private String email;

    private String github;

    private String sign;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;

}
