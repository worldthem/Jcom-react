package jcom.cms.response;

import jcom.cms.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCategory {
    private String response;
    private Category category;
}
