package co.com.nequi.franchise.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de respuesta para la franquicia")
public class FranchiseResponseDTO {

    @Schema(description = "Identificador único de la franquicia", example = "1")
    private Long id;

    @Schema(description = "Nombre de la franquicia", example = "Franquicia Nequi")
    private String name;
}
