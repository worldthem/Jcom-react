package jcom.cms.entity;

import com.stripe.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name =  "coupons_applied" )
public class CouponsApplied implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 40)
    private String userid;

    private Integer ordersid;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="couponid")
    private Coupons coupon;
}
