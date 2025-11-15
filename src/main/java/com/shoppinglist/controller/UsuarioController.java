package com.shoppinglist.controller;

import com.shoppinglist.dto.UsuarioDTO;
import com.shoppinglist.model.Usuario;
import com.shoppinglist.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todos os usuários ativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        logger.info("Recebida requisição para listar usaurios");
        try {
            List<Usuario> usuarios = usuarioRepository.findByAtivo('S');
            List<UsuarioDTO> usuariosDTO = usuarios.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            logger.info("Encontrado {} usuarios", usuariosDTO.stream());
            return ResponseEntity.ok(usuariosDTO);
        }catch (Exception e){
            logger.error("Erro ao listar usuários");
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha()); // Em produção, criptografar a senha!

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(toDTO(usuarioSalvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return (ResponseEntity<UsuarioDTO>) usuarioRepository.findById(id)
                .map(usuario -> {
                    // Verificar se o email já existe para outro usuário
                    if (!usuario.getEmail().equals(usuarioDTO.getEmail()) &&
                            usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                        return ResponseEntity.<UsuarioDTO>badRequest().build();
                    }

                    usuario.setNome(usuarioDTO.getNome());
                    usuario.setEmail(usuarioDTO.getEmail());
                    if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
                        usuario.setSenha(usuarioDTO.getSenha()); // Em produção, criptografar a senha!
                    }

                    Usuario usuarioAtualizado = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(toDTO(usuarioAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário (desativação lógica)")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setAtivo('N');
                    usuarioRepository.save(usuario);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Método auxiliar para converter Entity para DTO
    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setAtivo(usuario.getAtivo());
        // Não incluir a senha no DTO de retorno por segurança
        return dto;
    }
}