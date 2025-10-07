package com.example.sistema_inventario_back.repository.compra;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaResponseDTO;
import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Integer>, JpaSpecificationExecutor<MateriaPrima> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO(
                 m.idMateriaPrima,
                 m.nombreMateriaPrima,
                 m.stockActual
            )
            FROM MateriaPrima m
            """)
    Page<MateriaPrimaListarDTO> findAllMateriaPrima(Pageable pageable);

    @Query("SELECT m FROM MateriaPrima m WHERE m.proveedor.idProveedor = :idProveedor")
    List<MateriaPrima> findByProveedor(@Param("idProveedor") Integer idProveedor);
}