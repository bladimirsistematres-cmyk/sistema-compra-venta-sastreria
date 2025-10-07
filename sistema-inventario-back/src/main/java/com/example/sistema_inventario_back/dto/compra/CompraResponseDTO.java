package com.example.sistema_inventario_back.dto.compra;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompraResponseDTO {
    private Integer idCompra;
    private Double totalCompra;
    private LocalDateTime fechaCompra;
    private Integer IdProveedor;
    private String nombreComercial;
    private Integer idUsuario;
    private String nombreUsuario;
    private CompraDetalleResponseDTO detalles;
}