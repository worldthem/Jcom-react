package jcom.cms.mappingclass;

import java.time.LocalDateTime;

public interface CommentsList {

    String getPcpu();
    String getPtitle();
    Integer getCommentid();
    String getAuthor();
    Integer getUserid();
    Integer getProductid();
    String getEmail();
    String getIp();
    String getComment();
    Integer getStatus();
    Integer getStars();
    LocalDateTime getUpd();
    LocalDateTime getCreated();
    String getFirstName();
    String getLastName();
}
