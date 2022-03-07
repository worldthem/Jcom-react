package jcom.cms.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Maildto {
    private String fromemail ="";
    private String fromname="";
    private String host="";
    private String port="";
    private String username="";
    private String password="";


}