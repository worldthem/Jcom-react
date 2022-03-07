package jcom.cms.response;

import jcom.cms.entity.Category;
import jcom.cms.entity.Pages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageFullResponse {
    private Pages page;
    private List<Category> categories;
    private Map<String,String> catTypes;
}
