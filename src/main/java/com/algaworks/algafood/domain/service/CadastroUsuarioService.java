package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final CadastroGrupoService cadastroGrupoService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        this.usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = this.usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s",
                    usuario.getEmail()));
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return this.usuarioRepository.save(usuario);
    }

    public Usuario buscarOuFalhar(Long idUsuario) {
        return this.usuarioRepository.findById(idUsuario).orElseThrow(() ->
                new UsuarioNaoEncontradoException(idUsuario));
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {
            this.usuarioRepository.deleteById(usuarioId);
            this.usuarioRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, SenhaInput senhaInput) {
        Usuario usuarioCadastrado = buscarOuFalhar(usuarioId);
        if (!passwordEncoder.matches(senhaInput.getSenhaAtual(), usuarioCadastrado.getSenha()))
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");

        usuarioCadastrado.setSenha(passwordEncoder.encode(senhaInput.getSenhaNova()));
    }

    @Transactional
    public void adicionarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void removerGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.desassociarGrupo(grupo);
    }
}
