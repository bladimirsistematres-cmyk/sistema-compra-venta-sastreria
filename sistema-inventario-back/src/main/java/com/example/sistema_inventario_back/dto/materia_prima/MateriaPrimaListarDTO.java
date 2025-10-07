package com.example.sistema_inventario_back.dto.materia_prima;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaPrimaListarDTO {
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private Double stockActual;
}