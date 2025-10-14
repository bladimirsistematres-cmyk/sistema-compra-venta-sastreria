package com.example.sistema_inventario_back.service.materia_prima;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaPageListarDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaRequestDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaResponseDTO;
import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.compra.MateriaPrimaRepository;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface.MateriaPrimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaServicelmpl implements MateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Servicio para crear una nueva materia prima
    @Override
    public MateriaPrimaResponseDTO createMateriaPrima(MateriaPrimaRequestDTO materiaPrimaRequestDTO, String username) {
        MateriaPrima materiaPrima = matToEntity(materiaPrimaRequestDTO, username);
        MateriaPrima materiaPrimaGuardada = materiaPrimaRepository.save(materiaPrima);

        return matToResponseDTO(materiaPrimaGuardada);
    }

    // Servicio para listar a toda la materia prima paginada (nueva version)
    @Override
    public MateriaPrimaPageListarDTO getAllMateriaPrima(Pageable pageable) {
        Page<MateriaPrimaListarDTO> page = materiaPrimaRepository.findAllMateriaPrima(pageable);

        MateriaPrimaPageListarDTO response = new MateriaPrimaPageListarDTO();
        response.setMateriaPrima(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    @Override
    public List<MateriaPrimaResponseDTO> findByProveedorIdProveedor(Integer idProveedor) {
        return materiaPrimaRepository.findByProveedor(idProveedor)
                .stream()
                .map(this::matToResponseDTO)
                .collect(Collectors.toList());
    }

    // Convierte RequestDTO a entidad
    private MateriaPrima matToEntity(MateriaPrimaRequestDTO dto, String username){
        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Usuario usuario = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNombreMateriaPrima(dto.getNombreMateriaPrima());
        materiaPrima.setMarca(dto.getMarca());
        materiaPrima.setModelo(dto.getModelo());
        materiaPrima.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0.0);
        materiaPrima.setUnidadMedida(dto.getUnidadMedida());
        materiaPrima.setDescripcion(dto.getDescripcion());
        materiaPrima.setProveedor(proveedor);
        materiaPrima.setUsuario(usuario);

        return materiaPrima;
    }

    // Convierte entidad a ResponseDTO
    private MateriaPrimaResponseDTO matToResponseDTO(MateriaPrima materiaPrima){
        MateriaPrimaResponseDTO response = new MateriaPrimaResponseDTO();

        response.setIdMateriaPrima(materiaPrima.getIdMateriaPrima());
        response.setNombreMateriaPrima(materiaPrima.getNombreMateriaPrima());
        response.setStockActual(materiaPrima.getStockActual());
        response.setDescripcion(materiaPrima.getDescripcion());
        response.setUsuario(materiaPrima.getUsuario().getNombreUsuario());
        response.setProveedor(materiaPrima.getProveedor().getNombreComercial());
        response.setIdProveedor(materiaPrima.getProveedor().getIdProveedor());

        return response;
    }
}