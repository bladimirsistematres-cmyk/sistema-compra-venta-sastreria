package com.example.sistema_inventario_back.service.compra;

import com.example.sistema_inventario_back.dto.compra.*;
import com.example.sistema_inventario_back.entity.compra.Compra;
import com.example.sistema_inventario_back.entity.compra.CompraDetalle;
import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.compra.CompraRepository;
import com.example.sistema_inventario_back.repository.compra.MateriaPrimaRepository;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import com.example.sistema_inventario_back.service.compra.compra_interface.CompraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraServicelmpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    // Servicio para listar todas las compras
    @Override
    public CompraPageListarDTO getAllCompras(Pageable pageable) {
        Page<CompraListarDTO> page = compraRepository.findAllCompra(pageable);

        CompraPageListarDTO response = new CompraPageListarDTO();
        response.setListaCompra(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    // Servicio para actualizar la compra
    @Override
    public CompraResponseDTO updateCompra(CompraUpdateDTO dto) {
        Compra compra = compraRepository.findById(dto.getIdCompra())
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        // Editar total compra
        if (dto.getTotalCompra() != null){
            compra.setTotalCompra(dto.getTotalCompra());
        }

        // Editar proveedor
        if(dto.getIdProveedor() != null){
            Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            compra.setProveedor(proveedor);
        }

        // Especificar el motivo de la edición
        if (dto.getNotaEdicion() != null && !dto.getNotaEdicion().isEmpty()){
            compra.setNotaEdicion(dto.getNotaEdicion());
        }

        CompraDetalle detalle = compra.getCompraDetalle();
        CompraDetalleUpdateDTO detalleUpdate = dto.getDetalle();

        if(detalleUpdate.getCantidadUnidadMedida() != null){
            detalle.setCantidadUnidadMedida(detalleUpdate.getCantidadUnidadMedida());
        }

        detalle.setUnidadCompra(detalleUpdate.getUnidadCompra());
        detalle.setCantidadUnidadCompra(detalle.getCantidadUnidadCompra());
        detalle.setUnidadMedida(detalleUpdate.getUnidadMedida());

        compra.setCompraDetalle(detalle);

        return matToResponseDTO(compraRepository.save(compra));
    }

    // Servicio para listar las compras realizadas de una materia prima
    @Override
    public List<CompraResponseDTO> getAllComprasByMateriaPrima(Integer idMateriaPrima) {
        List<Compra> compras = compraRepository.findByMateriaPrima(idMateriaPrima);

        return compras
                .stream()
                .map(this::matToResponseDTO)
                .toList();
    }

    // Servicio para listar compras realizadas de un proveedor
    @Override
    public List<CompraResponseDTO> getAllComprasByProveedor(Integer idProveedor) {
        List<Compra> compras = compraRepository.findByProveedor_IdProveedor(idProveedor);

        return compras
                .stream()
                .map(this::matToResponseDTO)
                .toList();
    }

    // Servicio para crear una nueva compra
    @Override
    @Transactional
    public CompraResponseDTO createCompra(CompraRequestDTO compraRequestDTO, String username) {
        Compra compra = matToEntity(compraRequestDTO, username);
        Compra savedCompra = compraRepository.save(compra);

        return matToResponseDTO(savedCompra);
    }

    private Compra matToEntity(CompraRequestDTO dto, String username){
        Usuario usuario = userRepository.findByCarnetIdentidad(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setTotalCompra(dto.getTotalCompra());
        compra.setFechaCompra(LocalDateTime.now());
        compra.setUsuario(usuario);

        CompraDetalleRequestDTO detalleDTO = dto.getDetalle();

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(detalleDTO.getMateriaPrima())
                .orElseThrow(() -> new RuntimeException("Materia Prima no encontrada"));

        CompraDetalle detalle = new CompraDetalle();
        detalle.setMateriaPrima(materiaPrima);
        detalle.setUnidadCompra(detalleDTO.getUnidadCompra());
        detalle.setCantidadUnidadCompra(detalleDTO.getCantidadCompra());
        detalle.setUnidadMedida(detalleDTO.getUnidadMedida());
        detalle.setCantidadUnidadMedida(detalleDTO.getCantidadMedida());
        detalle.setCompra(compra);

        compra.setCompraDetalle(detalle);

        materiaPrima.setStockActual(materiaPrima.getStockActual() + detalle.getCantidadUnidadMedida());
        materiaPrimaRepository.save(materiaPrima);

        return compra;
    }

    private CompraResponseDTO matToResponseDTO(Compra compra){

        CompraResponseDTO responseDTO = new CompraResponseDTO();

        // Cabecera de la compra
        responseDTO.setIdCompra(compra.getIdCompra());
        responseDTO.setTotalCompra(compra.getTotalCompra());
        responseDTO.setFechaCompra(compra.getFechaCompra());

        // Proveedor
        responseDTO.setIdProveedor(compra.getProveedor().getIdProveedor());
        responseDTO.setNombreComercial(compra.getProveedor().getNombreComercial());

        // Usuario
        responseDTO.setIdUsuario(compra.getUsuario().getId_usuario());
        responseDTO.setNombreUsuario(compra.getUsuario().getNombre_usuario());

        CompraDetalle detalle = compra.getCompraDetalle();
        CompraDetalleResponseDTO detalleResponse = new CompraDetalleResponseDTO();
        detalleResponse.setIdCompraDetalle(detalle.getIdCompraDetalle());
        detalleResponse.setUnidadCompra(detalle.getUnidadCompra());
        detalleResponse.setCantidadCompra(detalle.getCantidadUnidadCompra());
        detalleResponse.setUnidadMedida(detalle.getUnidadMedida());
        detalleResponse.setCantidadMedida(detalle.getCantidadUnidadMedida());
        detalleResponse.setMateriaPrima(detalle.getMateriaPrima().getNombreMateriaPrima());

        responseDTO.setDetalles(detalleResponse);

        return responseDTO;
    }
}