package co.com.nequi.franchise.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de respuesta para una sucursal")
public class BranchResponseDTO {

    @Schema(description = "Identificador único de la sucursal", example = "1")
    private Long id;


    @Schema(description = "Identificador único de la franquicia", example = "1")
    private Long franchiseId;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Principal")
    private String name;
}
