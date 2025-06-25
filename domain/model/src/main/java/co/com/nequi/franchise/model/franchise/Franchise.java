package co.com.nequi.franchise.model.franchise;
import co.com.nequi.franchise.model.branch.Branch;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Franchise {

    private Long id;
    private String name;
    private List<Branch> branches;

}
