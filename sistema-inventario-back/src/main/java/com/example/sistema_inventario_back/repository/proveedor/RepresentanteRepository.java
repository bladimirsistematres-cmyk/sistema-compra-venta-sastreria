package com.example.sistema_inventario_back.repository.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.Representante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RepresentanteRepository extends JpaRepository<Representante, Integer>, JpaSpecificationExecutor<Representante> {

    Page<Representante> findByNombreContainingIgnoreCaseAndCargoContainingIgnoreCaseAndCedulaIdentidadContainingIgnoreCaseAndCorreoElectronicoContainingIgnoreCase(
            String nombre,
            String cargo,
            String cedulaIdentidad,
            String correoElectronico,
            Pageable pageable);

    List<Representante> findByProveedorIdProveedor(Integer idProveedor);

}