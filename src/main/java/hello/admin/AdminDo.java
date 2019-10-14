package hello.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenchx on 2019/1/3.
 */
@Entity
@Table(name="sys_admin")
@Getter
@Setter
public class AdminDo implements Serializable {
    private static final long serialVersionUID = 53806437996019542L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private Long idOwnOrg;

}

