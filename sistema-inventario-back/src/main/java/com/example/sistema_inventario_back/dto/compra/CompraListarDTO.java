package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraListarDTO {
    private Integer idCompra;
    private Double totalCompra;
    private String nombreComercial;
    private String nombreMateriaPrima;
    private UnidadMedida unidadMedida;
    private Double cantidadUnidadMedida;
}