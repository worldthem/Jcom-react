package jcom.cms.response;

import jcom.cms.payments.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymnetsResponse {
    private List<String> activated;
    private List<Payment> rows;

}
