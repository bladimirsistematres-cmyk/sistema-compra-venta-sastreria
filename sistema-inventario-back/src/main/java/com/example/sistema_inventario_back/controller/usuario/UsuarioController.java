package com.example.sistema_inventario_back.controller.usuario;

import com.example.sistema_inventario_back.dto.usuario.UsuarioUpdateDTO;
import com.example.sistema_inventario_back.dto.usuario.UsuarioUpdateNombreDTO;
import com.example.sistema_inventario_back.dto.usuario.UsuarioUpdatePasswordDTO;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.service.config_service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    // Controlador para listar a todos los usuarios
    @GetMapping("/listarUsuarios")
    public ResponseEntity<Iterable<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Controlador para actualizar un usuario por id
    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioUpdateDTO dto){

        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    // Controlador para actualizar el nombre de usuario
    @PutMapping("/updateNombreUsuario/{id}")
    public ResponseEntity<Usuario> cambiarNombreUsuario(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioUpdateNombreDTO dto
            ){
        return ResponseEntity.ok(
                usuarioService.actualizarNombreUsuario(id, dto.getNuevoNombreUsuario())
        );
    }

    // Controlador para cambiar el nombre de usuario.
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<Usuario> cambiarContrasenia(
            @PathVariable Integer id,
            @RequestBody @Valid UsuarioUpdatePasswordDTO dto
            ){
        return ResponseEntity.ok(
                usuarioService.actualizarPassword(id, dto.getActualPassword(), dto.getNuevoPassword())
        );
    }
}