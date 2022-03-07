package jcom.cms.response;

import jcom.cms.entity.Country;
import jcom.cms.entity.Shipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingMethodsResponse {
   private Page<Shipping> shipping;
   private Iterable<Country> countries;

}
