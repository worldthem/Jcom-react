package jcom.cms.response;

import jcom.cms.entity.Category;
import jcom.cms.entity.Pages;
import jcom.cms.entity.Settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSingleResponse {
    private Settings settings;
    private Map<String, String> customLinks ;
    private List<Pages> pages ;
    private List<Category> categories;
    private Map<String, String> typeOfCategories;
}
