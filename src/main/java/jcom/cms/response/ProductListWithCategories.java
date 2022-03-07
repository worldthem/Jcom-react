package jcom.cms.response;

import jcom.cms.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductListWithCategories {
    private Page<ProductList> list;
    private List<Category> categories;
}
