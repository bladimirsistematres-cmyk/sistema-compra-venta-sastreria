package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre comercial es obligatorio")
    private String nombreComercial;

    @NotBlank(message = "La identificacion fiscal es obligatorio")
    private String identificacionFiscal;

    //Opcional
    private String correoElectronico;

    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;

    //Opcional
    private String enlacePagina;

    @NotNull(message = "Tipo de Proveedor es obligatorio")
    private TipoProveedor tipoProveedor;

    @NotNull(message = "La ciudad es obligatoria")
    private Integer idCiudad;
}