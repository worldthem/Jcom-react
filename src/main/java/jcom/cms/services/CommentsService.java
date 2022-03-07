package jcom.cms.services;

import jcom.cms.entity.Comments;
import jcom.cms.mappingclass.CommentsList;
import jcom.cms.repositories.CommentsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CommentsService {

    @Autowired
    private CommentsRepository repository;

    public Page<CommentsList> getList(Integer status, Integer userid, String search, PageRequest pageable) {

        if(!search.isEmpty()){
             return repository.find(search,search,search, pageable);
        }

        if(status > 0 && userid == 0){
              return repository.getByStatus(status, pageable);
        }else if(status > 0 && userid > 0){
              return repository.getByStatusAndUser(status, userid, pageable);
        }else if(status == 0 && userid > 0){
              return repository.getByUser(userid, pageable);
        }

        return repository.getAll(pageable);
   }


    public Page<Comments> findAll(Pageable pageable){
        Page<Comments> contacts = repository.findAllByOrderByCommentidDesc(pageable);
        return contacts;
    }

    public Page<Comments> search(String search, Pageable pageable){
        return repository.findByCommentContainingOrAuthorContainingOrEmailContainingAllIgnoreCaseOrderByCommentid(search,search,search,pageable);
    }

    public Page<Comments> getBy(String by, String find, Pageable pageable){
        Page<Comments> comments = null;
        if(by.contains("status")){
            comments =  repository.findByStatusOrderByCommentid(Integer.parseInt(find), pageable);
        }else if(by.contains("userid")){
            comments =  repository.findByUseridOrderByCommentid(Integer.parseInt(find), pageable);
        }

        return comments;
    }

    public Comments save(Comments comments){
        return repository.save(comments);
    }

    public Comments getOne(Integer id){
        return repository.findByCommentid(id);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

    public long count(){
        return repository.count();
    }


}
