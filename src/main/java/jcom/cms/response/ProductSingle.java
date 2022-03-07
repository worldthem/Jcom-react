package jcom.cms.response;

import jcom.cms.entity.Category;
import jcom.cms.entity.Product;
import jcom.cms.pojo.Attr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSingle {
    private Product product;
    private LinkedHashMap<String, Attr> attributes;
    private Map<String, Map<String, Map<String,String>>> suggestions;
    private List<Category> categories;
    private Map<String, String>  categoriesType;
}
