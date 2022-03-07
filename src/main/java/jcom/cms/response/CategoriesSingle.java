package jcom.cms.response;

import jcom.cms.entity.Category;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriesSingle {
     private List<Category> categories;
     private Category category = new Category();
}
