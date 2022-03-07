package jcom.cms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutField {
    private LinkedHashMap<String,String> title;
    private String showTitle;
    private String required;
    private String boxLength;
    private String boxType;
}
