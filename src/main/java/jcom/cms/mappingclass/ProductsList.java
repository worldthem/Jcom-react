package jcom.cms.mappingclass;

import jcom.cms.entity.ProductCategories;
import java.util.List;

public interface ProductsList {
    String getProductid();
    String getUserid();
    String getCpu();
    String getMetad();
    String getMetak();
    String getTitle();
    String getPrice();
    String getSalePrice();
    String getAttr();
    String getOptionsdata();
    String getImage();
    String getStatus();
    String getFirstName();
    String getCatid();
    String getCattitle();
    String getCatcpu();
    List<ProductCategories> getR();
}
