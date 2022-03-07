package jcom.cms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVars {
    private LinkedHashMap<String, LinkedHashMap<String,String>> adminMenu;
}
