package jcom.cms.controllers.cp;


import jcom.cms.entity.Media;
import jcom.cms.services.UploadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * In this controller we do all the upload image and remove
 */
@RestController
@RequestMapping("/api/v1/cp/")
public class UploadController {
     @Autowired
     private UploadServices uploadServices;

    /**
     * upload images or files
     * @param request
     * @param files
     * @return
     */
     @PostMapping("/upload-files")
     public List<Media> updateBulk (HttpServletRequest request,
                                   @RequestParam("files") MultipartFile[] files) {

      return uploadServices.uploadToGallery(request,files);
     }


    /**
     * upload file to images folder and return the image path
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/simple-upload")
    public String simpleUpload (HttpServletRequest request,
                                   @RequestParam("file") MultipartFile file) {

        return uploadServices.simpleUpload(file);
    }


    /**
     * remove image or file from Table Media
     * @param request
     * @return
     */
    @GetMapping("/remove-media")
    public String removeMedia (HttpServletRequest request ) {

        return uploadServices.removeMedia(request );
    }
}
