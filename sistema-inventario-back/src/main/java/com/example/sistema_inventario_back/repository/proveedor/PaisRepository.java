package com.example.sistema_inventario_back.repository.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.pais.PaisListarDTO;
import com.example.sistema_inventario_back.entity.proveedor.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Integer> {

    @Query("SELECT new com.example.sistema_inventario_back.dto.proveedor.pais.PaisListarDTO(p.idPais, p.nombrePais) FROM Pais p")
    List<PaisListarDTO> findAllListar();
}