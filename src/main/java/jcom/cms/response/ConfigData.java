package jcom.cms.response;

import jcom.cms.entity.Settings;
import jcom.cms.pojo.Currency;
import jcom.cms.pojo.MainOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigData {
    private List<Currency> currencies = new ArrayList<>();
    private Currency currency = new Currency();
    private MainOptions mainOptions = new MainOptions();
    private List<Settings> settings = new ArrayList<>();
    private Map<String,String> translate = new HashMap<>();
    private List<String> languages = new ArrayList<>();
}
