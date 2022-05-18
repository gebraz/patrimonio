package com.lab.patrimonio.service;


import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Posicao;
import com.lab.patrimonio.entidade.Usuario;
import com.lab.patrimonio.entidade.repository.InvestimentoRepo;
import com.lab.patrimonio.entidade.repository.PosicaoRepo;
import com.lab.patrimonio.entidade.repository.UsuarioRepo;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired
    InvestimentoRepo repository;

    @Autowired
    UsuarioRepo usuarioRep;

    @Autowired
    PosicaoRepo posicaoRep;

    public Investimento salvar(Investimento inv) {
        verificaInvestimento(inv);
        return repository.save(inv);
    }

    public Investimento atualizar(Investimento inv) {
        verificarId(inv);
        return salvar(inv);
    }

    public void remover(Investimento inv) {
        verificarId(inv);
        verificarPosicao(inv);
        repository.delete(inv);
    }

    public void remover(Long idInvestimento) {
        Optional<Investimento> inv = repository.findById(idInvestimento);
        remover(inv.get());
    }


    public List<Investimento> buscar (Investimento filtro) {
        Example<Investimento> example =
                Example.of(filtro, ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(StringMatcher.CONTAINING)
                );

        return repository.findAll(example);
    }


    public Double obterValorTotal(Investimento inv) {
        verificarId(inv);
        return repository.obterSaldoInvestimento(inv);
    }


    private void verificarPosicao(Investimento inv) {
        List<Posicao> res = posicaoRep.findByInvestimento(inv);
        if (!res.isEmpty())
            throw new RegraNegocioRunTime("Investimento informado possui posições");
    }

    private void verificarId(Investimento inv) {
        if ((inv == null) || (inv.getId() == null))
            throw new RegraNegocioRunTime("Investimento sem id");
    }

    private void verificaInvestimento(Investimento inv) {
        if(inv == null)
            throw new RegraNegocioRunTime("Um investimento válido deve ser informado");

        if ((inv.getNome() == null)
                || (inv.getNome().equals("")))
            throw new RegraNegocioRunTime("Nome do investimento precisa ser preenchido");

        if(inv.getUsuario() == null)
            throw new RegraNegocioRunTime("Um investimento deve estar atrelado a um usuário válido");

        Optional<Usuario> temp = usuarioRep.findById(inv.getUsuario().getId());
        if (!temp.isPresent())
            throw new RegraNegocioRunTime("Usuário informado não consta na base");

    }

}