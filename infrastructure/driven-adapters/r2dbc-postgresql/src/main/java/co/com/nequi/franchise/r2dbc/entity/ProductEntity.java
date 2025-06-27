package co.com.nequi.franchise.r2dbc.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("product")
public class ProductEntity {
    @Id
    private Long id;

    @Column("branch_id")
    private Long branchId;

    @Column("name")
    private String name;

    @Column("state")
    private Boolean state;

    @Column("stock")
    private Integer stock;
}
