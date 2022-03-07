package jcom.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Media implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 255)
    private String sesion_id;

    @Column(name = "productid", nullable=false)
    private Integer productid;

    private Integer pageid;

    private Integer userid;
 
    @Column(columnDefinition = "TEXT")
    private String directory;

    @Column(length = 512)
    private String title;
 
    @Column(length = 5)
    private Integer sort;
 
    @Column(length = 20)
    private String typefile ;
 
    @Column(length = 5)
    private Integer importnr;

}
