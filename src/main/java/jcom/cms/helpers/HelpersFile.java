package jcom.cms.helpers;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jcom.cms.JcomCmsApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class HelpersFile {

/*
       String path = TestCl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
       try {
           path = URLDecoder.decode(path, "UTF-8");
           path = path.replace("target/classes/","");
           path = path.replace("file:","");
          if(path.contains(".jar")){
               String[] ssplit = path.split(".jar");
               String[] spl2 = ssplit[0].split("/");
               path =  ssplit[0].replace(spl2[spl2.length-1],"");
           }


           Windows
             path abs is:D:\Java projects\icommyjava\target\classes --- D:\Java projects\icommyjava\target\classes
             path to is :/D:/Java projects/icommyjava/content/lang/ak.json

          windows run jar
            path to is :file:/C:/Users/Ricky/Desktop/jarCompile/jcms.jar!/BOOT-INF/classes!/
            path abs is:C:\Users\Ricky\Desktop\jarCompile --- C:\Users\Ricky\Desktop\jarCompile\jcms.jar
            path to is :file:/C:/Users/Ricky/content/views/themes/standart/config/config.xml


             ubuntu
             path abs is:/home/ecommerc/cms --- /home/ecommerc/cms/jcms.jar
             path to is :file:/home/ecommerc/cms/content/lang/en.json



           ApplicationHome home = new ApplicationHome(IcommyjavaApplication.class);
          System.out.println("path abs is:"+home.getDir() +" --- "+home.getSource());  // path abs is:D:\Java projects\icommyjava\target\classes --- D:\Java projects\icommyjava\target\classes
           ////home/ecommerc/cms/jcms.jar!/BOOT-INF/classes!/content/lang/


           char c = pathGet.charAt(0);
           //path = c=='/'? path.substring(1)+pathGet.substring(1):path.substring(1)+pathGet;
           path = c=='/'? path+pathGet.substring(1):path+pathGet;
            System.out.println("path to is :"+path );
            return path;

           //System.out.println("path to file jar is:"+new File(IcommyjavaApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
       }catch (Exception e){
           return pathGet;
       }

*/

    public static LinkedHashMap<String, String> getJsonFileData(String field){
        String json = HelpersFile.readFile("content/config/biling_shipping/"+field+".json");
        if(json.isEmpty())
            return null;

        try{
            Type type = new TypeToken<LinkedHashMap<String, String>>(){}.getType();
            return  (new Gson()).fromJson(json, type);
        }catch (Exception e){
            return null;
        }
    }

   public static String pathToFile(String pathGet){
       ApplicationHome home = new ApplicationHome(JcomCmsApplication.class);
       String path = home.getDir().getPath();
        path = path.replaceAll("\\\\","/");
        path = path.replace("/target/classes","");
        path = path.replace("//","");
        char c = pathGet.charAt(0);
        path = c=='/'? path+pathGet:path+"/"+pathGet;
        //System.out.println("path to is :"+path );
       return path;

   }

    public static LinkedHashMap<String, LinkedHashMap<String,String>> getAdminMenu(String Path){
        Path = Path.isEmpty() ? "content/config/adminconfig.xml" : Path;

        LinkedHashMap<String, LinkedHashMap<String,String>> list = new LinkedHashMap<>();

        try {
             // get node menus
             NodeList nList = readXml(Path, "menus");
             Node nNode = nList.item(0); // we have only one


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    // get all nodes menuitem
                    Element eElement = (Element) nNode;
                    NodeList nodeMenuitem = eElement.getElementsByTagName("menuitem");

                     // loop all nodes menuitem
                    for (int i = 0; i < nodeMenuitem.getLength(); i++) {
                      Element elementMenuitem = (Element) nodeMenuitem.item(i);
                      if(elementMenuitem !=null) {

                        // get node menu
                        NodeList nodeMenu = elementMenuitem.getElementsByTagName("menu");

                        LinkedHashMap<String, String> list2 = new LinkedHashMap<>();

                        // loop node menu
                        for (int j = 0; j < nodeMenu.getLength(); j++) {
                            Element elementMenu = (Element) nodeMenu.item(j);
                            Node ittemMenu = nodeMenu.item(j);

                            if (ittemMenu != null)
                                list2.put(elementMenu.getAttribute("url"), ittemMenu.getTextContent());
                          }

                         list.put(elementMenuitem.getAttribute("nr"),list2);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:"+e.getMessage());
        }

        return list;
    }

    public static Map<String, String> getConfigXmlData(String node1, String node2, String Path){
        Path = Path.isEmpty() ? "content/config/adminconfig.xml" : Path;

        try {
            NodeList nList = readXml(Path, node1);
            //new TreeMap
            Map<String, String> nodeittem = new LinkedHashMap<>();

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    // get the second node
                    NodeList nodelist2 = eElement.getElementsByTagName(node2);
                    // get the first node
                    Element element1 = (Element) nodelist2.item(0);

                    if(element1 !=null) {
                       // get inside node by name: ittem
                        NodeList nodelist3 = element1.getElementsByTagName("ittem");
                        // put all the ittems in Map
                        for (int j = 0; j < nodelist3.getLength(); j++) {


                            Element element2 = (Element) nodelist3.item(j);
                            Node ittem = nodelist3.item(j);
                            if (ittem != null) {
                                String field = element2.getAttribute("field") == null ? "field" + j : element2.getAttribute("field");
                                nodeittem.put(field, ittem.getTextContent());
                                //System.out.println("field:" + field + "---value:" + ittem.getTextContent() + j);
                            }
                        }
                    }

                }
            }
            return nodeittem;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:"+e.getMessage());
        }

        return null;
    }

    /**
     * will return the data of file
     * @param filePath
     * @return
     */

    public static String readFile(String filePath){
        filePath = filePath.contains("classpath:")? filePath : pathToFile(filePath);
        File file =   new File(filePath);
        return getFileContent(file);
    }


   public static String getFileContent(File file){
        if (!file.exists() ) { //&& !file.mkdirs()
            return "";
        }
        String data = "";

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                data = data + line;
            }
            br.close();

        }catch (Exception e){
            System.out.println( "Error read file: " + e);
            return "";
        }
        return data;
    }

    public static NodeList readXml(String path, String node){
        try{
            File fXmlFile = new File(pathToFile(path));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            return doc.getElementsByTagName(node);
        }catch (Exception d){
            System.out.println("Error read xml:"+d.getMessage());
            return null;
        }

    }

    public static void updateFile(String text, String filepath){
          try {

                File fileOptionsName = new File(pathToFile(filepath));
                FileOutputStream fos = new FileOutputStream(fileOptionsName);
                fos.write(text.getBytes());
                fos.close();
            } catch (Exception error) {  }
     }

  public static boolean fileExist(String path){
        try{
            File tmpDir = new File(pathToFile(path));
            return tmpDir.exists();
        }catch (Exception e){  return false;}
  }



}
