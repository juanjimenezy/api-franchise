package co.com.nequi.franchise.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la creación de una sucursal")
public class BranchRequestDTO {

    @Schema(description = "ID de la franquicia asociada", example = "1")
    private Long franchiseId;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Principal")
    private String name;
}
