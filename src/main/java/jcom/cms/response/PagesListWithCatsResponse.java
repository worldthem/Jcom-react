package jcom.cms.response;

import jcom.cms.entity.Category;
import jcom.cms.entity.Pages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagesListWithCatsResponse {
    private Page<Pages> pages;
    private List<Category> categories;
}
