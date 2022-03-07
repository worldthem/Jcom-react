package jcom.cms.request;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class SettingsIn {
    private String value;
    private String value1;

}
