package co.com.nequi.franchise.model.branch;
import co.com.nequi.franchise.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private Long id;
    private String name;
    private List<Product> products;
}
