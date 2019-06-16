package top.swiftr.book_shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class LoginLog {
    @Id
    private Integer id;

    private String username;

    private String ip;

    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    private String device;
}
