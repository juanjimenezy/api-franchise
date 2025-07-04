package co.com.nequi.franchise.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Nombre de la sucursal", example = "Sucursal Principal")
public class FranchiseRequestDTO {
    @Schema(description = "Nombre de la franquicia", example = "Franquicia Ejemplo")
    private String name;
}
