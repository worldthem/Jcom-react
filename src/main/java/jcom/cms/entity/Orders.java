package jcom.cms.entity;

import jcom.cms.helpers.HelpersJson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity // This tells Hibernate to make a table out of this class
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ordersid;

    @Column(length = 10)
    private Integer userid;

    @Column(length = 70)
    private String sessionuser;

    @Column(length = 100)
    private String secretnr;

    @Column(length = 100)
    private String paymenttoken;

    @Column(columnDefinition = "TEXT")
    private String shipping;

    @Column(columnDefinition = "TEXT")
    private String options;

    @Column(length = 100)
    private String status;

    @Column(length = 150)
    private String message;

    @Column(length = 150)
    private String totalpayd;

    @Column(length = 10)
    private Integer shippingid;

    @Column(length = 150)
    private String paymentmethod;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ordersid") //, insertable=false, updatable=false
    private List<Cart> cart;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shippingid", insertable=false, updatable=false)
    private Shipping shippingtable;

    public Shipping getShipingTable() {
        try {
            if(getShippingid()>0){
                String shipn = shippingtable.getShipping_name();
                return shippingtable;
            }
            return null;
        }catch (Exception e){
            return null;
        }


    }


    public String getShipping() {
        return shipping == null ? "" : shipping;
    }

    public Map<String,String> getShippingMap() {
        return HelpersJson.convertJsonToMap(shipping);
    }

    public Float getTotalpayd() {
        return totalpayd != null ? Float.parseFloat(totalpayd ) : 0.0f;
    }

    public Integer getShippingid() {
        return shippingid == null ? 0 : shippingid;
    }

    public String getPaymentmethod() {
        return paymentmethod ==null? "": paymentmethod;
    }

}
