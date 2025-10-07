package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorFiltroDTO {
    private String nombreComercial;
    private String identificacionFiscal;
}