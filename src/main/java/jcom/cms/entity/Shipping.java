package jcom.cms.entity;

import com.google.gson.Gson;
import lombok.*;

import javax.persistence.*;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 3)
    private Integer type_shipping;

    @Column(length = 255)
    private String shipping_name;

    @Column(length = 5)
    private Integer store;

    private Integer country;

    @Column(columnDefinition="Decimal(10,2)")
    private Float weight;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price;

    @Column(columnDefinition="Decimal(10,2)")
    private Float free_delivery;

    @Column(length = 5)
    private Integer status= 1;

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(Map<String, String> shipping_name) {
        this.shipping_name = (new Gson()).toJson(shipping_name);
    }

    //public void setShipping_name(String shipping_name) {
        //this.shipping_name = shipping_name;
    //}
}
