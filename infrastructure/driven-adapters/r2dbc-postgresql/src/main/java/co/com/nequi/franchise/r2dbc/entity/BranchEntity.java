package co.com.nequi.franchise.r2dbc.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("branch")
public class BranchEntity {

    @Id
    private Long id;

    @Column("franchise_id")
    private Long franchiseId;

    @Column("name")
    private String name;
}
