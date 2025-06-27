package co.com.nequi.franchise.model.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;
    private Long branchId;
    private String name;
    private Boolean state;
    private Integer stock;

}
