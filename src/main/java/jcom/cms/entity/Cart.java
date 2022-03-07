package jcom.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity // This tells Hibernate to make a table out of this class
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cart  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer cartid;
    @Column(length = 5)
    private Integer typecart;

    @Column(length = 40)
    private String userid;

    @Column(length = 5)
    private Integer qty;

    @Column(length = 5)
    private Integer downloaded;

    @Column(length = 30)
    private String optionsid;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price  = 0.00f;

    private LocalDateTime date;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

    private Integer ordersid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productid")//, insertable=false, updatable=false
    private Product product;

 /*
    public Float getTotalRow(){
        Float price = product.getPriceFull();
        if( optionsid != null){
            Float priceOption = product.getAttrMapModule().containsKey(optionsid)?  Float.parseFloat(product.getAttrMapModule().get(optionsid).get("price")) : price;
            price = priceOption > 0 ? priceOption : price;
        }
        return qty*price;
    }
    public Float getWight(){
        Float wight = product.getWeight();
        if( optionsid != null){
            Float wightOption = product.getAttrMapModule().containsKey(optionsid)?  Float.parseFloat(product.getAttrMapModule().get(optionsid).get("weight")) : wight;
            wight = wightOption > 0 ? wightOption : wight;
        }
        return qty*wight;
    }

  */
}
