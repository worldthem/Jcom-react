package jcom.cms.controllers.cp;

import com.google.gson.Gson;
import jcom.cms.pojo.Attr;
import jcom.cms.services.AttributesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cp/")
public class AttributesController {

    @Autowired
    private AttributesServices service;

    @GetMapping(value = "/get-attributes/{type}")
    public LinkedHashMap<String, Attr> getList(@PathVariable String type) {
        return service.getList(type);
    }

    @PutMapping(path = "/update-attributes", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody LinkedHashMap<String, Attr> data) {
        String json = "";
        try{
            json = new Gson().toJson(data);
        }catch (Exception e){}

        return service.update(json, "_attributes");
    }

    @GetMapping(value = "/get-attributes-suggestion/{key}")
    public Map<String, Map<String,String>> getListSuggestion(@PathVariable String key) {
         return service.getListSuggestion(key);
    }

    @PutMapping(path = "/update-attributes-suggestion/{key}", consumes = "application/json", produces = "application/json")
    public String store(@RequestBody LinkedHashMap<String, Map<String,String>> data, @PathVariable String key) {

        String json = "";
         try{
             json = new Gson().toJson(data);
         }catch (Exception e){}

        return service.update(json, key);
    }


    /*
    @RequestMapping(value = "/admin/attributes-delete/{fieldKey}", method = RequestMethod.GET)

    public String delete(@PathVariable String fieldKey, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Attributes attributes = Helpers.getAttrSettings();
        if (!fieldKey.isEmpty() && attributes.getFields().containsKey(fieldKey)) {
            attributes.getFields().remove(fieldKey);
            attributes.save();
        }

        return "redirect:" + request.getHeader("Referer");
    }


    private String getSugestionPage(String key) {


        return "";
    }

    @PostMapping(path = "/attributes-suggestion")
    public Map<String, Lang> showSugestion(@RequestParam(value = "key") String key) {
        Attributes attributes = Helpers.getAttrSettings();
        return attributes.getFields().containsKey(key) ? attributes.getFields().get(key).getSugestion() :
                new LinkedHashMap<>();
    }


    @PostMapping(path = "/admin/attributes-sugestion/update", consumes = "application/x-www-form-urlencoded")
    public String storeSugestion(HttpServletRequest request, Model model) {
        String fieldKey = request.getParameter("fieldKey");
        String id = request.getParameter("id");
        id = id.contains("new") ? Helpers.randomString(4) : id;
        // get all attributes
        Attributes attributes = Helpers.getAttrSettings();


        if (attributes.getFields().containsKey(fieldKey)) {
            AttributesVar attributesVar = attributes.getFields().get(fieldKey);
            Map<String, Lang> sugestion = attributesVar.getSugestion() == null ? new LinkedHashMap<>() : attributesVar.getSugestion();


            List<String> listk = new ArrayList<>();
            for (int i = 0; i < 400; i++) {
                listk.add(Helpers.randomString(5));
            }

            for (int i = 0; i < Helpers.listLang().size(); i++) {
                String lang = Helpers.listLang().get(i);

                String[] split = request.getParameter("title[" + lang + "]").split("\\r?\\n");

                for (int j = 0; j < split.length; j++) {
                    String keyIs = listk.get(j);
                    Lang title = sugestion.containsKey(keyIs) ? sugestion.get(keyIs) : new Lang();
                    title.set(lang, split[j]);
                    sugestion.put(keyIs, title);
                }

            }

            attributesVar.setSugestion(sugestion);
            attributes.getFields().put(fieldKey, attributesVar);
            attributes.save();
        }
        return "";
    }


    @PostMapping(path = "/admin/attributes-sugestion/update-single")
    public @ResponseBody
    String updateSingle(HttpServletRequest request, Model model) {
        String fieldKey = request.getParameter("fieldKeySingle");
        String key = request.getParameter("id");

        Attributes attributes = Helpers.getAttrSettings();

        if (attributes.getFields().containsKey(fieldKey)) {
            try {
                AttributesVar attributesVar = attributes.getFields().get(fieldKey);
                Map<String, Lang> sugestion = attributesVar.getSugestion();
                sugestion.remove(key);
                Lang title = new Lang();
                for (int i = 0; i < Helpers.listLang().size(); i++) {
                    String lang = Helpers.listLang().get(i);
                    title.set(lang, request.getParameter("title[" + lang + "]"));
                }
                sugestion.put(key, title);
                attributesVar.setSugestion(sugestion);
                attributes.getFields().put(fieldKey, attributesVar);
                attributes.save();
                //updateAttrFile(existingAttr);
            } catch (Exception e) {
                System.out.println("tiltle error:" + e);
            }

        }
        return "ok";
    }


    @PostMapping(path = "/admin/attributes-sugestion/bulk")
    public String bulkOptions(HttpServletRequest request, Model model) {
        String fieldKey = request.getParameter("fieldKey");
        String action = request.getParameter("action");

        Attributes attributes = Helpers.getAttrSettings();

        if (attributes.getFields().containsKey(fieldKey) && action.contains("del")) {
            try {
                AttributesVar attributesVar = attributes.getFields().get(fieldKey);
                Map<String, Lang> sugestion = attributesVar.getSugestion();
                Map<String, String[]> mapParam = request.getParameterMap();

                if (mapParam.containsKey("rowid")) {
                    for (int i = 0; i < mapParam.get("rowid").length; i++) {
                        sugestion.remove(mapParam.get("rowid")[i]);
                    }

                    attributesVar.setSugestion(sugestion);
                    attributes.getFields().put(fieldKey, attributesVar);
                    attributes.save();
                }
            } catch (Exception e) {
            }

        }

        return "";
    }

    @PostMapping(path = "/admin/attributes-sugestion/remove")

    public String remouveSugestion(HttpServletRequest request, @RequestParam(value = "fieldKey") String fieldKey, @RequestParam(value = "key") String key, Model model) {

        Attributes attributes = Helpers.getAttrSettings();

        if (attributes.getFields().containsKey(fieldKey)) {
            try {
                AttributesVar attributesVar = attributes.getFields().get(fieldKey);
                Map<String, Lang> sugestion = attributesVar.getSugestion();
                sugestion.remove(key);
                attributesVar.setSugestion(sugestion);
                attributes.getFields().put(fieldKey, attributesVar);
                attributes.save();
            } catch (Exception e) {
            }

        }
        return "";
    }*/
}
