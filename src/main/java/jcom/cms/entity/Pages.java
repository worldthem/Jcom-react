package jcom.cms.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Pages implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer pageid;

    private Integer sort;

    @Column(length = 20)
    private String type;

    @Column(length = 300)
    private String cpu;

    @Column(length = 300)
    private String title;

    @Column(length = 512)
    private String metad;

    @Column(length = 512)
    private String metak;

    @Column(length = 512)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String style;

    @Column(columnDefinition = "TEXT")
    private String css;

    @Column(columnDefinition = "TEXT")
    private String options;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @Column(length = 20)
    private String direction;

    @Column(length = 4)
    private Integer status= 1;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

    @Transient
    private String lang;

    @Transient
    private Map<String, Map<String,String>> styleCollection;

    @ManyToMany(fetch = FetchType.EAGER) //, cascade = CascadeType.ALL
    @JoinTable( name = "pages_categories",
                joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "pageid", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "catid",nullable = false))
    private List<Category> categories;

    @Transient
    private List<Integer> catids;

    public List<Integer> getCatids(){

        if(this.categories==null)
            return new ArrayList<>();

        List<Integer>  ids = new ArrayList<>();
        categories.forEach(v->ids.add(v.getCatid()));
        return ids;
    }


    public Map<String, Map<String,String>> getStyleCollection() {
        try {
            Type type = new TypeToken<Map<String, Map<String,String>>>(){}.getType();
            return style==null ? new HashMap<>() : (new Gson()).fromJson(style, type);
        }catch (Exception e){}

        return new HashMap<>();
    }
}
