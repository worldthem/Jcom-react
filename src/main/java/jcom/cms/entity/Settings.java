package jcom.cms.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jcom.cms.pojo.Attr;
import jcom.cms.pojo.Lang;
import jcom.cms.pojo.MainOptions;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity // This tells Hibernate to make a table out of this class

public class Settings implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(length = 150)
    private String param;

    @Column(length = 5592415)
    private String value;

    @Column(length = 5592415)
    private String value1;

    @Column(length = 255)
    private String value2;

    @Column(length = 3)
    private String autoload;


   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
 
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
 
    public String getValue1() {
        return value1;
    }


  /*
    public Lang getValue1AllLang() {
        try{
            Gson gson = new Gson();
            return gson.fromJson(value1, (Type) Lang.class);
        }catch (Exception e){
            return new Lang();
        }
    }*/


    public Map<String, String> getValue1Map() {
       try {
           Type type = new TypeToken<Map<String, String>>(){}.getType();
           return value1==null ? new HashMap<>() : (new Gson()).fromJson(value1, type);
       }catch (Exception e){}

       return new HashMap<>();
    }

    public Map<String, Map<String,String>> getValue1MapInMap() {
        try {
            Type type = new TypeToken<Map<String, Map<String,String>>>(){}.getType();
            return value1==null ? new HashMap<>() : (new Gson()).fromJson(value1, type);
        }catch (Exception e){}

        return new HashMap<>();
    }

    //convert value 1 to MainOptions object
    public MainOptions getMainOptions() {
        try {
            Type type = new TypeToken<MainOptions>(){}.getType();
            return value1==null ? new MainOptions() : (new Gson()).fromJson(value1, type);
        }catch (Exception e){
            System.out.println("error to convert:"+e.getMessage());
        }

        return new MainOptions();
    }


    public LinkedHashMap<String, Attr> getAttributes() {
        try {
            Type type = new TypeToken<LinkedHashMap<String, Attr>>(){}.getType();
             Gson gson = new GsonBuilder().serializeNulls().create();
            return value1==null ? new LinkedHashMap<>() : gson.fromJson(value1, type);
        }catch (Exception e){}

        return new LinkedHashMap<>();
    }

    public List<String> getValue1List() {
       try {
            Type type = new TypeToken<List<String>>(){}.getType();
            return value1==null ? new ArrayList<>() : (new Gson()).fromJson(value1, type);
       }catch (Exception e){}

       return new ArrayList<>();
    }



  }
