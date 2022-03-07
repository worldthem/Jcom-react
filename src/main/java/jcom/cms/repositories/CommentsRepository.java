package jcom.cms.repositories;

import jcom.cms.entity.Comments;
import jcom.cms.mappingclass.CommentsList;
import jcom.cms.mappingclass.ProductsList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
     String query="SELECT p.cpu as pcpu, p.title as ptitle, " +
                         " c.commentid as commentid, c.userid as userid, c.productid as productid, c.author as author, "+
                         " c.email as email, c.ip as ip, c.comment as comment, c.status as status, " +
                         " c.stars as stars, c.updated_at as upd, c.created_at as created, " +
                         " u.firstName as firstName, u.lastName as lastName " +
                         " FROM Comments c " +
                         " LEFT JOIN Users u ON c.userid = u.userid " +
                         " LEFT JOIN Product p ON c.productid = p.productid ";

    Page<Comments> findAll(Pageable pageable);
    Page<Comments> findByStatusOrderByCommentid(Integer status, Pageable pageable);
    Page<Comments> findByUseridOrderByCommentid(Integer userid, Pageable pageable);
    Page<Comments> findByUseridAndStatusOrderByCommentid(Integer userid, Integer status, Pageable pageable);
    Page<Comments> findByCommentContainingOrAuthorContainingOrEmailContainingAllIgnoreCaseOrderByCommentid(String search, String search1, String search2, Pageable pageable);
    Comments findByCommentid(Integer id);
    Page<Comments> findAllByOrderByCommentidDesc(Pageable pageable);


    @Query(value = query+ " ORDER BY c.commentid DESC" )
    Page<CommentsList>getAll(Pageable pageable);

    @Query(value = query+ " WHERE c.status = ?1 ORDER BY c.commentid DESC" )
    Page<CommentsList>getByStatus(Integer status, Pageable pageable);

    @Query(value = query+ " WHERE c.status = ?1 AND u.userid = ?2 ORDER BY c.commentid DESC" )
    Page<CommentsList>getByStatusAndUser(Integer status, Integer userid, Pageable pageable);

    @Query(value = query+ " WHERE u.userid = ?1 ORDER BY c.commentid DESC" )
    Page<CommentsList>getByUser(Integer userid, Pageable pageable);

    @Query(value = query+ " WHERE c.comment LIKE %?1% OR c.author LIKE %?2% OR c.email LIKE %?3%  ORDER BY c.commentid DESC" )
    Page<CommentsList>find(String search, String search1, String search2, Pageable pageable);
}
