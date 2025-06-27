package co.com.nequi.franchise.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("franchise")
public class FranchiseEntity {
    @Id
    private Long id;

    @Column("name")
    private String name;

}
