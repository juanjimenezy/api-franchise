package co.com.nequi.franchise.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
@AllArgsConstructor
public class FranchiseEntity {
    @Id
    private Long id;

    @Column("name")
    private String name;
}
