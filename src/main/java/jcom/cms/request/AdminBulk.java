package jcom.cms.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminBulk {
    private List<String> id = new ArrayList<>();
    private String action;
    private String categoryId;
}
