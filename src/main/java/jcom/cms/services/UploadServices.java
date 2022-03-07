package jcom.cms.services;


import jcom.cms.entity.Media;
import jcom.cms.helpers.HelpersFile;
import jcom.cms.repositories.MediaRepositorey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UploadServices {
    @Autowired
    private MediaRepositorey mediaRepo;

    @Autowired
    private StorageService storageService;

    @Value("${public.path}")
    private String publicPath;

    /**
     * upload file(s) to media file
     * @param request
     * @param files
     * @return
     */
    public List<Media> uploadToGallery(HttpServletRequest request, MultipartFile[] files){
         String pageid = request.getParameter("pageid");
         String productid = request.getParameter("productid");
         String title = request.getParameter("title");

          for(MultipartFile file: files){
            String typeFile = request.getParameter("typefile");
            String path = typeFile ==null || typeFile.contains("gallery") ? "" : publicPath+"/files";

            String fileName = typeFile == null || typeFile.contains("gallery") ?
                               storageService.uploadSingleImg(file,path) : storageService.simpleUpload(file,path);

            if(!fileName.isEmpty()) {
                Media gallery = new Media();
                gallery.setDirectory(fileName);
                gallery.setTitle(title != null ? title : "");

                gallery.setProductid(productid != null ? Integer.parseInt(productid) : 0);
                gallery.setPageid(pageid != null ? Integer.parseInt(pageid) : 0);
                gallery.setTypefile(typeFile != null ? typeFile : "");

                mediaRepo.save(gallery);
              }
          }

        return productid !=null ? mediaRepo.findByProductid(Integer.parseInt(productid)) :
                    (pageid !=null ? mediaRepo.findByPageid(Integer.parseInt(pageid)): null) ;

      }


    /**
     * will remove the file it self and one line from table media
     * @param request
     * @return
     */
    public String removeMedia(HttpServletRequest request) {
        String type = "";
        Integer id = 0;
        try{
           Media media = mediaRepo.getById(Integer.parseInt(request.getParameter("id")));
            type = media.getPageid() !=null && media.getPageid() > 0 ? "page" :"product";
            id = media.getPageid() !=null && media.getPageid() > 0  ? media.getPageid() :
                                                                 media.getProductid();
            mediaRepo.deleteById(media.getId());

            String directory = HelpersFile.pathToFile( publicPath+(media.getTypefile().contains("gallery") ? "imgproduct" : "files"));

            File myObj = new File(directory +"/"+ media.getDirectory());
            if (myObj.delete()) { }

            return "ok";

        }catch (Exception e){
           return e.getMessage();
        }
    }

    /**
     * upload single image and return the name of the image
     * @param file
     * @return
     */
    public String simpleUpload(MultipartFile file){
        return storageService.uploadSingleImg(file,publicPath+"images");
    }
}
