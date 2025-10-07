package com.example.sistema_inventario_back.dto.compra;
import lombok.Data;

@Data
public class CompraRequestDTO {
    private Integer idProveedor;
    private Double totalCompra;
    private CompraDetalleRequestDTO detalle;
}