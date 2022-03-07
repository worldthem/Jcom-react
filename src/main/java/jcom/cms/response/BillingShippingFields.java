package jcom.cms.response;


import jcom.cms.pojo.CheckoutField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingShippingFields {
    LinkedHashMap<String, String> configFields;
    LinkedHashMap<String, CheckoutField> dbFields = new LinkedHashMap<>();
    LinkedHashMap<String, String> title = new LinkedHashMap<>();

}
