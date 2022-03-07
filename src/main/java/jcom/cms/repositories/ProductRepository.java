package jcom.cms.repositories;



import jcom.cms.entity.Product;
import jcom.cms.mappingclass.ProductData;
import jcom.cms.mappingclass.ProductsList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

     Product findByProductid(Integer id);
     Product findFirstByCpu(String cpu);
     Product getOne(Integer id);
     Page<Product> findByTitleContaining(String title, Pageable pageable);
     Page<Product> findByCategories_CatidOrderByProductidDesc(Integer catid, Pageable pageable);

     @Query(value ="SELECT p.productid as productid, p.userid as userid,  p.cpu as cpu, " +
                    " p.metad as metad, p.metak as metak, p.title as title, "+
                    " p.price as price, p.salePrice as salePrice, p.attr as attr, p.optionsdata as optionsdata," +
                    " p.image as image, p.status as status, u.firstName as firstName, " +
                    " c.title as cattitle, c.cpu as catcpu, r.cid as catid  " +
                    " FROM Product p " +
                    " LEFT JOIN Users u ON p.userid = u.userid " +
                    " LEFT JOIN ProductCategories r ON r.pid = p.productid " +
                    " LEFT JOIN Category c ON c.catid = r.cid " +
                    " ORDER BY p.productid DESC" )
     Page<ProductsList>getAllProducts(Pageable pageable);


     @Query(value ="SELECT p.productid as productid, p.userid as userid,  p.cpu as cpu, " +
             " p.metad as metad, p.metak as metak, p.title as title, "+
             " p.price as price, p.salePrice as salePrice, p.attr as attr, p.optionsdata as optionsdata," +
             " p.image as image, p.status as status, u.firstName as firstName, " +
             " c.title as cattitle, c.cpu as catcpu, r.cid as catid " +
             " FROM Product p " +
             " LEFT JOIN Users u ON p.userid = u.userid " +
             " JOIN ProductCategories r ON r.pid = p.productid " +
             " LEFT JOIN Category c ON c.catid = r.cid " +
             " WHERE r.cid = ?1 ORDER BY p.productid DESC" )
     Page<ProductsList>findByCatid(Integer catid, Pageable pageable);

     @Query(value ="SELECT p.productid as productid, p.userid as userid, p.catid as catid, p.cpu as cpu, " +
             " p.metad as metad, p.metak as metak, p.title as title, "+
             " p.price as price, p.salePrice as salePrice, p.attr as attr, p.optionsdata as optionsdata," +
             " p.image as image, p.status as status, u.firstName as firstName " +
             " FROM Product p LEFT JOIN Users u ON p.userid = u.userid " +
             " WHERE p.title LIKE %?1% ORDER BY p.productid DESC" )
     Page<ProductsList>findByTitle(String title, Pageable pageable);

     @Query(value ="SELECT p.productid as productid, p.userid as userid, p.catid as catid, p.cpu as cpu, " +
             " p.metad as metad, p.metak as metak, p.title as title, "+
             " p.price as price, p.salePrice as salePrice, p.attr as attr, p.optionsdata as optionsdata," +
             " p.image as image, p.status as status, u.firstName as firstName " +
             " FROM Product p LEFT JOIN Users u ON p.userid = u.userid " +
             " WHERE p.userid = ?1  ORDER BY p.productid DESC" )
     Page<ProductsList>findByUserid(Integer userid, Pageable pageable);


     @Query(value ="SELECT p.productid as productid, p.catid as catid, p.salePrice as salePrice FROM Product p ORDER BY p.productid DESC " )
     Page<ProductData>getAllProducts1(Pageable pageable);

     Page<Product> findAllByOrderByProductidDesc(Pageable pageable);

     Page<Product> findByCatidContainingAndPriceBetweenOrderByProductidDesc(String catid, Float pricemin, Float pricemax, Pageable pageable);

     Page<Product> findByCatidContainingOrderByPriceDesc(String catid, Pageable pageable);
     Page<Product> findByCatidContainingOrderByPriceAsc(String catid, Pageable pageable);

     Page<Product> findByCatidContainingAndPriceBetweenOrderByPriceDesc(String catid, Float pricemin, Float pricemax, Pageable pageable);
     Page<Product> findByCatidContainingAndPriceBetweenOrderByPriceAsc(String catid, Float pricemin, Float pricemax, Pageable pageable);

     List<Product> findByCatidContainingOrderByProductidAsc(String catid, Pageable pageable);

     Page<Product> findByUseridOrderByProductidDesc(Integer uid, Pageable pageable);

     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByProductidDesc(String title, String descr, String text, String attr, Pageable pageable);

     Page<Product> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrTextContainingIgnoreCaseOrAttrContainingIgnoreCaseOrderByProductidDesc(String title, String descr, String text, String attr, Pageable pageable);

     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByProductidDesc(String title, String descr, String text, String attr, Float pricemin, Float pricemax, Pageable pageable);

     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByPriceDesc(String title, String descr, String text, String attr, Float pricemin, Float pricemax, Pageable pageable);
     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingAndPriceBetweenOrderByPriceAsc(String title, String descr, String text, String attr, Float pricemin, Float pricemax, Pageable pageable);

     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByPriceDesc(String title, String descr, String text, String attr, Pageable pageable);
     Page<Product> findByTitleContainingOrDescriptionContainingOrTextContainingOrAttrContainingOrderByPriceAsc(String title, String descr, String text, String attr, Pageable pageable);




     long count();


     Page<Product> findByCatidContainingAndTitleContainingAllIgnoreCaseOrderByProductidDesc(String cat, String title , Pageable pageable);
}