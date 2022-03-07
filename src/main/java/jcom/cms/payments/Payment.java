package jcom.cms.payments;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

public interface Payment {
    public String getId();

    public String getTitle();

    public String getDescription();

    public String frontEnd();

    public String admin();

    public String makePayment(HttpServletRequest request, Float totalcart, Integer orderId);

    public String store(MultipartHttpServletRequest request);
}
