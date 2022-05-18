package com.lab.patrimonio.service;

import com.lab.patrimonio.entidade.Usuario;
import com.lab.patrimonio.entidade.dto.InvestimentoSaldo;
import com.lab.patrimonio.entidade.repository.UsuarioRepo;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService  {

    @Autowired
    UsuarioRepo repository;

    public boolean efetuarLogin(String email, String senha) {
        Optional<Usuario> usr = repository.findByEmail(email);

        if (!usr.isPresent())
            throw new RegraNegocioRunTime("Erro de autenticação. Email informado não encontrado");
        if (!usr.get().getSenha().equals(senha))
            throw new RegraNegocioRunTime("Erro de autenticação. Senha inválida");

        return true;
    }

    public Usuario salvar(Usuario usuario) {
        verificarUsuario(usuario);
        return repository.save(usuario);
    }

    public List<InvestimentoSaldo> obterSaldos(Usuario usuario) {
        verficarId(usuario);
        return repository.obterSaldosInvestimentos(usuario);
    }

    private void verficarId(Usuario usuario) {
        if ((usuario == null) || (usuario.getId() == null))
            throw new RegraNegocioRunTime("Usuario inválido");
    }

    private void verificarUsuario(Usuario usuario) {
        if (usuario == null)
            throw new RegraNegocioRunTime("Um usuário válido deve ser informado");
        if ((usuario.getNome() == null) || (usuario.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do usuário deve ser informado");
        if ((usuario.getEmail() == null) || (usuario.getEmail().equals("")))
            throw new RegraNegocioRunTime("Email deve ser informado");
        boolean teste = repository.existsByEmail(usuario.getEmail());
        if (teste)
            throw new RegraNegocioRunTime("Email informado já existe na base");
        if ((usuario.getSenha() == null) || (usuario.getSenha().equals("")))
            throw new RegraNegocioRunTime("Usuário deve possui senha");
    }
}
