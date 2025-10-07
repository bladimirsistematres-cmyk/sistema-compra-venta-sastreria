package com.example.sistema_inventario_back.dto.proveedor.ciudad;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CiudadListarDTO {
    private Integer idCiudad;
    private String nombreCiudad;
}