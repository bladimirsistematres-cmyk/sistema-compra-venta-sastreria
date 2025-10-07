package com.example.sistema_inventario_back.service.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Ciudad;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.proveedor.Representante;
import com.example.sistema_inventario_back.exception.proveedor.CiudadNoEncontradoException;
import com.example.sistema_inventario_back.exception.proveedor.ProveedorNoEncontradoException;
import com.example.sistema_inventario_back.repository.proveedor.CiudadRepository;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProveedorServicelpml implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public ProveedorResponseDTO createProveedor(ProveedorRequestDTO proveedorRequestDTO){
        Proveedor proveedor = matToEntity(proveedorRequestDTO);
        Proveedor saved = proveedorRepository.save(proveedor);

        return matToResponseDTO(saved);
    }

    // Servicio para listar a todos los Proveedores (nueva version)
    @Override
    public ProveedorPageListarDTO getAllProveedoresNew(Pageable pageable){
        Page<ProveedorListarDTO> page = proveedorRepository.findAllResumen(pageable);

        ProveedorPageListarDTO response = new ProveedorPageListarDTO();
        response.setProveedores(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    //Servicio para listar a todos los Proveedores
    @Override
    public ProveedorPageResponseDTO getAllProveedores(Pageable pageable){
        Page<Proveedor> page = proveedorRepository.findAll(pageable);

        List<ProveedorResponseDTO> dtoList = page.getContent()
                .stream()
                .map(this::matToResponseDTO)
                .toList();

        ProveedorPageResponseDTO responseDTO = new ProveedorPageResponseDTO();
        responseDTO.setProveedores(dtoList);
        responseDTO.setPaginaActual(page.getNumber());
        responseDTO.setTotalPaginas(page.getTotalPages());
        responseDTO.setTotalElementos(page.getTotalElements());
        responseDTO.setTamanioPagina(page.getSize());

        return responseDTO;
    }

    //Servicio para actualizar el proveedor
    @Override
    public ProveedorResponseDTO updateProveedor(Integer id, ProveedorRequestDTO proveedorRequestDTO){
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNoEncontradoException(id));

        Ciudad ciudad = ciudadRepository.findById(proveedorRequestDTO.getIdCiudad())
                .orElseThrow(() -> new CiudadNoEncontradoException(id));

        proveedor.setNombreComercial(proveedorRequestDTO.getNombreComercial().toUpperCase());
        proveedor.setIdentificacionFiscal(proveedorRequestDTO.getIdentificacionFiscal().toUpperCase());
        proveedor.setCorreoElectronico(proveedorRequestDTO.getCorreoElectronico());
        proveedor.setDireccion(proveedorRequestDTO.getDireccion().toUpperCase());
        proveedor.setEnlacePagina(proveedorRequestDTO.getEnlacePagina());
        proveedor.setTipoProveedor(proveedorRequestDTO.getTipoProveedor());
        proveedor.setCiudad(ciudad);
        proveedor.setFechaActualizacion(LocalDateTime.now());

        Proveedor proveedorActualizado = proveedorRepository.save(proveedor);

        return matToResponseDTO(proveedorActualizado);
    }

    //Servicio para buscar un proveedor por id
    @Override
    public ProveedorResponseDTO getProveedorById(Integer id){

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " +id));

        return matToResponseDTO(proveedor);
    }

    @Override
    public List<RepresentanteResponseDTO> getRepresentantesByProveedorId(Integer idProveedor){
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " +idProveedor));

        return proveedor.getRepresentantes()
                .stream()
                .map(this::matRepresentanteToDTO)
                .toList();
    }

    // Servicio para la filtracion
    @Override
    public ProveedorPageListarDTO buscarProveedorConFiltros(ProveedorFiltroDTO filtro, Pageable pageable){

        String nombreComercialLike = filtro.getNombreComercial() != null ? "%" + filtro.getNombreComercial() + "%" : null;
        String identificacionFiscalLike = filtro.getIdentificacionFiscal() != null ? "%" + filtro.getIdentificacionFiscal() + "%" : null;

        Page<ProveedorListarDTO> page = proveedorRepository.findAllProveedoresConFiltros(
               nombreComercialLike, identificacionFiscalLike, pageable
        );

        ProveedorPageListarDTO response = new ProveedorPageListarDTO();
        response.setProveedores(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    @Override
    public ProveedorResponseDTO cambiarNombreProveedor(ProveedorCambiarNombreDTO nuevoNombre) {
        Proveedor proveedor = proveedorRepository.findById(nuevoNombre.getIdProveedor())
                .orElseThrow(() -> new ProveedorNoEncontradoException(nuevoNombre.getIdProveedor()));

        proveedor.setNombreComercial(nuevoNombre.getNuevoNombre().toUpperCase());
        proveedor.setFechaActualizacion(LocalDateTime.now());

        Proveedor proveedorActualizado = proveedorRepository.save(proveedor);

        return matToResponseDTO(proveedorActualizado);
    }

    private ProveedorListarDTO mapToListarDTO(Proveedor proveedor){
        ProveedorListarDTO dto = new ProveedorListarDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreComercial(proveedor.getNombreComercial());
        dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setTieneRepresentantes(proveedor.getRepresentantes() != null && !proveedor.getRepresentantes().isEmpty());

        return dto;
    }

    private Proveedor matToEntity(ProveedorRequestDTO dto){
        Ciudad ciudad = ciudadRepository.findById(dto.getIdCiudad())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " +dto.getIdCiudad()));

        Proveedor proveedor = new Proveedor();

        proveedor.setNombreComercial(dto.getNombreComercial().toUpperCase());
        proveedor.setIdentificacionFiscal(dto.getIdentificacionFiscal().toUpperCase());
        proveedor.setCorreoElectronico(dto.getCorreoElectronico());
        proveedor.setDireccion(dto.getDireccion().toUpperCase().toUpperCase());
        proveedor.setEnlacePagina(dto.getEnlacePagina());
        proveedor.setTipoProveedor(dto.getTipoProveedor());
        proveedor.setCiudad(ciudad);

        return proveedor;
    }

    private ProveedorResponseDTO matToResponseDTO(Proveedor proveedor){
        ProveedorResponseDTO dto = new ProveedorResponseDTO();

        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreComercial(proveedor.getNombreComercial());
        dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
        dto.setCorreoElectronico(proveedor.getCorreoElectronico());
        dto.setDireccion(proveedor.getDireccion());
        dto.setEnlacePagina(proveedor.getEnlacePagina());
        dto.setTipoProveedor(proveedor.getTipoProveedor());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setFechaActualizacion(proveedor.getFechaActualizacion());
        dto.setTieneRepresentantes(!proveedor.getRepresentantes().isEmpty());

        // Cargar datos de la ciudad
        if (proveedor.getCiudad() != null){
            dto.setIdCiudad(proveedor.getCiudad().getIdCiudad());
            dto.setNombreCiudad(proveedor.getCiudad().getNombreCiudad());

            if (proveedor.getCiudad().getPais() != null){
                dto.setNombrePais(proveedor.getCiudad().getPais().getNombrePais());
            }
        }

        List<RepresentanteResponseDTO> representantes = proveedor.getRepresentantes()
                .stream()
                .map(this::matRepresentanteToDTO)
                .toList();

        dto.setRepresentantes(representantes);

        return dto;
    }

    private RepresentanteResponseDTO matRepresentanteToDTO(Representante representante){
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
        dto.setIdProveedor(representante.getProveedor().getIdProveedor());
        dto.setNombreComercial(representante.getProveedor().getNombreComercial());

        return dto;
    }
}