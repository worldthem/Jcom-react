package jcom.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(length = 10,name = "productid")
    private Integer productid;

    @Column(length = 5)
    private Integer userid;

    @Column(length = 255, name = "catid")
    private String catid;

    @Column(length = 100, name = "sku")
    private String sku;

    private Integer qtu = 0;
 
    @Column(length = 5)
    private Integer store;

    @Column(length = 512)
    private String title;

    @Column(length = 512)
    private String cpu;

    @Column(length = 512)
    private String metad;

    @Column(length = 512)
    private String metak;
 
    @Column(columnDefinition = "TEXT")
    private String description;
 
    @Column(columnDefinition = "TEXT")
    private String text;
 
    @Column(columnDefinition="Decimal(10,2)")
    private Float weight ;

    @Column(length = 10)
    private String stock;

    @Column(columnDefinition="Decimal(10,2)")
    private Float price;
 
    @Column(columnDefinition="Decimal(10,2)", name = "sale_price")
    private Float salePrice;
 
    @Column(columnDefinition = "TEXT")
    private String attr;
 
    @Column(columnDefinition = "TEXT")
    private String optionsdata;

    @Column(length = 120)
    private String image;

    @Column(length = 5)
    private Integer status= 1;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

     //@OneToOne(fetch = FetchType.LAZY)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn(name = "productid", insertable=false, updatable=false)
    private List<Media> media;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", insertable=false, updatable=false)
    private  List<Comments> comments;

    @Transient
    private String lang;


    @ManyToMany(fetch = FetchType.EAGER) //, cascade = CascadeType.ALL
    @JoinTable( name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "productid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "catid",nullable = false))
    private List<Category> categories;

    @Transient
    private List<Integer> catids;

    public List<Integer> getCatids(){
        if(this.categories == null)
            return new ArrayList<>();

        List<Integer> ids = new ArrayList<>();
        categories.forEach(v->ids.add(v.getCatid()));
        return ids;
    }

    public Product(Integer productid, Integer userid, String sku, Integer qtu, String title,
                   String cpu, String stock, Float price, Float salePrice, String attr, String image, List<Category> categories) {
        super();
        this.productid = productid;
        this.userid = userid;
        this.sku = sku;
        this.qtu = qtu;
        this.title = title;
        this.cpu = cpu;
        this.stock = stock;
        this.price = price;
        this.salePrice = salePrice;
        this.attr = attr;
        this.image = image;
        this.categories = categories;
    }


//@OneToOne(targetEntity = Gallery.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name ="productid",referencedColumnName = "productid")
    //private Gallery gallery ;

/*
    @OneToMany(targetEntity = Gallery.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="productid",referencedColumnName = "productid")
    private List<Gallery> galleries;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
*/
/*
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }
 
    public Integer getQtu() {
        return qtu == null? 0 :qtu ;
    }

    public void setQtu(Integer qtu) {
        this.qtu = qtu==null  ? 0 : qtu;
    }
 
    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }
 

 
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = isEmpty(cpu)  ?  Helpers.slug(this.title) : Helpers.slug(cpu);
    }

    public String getUrl() {
        return Helpers.productUrl(cpu, catid );
    }

  // Translate
    public String getTitle() {
        return Helpers.splitLang(title);
    }

    public void setTitle(String title) {
        this.title = Helpers.saveLang(title, this.title, this.lang);
    }

    public String getMetad() {
        return Helpers.splitLang(metad);
    }

    public void setMetad(String metad) {
        this.metad =Helpers.saveLang(metad, this.metad, this.lang) ;
    }

    public String getMetak() {
        return Helpers.splitLang(metak);
    }

    public void setMetak(String metak) {
        this.metak =  Helpers.saveLang(metak, this.metak, this.lang) ;
    }
    public String getText() {
        return Helpers.splitLang(text);
    }

    public void setText(String text) {
        this.text = Helpers.saveLang(text, this.text, this.lang) ;
    }

    public String getDescription() {
        return Helpers.splitLang(description);
    }

    public void setDescription(String description) {
        this.description = Helpers.saveLang(description, this.description, this.lang);
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight==null ? 0.0f : weight;
    }
 
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price==null ? 0.0f : price;
    }
 
    public Float getSale_price() {
        return sale_price;
    }

    public void setSale_price(Float sale_price) {
        this.sale_price = sale_price==null ? 0.0f : sale_price;
    }
    public Float getPriceFull(){
         return sale_price != null ? (sale_price > 0 ? sale_price : price) : price;
    }

    public Float getFinalPrice(){
       return sale_price > 0 ? sale_price : price;
    }

    public String getPriceHtml(){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //ViewHelpersRequest v= new ViewHelpersRequest(request);
        return Helpers.price(price, sale_price );
    }


    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }

    public Map<String, Map<String, String>> getAttrMapMap() {
        Map<String, Map<String, String>> map = new LinkedHashMap<>();

        if(attr != null){
             Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
             try{
                 return  Helpers.isJSONValid(attr) !=null ? (new Gson()).fromJson(attr, type) : map;
             }catch (Exception d){
                 return map;
             }

         }else{
             return map;
        }
      }

    public Map<String, ProductVariations> getAttrMapModule() {
        Map<String, ProductVariations> map = new LinkedHashMap<>();

        if(attr != null){
            Type type = new TypeToken<Map<String, ProductVariations>>(){}.getType();
            return  Helpers.isJSONValid(attr) !=null ? (new Gson()).fromJson(attr, type) : map;
        }else{
            return map;
        }
    }



    public String getAttrNames(String OptionId){
        Attributes attributes = Helpers.getAttrSettings();
        String retData="";
        Map<String, Map<String, String>> map = getAttrMapMap();
        if(map.size()>0 && !OptionId.isEmpty()){
            if(map.containsKey(OptionId)) {
                Map<String, String> option = map.get(OptionId);
                for(Map.Entry<String, String> entry: option.entrySet()) {

                    if(attributes.getFields().containsKey(entry.getKey())) {
                        retData = retData + attributes.getFields().get(entry.getKey()).getNameTranslated()+" : "+ attributes.getFields().get(entry.getKey()).getSugestionByKey(entry.getValue())+"; ";
                    }
                }
            }
        }
        return retData;
    }

 
    public String getOptionsdata() {
        return optionsdata;
    }

    public void setOptionsdata(String optionsdata) {
        this.optionsdata = optionsdata;
    }
 
    public Integer getHide() {
        return  hide==null ? 0 :hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide==null ? 0 :hide;
    }
 
    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
 
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getCatid() {
        return catid;
    }

    public CatJson getCat() {
        List<String> list = HelpersJson.isJSONArrValid(catid);


        try {
           return list !=null ? (Helpers.getConfig().getCategories().containsKey(list.get(0)) ? Helpers.getConfig().getCategories().get(list.get(0)) : new CatJson() )  : new CatJson();
        }catch (Exception e){
            return new CatJson();
        }
     }

    public void setCatid(String catid) {
        this.catid = catid;
    }



    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image == null ? "noimage.jpg" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getStock() {
        return stock == null ? "yes" : stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        try {
            Users user = users;
            String name= user.getFirstName();
            return user;
        }catch (Exception e){
            return new Users();
        }
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFlullTitle() { return title; }
    public String getFlullMetak() { return metak; }
    public String getFlullMetad() {  return metad; }
    public String getFlullDescription() {  return description; }
    public String getFlullText() { return text;}

    public void setter(Product entity, String lang){
        setLang(lang);
        setCpu(this.cpu);
        this.title =  Helpers.saveLang(this.title, entity.getFlullTitle(), lang);
        this.metad=  Helpers.saveLang(this.metad, entity.getFlullMetad(), lang);
        this.metak=  Helpers.saveLang(this.metak, entity.getFlullMetak(), lang);
        this.text=  Helpers.saveLang(this.text, entity.getFlullText(), lang);
        this.description=  Helpers.saveLang(this.description, entity.getFlullDescription(), lang);
    }

 */




}
