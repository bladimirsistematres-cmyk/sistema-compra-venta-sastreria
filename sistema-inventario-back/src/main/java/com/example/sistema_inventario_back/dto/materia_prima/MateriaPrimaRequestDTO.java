package com.example.sistema_inventario_back.dto.materia_prima;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.Data;

@Data
public class MateriaPrimaRequestDTO {
    private String nombreMateriaPrima;
    private String marca;
    private String modelo;
    private Double stockActual;
    private UnidadMedida unidadMedida;
    private String descripcion;
    private Integer idProveedor;
}