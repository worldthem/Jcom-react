package jcom.cms.payments.banktransfer;

import jcom.cms.helpers.Language;
import jcom.cms.pojo.Lang;

public class BankTransferModule {
    private String noimage ="";
    private String image;
    private Lang instruction;
    private Lang title;
    private  Lang description;
    private String lang;

    public String getTitle() {
        return title== null?  "Direct bank transfer": Language.getLangData(title);
    }

    public void setTitle(String title) {
        this.title =  Language.saveSimple(title, this.title, lang );
    }

    public String getDescription() {
        return description==null?  "Make your payment directly into our bank account. Please use your Order ID as the payment reference. Your order will not be shipped until the funds have cleared in our account." :
                                   Language.getLangData(description);
    }

    public void setDescription(String description) {
        this.description= Language.saveSimple(description, this.description, lang );
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstruction() {
        return Language.getLangData(instruction);
    }

    public void setInstruction(String instruction) {
        this.instruction= Language.saveSimple(instruction, this.instruction, lang);
    }

    public Boolean getNoimage() {
        return noimage == null  || !noimage.contains("yes")  ? false : true;
    }

    public void setNoimage(String noimage) {
        this.noimage = noimage;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}