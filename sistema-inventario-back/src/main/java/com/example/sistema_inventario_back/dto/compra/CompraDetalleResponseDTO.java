package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.Data;

@Data
public class CompraDetalleResponseDTO {
    private Integer idCompraDetalle;
    private UnidadCompra unidadCompra;
    private Double cantidadCompra;
    private UnidadMedida unidadMedida;
    private Double cantidadMedida;
    private String materiaPrima;
}