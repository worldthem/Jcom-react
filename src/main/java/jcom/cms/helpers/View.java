package jcom.cms.helpers;

import org.springframework.stereotype.Component;

@Component
public class View {
    /**
     * Translate simple words
     * @param text
     * @return
     */
    public String l(String text) {
        return  Helpers.translate(text);
    }
}
