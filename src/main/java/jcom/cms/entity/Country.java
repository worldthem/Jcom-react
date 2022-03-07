package jcom.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Country  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 200)
    private String country;

    @Column(length = 15)
    private String code;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price;

    @Column(length = 200)
    private String shipping;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price1;

    @Column(length = 200)
    private String shipping1;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price2;

    @Column(length = 200)
    private String shipping2;
}
