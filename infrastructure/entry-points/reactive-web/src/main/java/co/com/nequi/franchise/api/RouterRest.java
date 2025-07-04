package co.com.nequi.franchise.api;

import co.com.nequi.franchise.api.dto.request.BranchRequestDTO;
import co.com.nequi.franchise.api.dto.request.FranchiseRequestDTO;
import co.com.nequi.franchise.api.dto.response.BranchResponseDTO;
import co.com.nequi.franchise.api.dto.response.FranchiseResponseDTO;
import co.com.nequi.franchise.api.handler.BranchHandler;
import co.com.nequi.franchise.api.handler.FranchiseHandler;
import co.com.nequi.franchise.api.handler.ProductHandler;
import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.model.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Value("${api.endpoint.franchise}")
    private String endpointApiFranchise;

    @Value("${api.endpoint.branch}")
    private String endpointApiBranch;

    @Value("${api.endpoint.product}")
    private String endpointApiProduct;

    private static final String PATH_VARIABLE_ID = "/{id}";

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/franchise/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "getFranchiseById",
                    operation = @Operation(
                            operationId = "getFranchiseById",
                            summary = "Obtener franquicia por ID",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la franquicia")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Franquicia encontrada", content = @Content(schema = @Schema(implementation = FranchiseResponseDTO.class))),
                                    @ApiResponse(responseCode = "404", description = "No encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/franchise",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Crear una nueva franquicia",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Datos de la franquicia",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = FranchiseRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Franquicia creada", content = @Content(schema = @Schema(implementation = Franchise.class))),
                                    @ApiResponse(responseCode = "400", description = "Error al crear la franquicia")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/franchise/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchiseName",
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = "Actualizar el nombre de una franquicia",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la franquicia")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Nuevo nombre de la franquicia",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = FranchiseRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Franquicia actualizada", content = @Content(schema = @Schema(implementation = FranchiseResponseDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Franquicia no existe o error en la solicitud")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/branch/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = BranchHandler.class,
                    beanMethod = "getBranchById",
                    operation = @Operation(
                            operationId = "getBranchById",
                            summary = "Obtener sucursal por ID",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la sucursal")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Sucursal encontrada", content = @Content(schema = @Schema(implementation = BranchResponseDTO.class))),
                                    @ApiResponse(responseCode = "404", description = "No encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/branch",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = BranchHandler.class,
                    beanMethod = "createBranch",
                    operation = @Operation(
                            operationId = "createBranch",
                            summary = "Crear una nueva sucursal",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Datos de la sucursal",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Branch.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Sucursal creada", content = @Content(schema = @Schema(implementation = BranchResponseDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Error al crear la sucursal")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/branch/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.PUT,
                    beanClass = BranchHandler.class,
                    beanMethod = "updateBranchName",
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = "Actualizar el nombre de una sucursal",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la sucursal")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Nuevo nombre de la sucursal",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = BranchRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Sucursal actualizada", content = @Content(schema = @Schema(implementation = BranchResponseDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Sucursal no existe o error en la solicitud")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = ProductHandler.class,
                    beanMethod = "getProductById",
                    operation = @Operation(
                            operationId = "getProductById",
                            summary = "Obtener producto por ID",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = Product.class))),
                                    @ApiResponse(responseCode = "404", description = "No encontrado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class,
                    beanMethod = "createProduct",
                    operation = @Operation(
                            operationId = "createProduct",
                            summary = "Crear un nuevo producto",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Datos del producto",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Product.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Producto creado", content = @Content(schema = @Schema(implementation = Product.class))),
                                    @ApiResponse(responseCode = "400", description = "Error al crear el producto")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/stock/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class,
                    beanMethod = "changeProductAmount",
                    operation = @Operation(
                            operationId = "changeProductAmount",
                            summary = "Cambiar cantidad de stock de un producto",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Nueva cantidad de stock",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Product.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Stock actualizado", content = @Content(schema = @Schema(implementation = Product.class))),
                                    @ApiResponse(responseCode = "400", description = "Error al actualizar el stock")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.DELETE,
                    beanClass = ProductHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Eliminar producto por ID",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Producto eliminado"),
                                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.PUT,
                    beanClass = ProductHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Actualizar el nombre de un producto",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del producto")
                            },
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Nuevo nombre del producto",
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Product.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Producto actualizado", content = @Content(schema = @Schema(implementation = Product.class))),
                                    @ApiResponse(responseCode = "400", description = "Producto no existe o error en la solicitud")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/maxStock/products",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = ProductHandler.class,
                    beanMethod = "getProductsWithMaxStockByBranch",
                    operation = @Operation(
                            operationId = "getProductsWithMaxStockByBranch",
                            summary = "Obtener productos con máximo stock por sucursal",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de productos", content = @Content(schema = @Schema(implementation = Product.class)))
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/product/franchise/maxStock/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = ProductHandler.class,
                    beanMethod = "getProductsByFranchiseIdAndMaxStock",
                    operation = @Operation(
                            operationId = "getProductsByFranchiseIdAndMaxStock",
                            summary = "Obtener productos con máximo stock por franquicia",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID de la franquicia")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de productos", content = @Content(schema = @Schema(implementation = Product.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler franchiseHandler, BranchHandler branchHandler, ProductHandler productHandler) {
        return route(GET(endpointApiFranchise.concat(PATH_VARIABLE_ID)), franchiseHandler::getFranchiseById)
                .andRoute(POST(endpointApiFranchise), franchiseHandler::createFranchise)
                .andRoute(PUT(endpointApiFranchise.concat(PATH_VARIABLE_ID)), franchiseHandler::updateFranchiseName)

                .andRoute(GET(endpointApiBranch.concat(PATH_VARIABLE_ID)), branchHandler::getBranchById)
                .andRoute(POST(endpointApiBranch), branchHandler::createBranch)
                .andRoute(PUT(endpointApiBranch.concat(PATH_VARIABLE_ID)), branchHandler::updateBranchName)

                .andRoute(GET(endpointApiProduct.concat(PATH_VARIABLE_ID)), productHandler::getProductById)
                .andRoute(POST(endpointApiProduct), productHandler::createProduct)
                .andRoute(POST(endpointApiProduct.concat(PATH_VARIABLE_ID)), productHandler::changeProductAmount)
                .andRoute(DELETE(endpointApiProduct.concat(PATH_VARIABLE_ID)), productHandler::deleteProduct)
                .andRoute(PUT(endpointApiProduct.concat(PATH_VARIABLE_ID)), productHandler::updateProductName)
                .andRoute(GET(endpointApiProduct.concat("/maxStock/products")), productHandler::getProductsWithMaxStockByBranch)
                .andRoute(GET(endpointApiProduct.concat("/franchise/maxStock/{id}")), productHandler::getProductsByFranchiseIdAndMaxStock);
    }
}
