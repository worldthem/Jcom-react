package jcom.cms.response;

import jcom.cms.payments.Payment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class PaymentSettingResponse {
    private Payment payment;
    private String adminForm;
    private Boolean activated;
}
