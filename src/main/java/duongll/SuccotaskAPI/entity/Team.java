package duongll.SuccotaskAPI.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_teams")
@Getter
@Setter
public class Team implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(100)")
    private String name;

}
