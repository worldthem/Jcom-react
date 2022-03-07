package jcom.cms.pojo;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attr {
    private Map<String,String> name;
    private String box;
    private String type;
}
