package jcom.cms.response;

import jcom.cms.pojo.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrenciesResponse {
    private LinkedHashMap<String, String> jsonFileData;
    private LinkedHashMap<String, Currency> dbData = new LinkedHashMap<>();
}
