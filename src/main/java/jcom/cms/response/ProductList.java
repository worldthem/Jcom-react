package jcom.cms.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductList {
    private Integer productid;
    private Integer userid;
    private String sku;
    private Integer qtu;
    private String title;
    private String cpu;
    private String stock;
    private Float price;
    private Float salePrice;
    private String attr;
    private String image;
    private Integer catid;
    private String ctitle;
    private String ccpu;

}
