package com.example.sistema_inventario_back.service.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentantePageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.proveedor.Representante;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.repository.proveedor.RepresentanteRepository;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepresentanteServicelpml implements RepresentanteService{

    @Autowired
    private RepresentanteRepository representanteRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    //Servicio para crear un nuevo representante
    @Override
    public RepresentanteResponseDTO createRepresentante(RepresentanteRequestDTO representanteRequestDTO){
        Representante representante = mapToEntity(representanteRequestDTO);
        Representante saved = representanteRepository.save(representante);

        return mapToResponseDTO(saved);
    }

    //Servicio para listar a todos los representantes
    @Override
    public RepresentantePageResponseDTO getAllRepresentantes(Pageable pageable){
        Page<Representante> page = representanteRepository.findAll(pageable);

        List<RepresentanteResponseDTO> dtoList = page.getContent()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();

        RepresentantePageResponseDTO response = new RepresentantePageResponseDTO();
        response.setRepresentantes(dtoList);
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    //Servicio para buscar un representante por id
    @Override
    public RepresentanteResponseDTO getRepresentanteById(Integer id){
        Representante representante = representanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Representante no encontrado con id: " +id));

        return mapToResponseDTO(representante);
    }

    //Servicio para actualizar un representante
    @Override
    public RepresentanteResponseDTO updateRepresentante(Integer id, RepresentanteRequestDTO representanteRequestDTO){
        Representante representante = representanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Representante no encontrado con id: " +id));

        representante.setNombre(representanteRequestDTO.getNombre());
        representante.setCargo(representanteRequestDTO.getCargo());
        representante.setCedulaIdentidad(representanteRequestDTO.getCedulaIdentidad());
        representante.setGeneroRepresentante(representanteRequestDTO.getGeneroRepresentante());
        representante.setTelefonoFijo(representanteRequestDTO.getTelefonoFijo());
        representante.setTelefonoCelular(representanteRequestDTO.getTelefonoCelular());
        representante.setCorreoElectronico(representanteRequestDTO.getCorreoElectronico());
        representante.setDireccion(representanteRequestDTO.getDireccion());
        representante.setObservaciones(representanteRequestDTO.getObservaciones());
        representante.setFechaActualizacion(LocalDateTime.now());

        Proveedor proveedor = proveedorRepository.findById(representanteRequestDTO.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " +representanteRequestDTO.getIdProveedor()));

        representante.setProveedor(proveedor);
        Representante representanteActualizado = representanteRepository.save(representante);
        return mapToResponseDTO(representanteActualizado);
    }

    //Servicio para buscar por un campo
    @Override
    public RepresentantePageResponseDTO buscarRepresentantesConFiltros(
            String nombre,
            String cargo,
            String cedulaIdentidad,
            String correoElectronico,
            Pageable pageable
    ){
        Specification<Representante> specification = Specification.where(null);

        if (nombre != null && !nombre.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%")));
        }

        if (cargo != null && !cargo.isBlank()) {
            specification = specification.or((root, query, cb) -> cb.like(cb.lower(root.get("cargo")), "%" + cargo.toLowerCase() + "%"));
        }

        if (cedulaIdentidad != null && !cedulaIdentidad.isBlank()) {
            specification = specification.or((root, query, cb) -> cb.like(cb.lower(root.get("cedulaIdentidad")), "%" + cedulaIdentidad.toLowerCase() + "%"));
        }

        if (correoElectronico != null && !correoElectronico.isBlank()) {
            specification = specification.or((root, query, cb) -> cb.like(cb.lower(root.get("correoElectronico")), "%" + correoElectronico.toLowerCase() + "%"));
        }

        Page<Representante> page = representanteRepository.findAll(specification, pageable);

        List<RepresentanteResponseDTO> dtoList = page.getContent().stream()
                .map(this::mapToResponseDTO)
                .toList();

        RepresentantePageResponseDTO response = new RepresentantePageResponseDTO();
        response.setRepresentantes(dtoList);
        response.setTotalElementos(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    // Servicio para listar a todoso los represenantes segun el id del proveedor
    @Override
    public List<RepresentanteListarDTO> getAllRepresentantesByIdProveedor(Integer idProveedor) {
        List<Representante> representantes = representanteRepository.findByProveedorIdProveedor(idProveedor);

        return representantes.stream().map(representante -> {
            RepresentanteListarDTO representantesDTO = new RepresentanteListarDTO();

            representantesDTO.setNombre(representante.getNombre());
            representantesDTO.setCargo(representante.getCargo());
            representantesDTO.setCedulaIdentidad(representante.getCedulaIdentidad());
            representantesDTO.setTelefonoCelular(representante.getTelefonoCelular());
            representantesDTO.setFechaCreacion(representante.getFechaCreacion());

            return representantesDTO;
        }).collect(Collectors.toList());
    }

    private Representante mapToEntity(RepresentanteRequestDTO dto){
        Representante representante = new Representante();

        representante.setNombre(dto.getNombre());
        representante.setCargo(dto.getCargo());
        representante.setCedulaIdentidad(dto.getCedulaIdentidad());
        representante.setGeneroRepresentante(dto.getGeneroRepresentante());
        representante.setTelefonoFijo(dto.getTelefonoFijo());
        representante.setTelefonoCelular(dto.getTelefonoCelular());
        representante.setCorreoElectronico(dto.getCorreoElectronico());
        representante.setDireccion(dto.getDireccion());
        representante.setObservaciones(dto.getObservaciones());

        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " +dto.getIdProveedor()));

        representante.setProveedor(proveedor);

        return representante;
    }

    private RepresentanteResponseDTO mapToResponseDTO(Representante representante){
        RepresentanteResponseDTO dto = new RepresentanteResponseDTO();

        dto.setIdRepresentante(representante.getIdRepresentante());
        dto.setNombre(representante.getNombre());
        dto.setCargo(representante.getCargo());
        dto.setCedulaIdentidad(representante.getCedulaIdentidad());
        dto.setGeneroRepresentante(representante.getGeneroRepresentante());
        dto.setTelefonoFijo(representante.getTelefonoFijo());
        dto.setTelefonoCelular(representante.getTelefonoCelular());
        dto.setCorreoElectronico(representante.getCorreoElectronico());
        dto.setDireccion(representante.getDireccion());
        dto.setObservaciones(representante.getObservaciones());
        dto.setFechaCreacion(representante.getFechaCreacion());
        dto.setFechaActualizacion(representante.getFechaActualizacion());

        if (representante.getProveedor() != null){
            dto.setIdProveedor(representante.getProveedor().getIdProveedor());
            dto.setNombreComercial(representante.getProveedor().getNombreComercial());
        }

        return dto;
    }
}