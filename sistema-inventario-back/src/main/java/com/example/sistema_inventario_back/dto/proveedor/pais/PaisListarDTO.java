package com.example.sistema_inventario_back.dto.proveedor.pais;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaisListarDTO {
    private Integer idPais;
    private String nombrePais;
}