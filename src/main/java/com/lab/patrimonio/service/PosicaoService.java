package com.lab.patrimonio.service;

import java.util.List;
import java.util.Optional;

import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Posicao;
import com.lab.patrimonio.entidade.repository.InvestimentoRepo;
import com.lab.patrimonio.entidade.repository.PosicaoRepo;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class PosicaoService {

    @Autowired
    PosicaoRepo repository;

    @Autowired
    InvestimentoRepo investimentoRep;

    public Posicao salvar(Posicao pos) {
        verificaPosicao(pos);
        return repository.save(pos);
    }

    public Posicao atualizar(Posicao pos) {
        verficarId(pos);
        return salvar(pos);
    }

    public void remover(Posicao pos) {
        verficarId(pos);
        repository.delete(pos);
    }

    public List<Posicao> buscar (Posicao filtro) {
        Example<Posicao> example = Example.of(filtro, ExampleMatcher.matchingAny());

        return repository.findAll(example);
    }

    private void verficarId(Posicao pos) {
        if ((pos == null) || (pos.getId() == null))
            throw new RegraNegocioRunTime("Posicao sem id");
    }

    private void verificaPosicao(Posicao pos) {
        if(pos == null)
            throw new RegraNegocioRunTime("Uma posição válida deve ser informado");

        if (pos.getValor() == null)
            throw new RegraNegocioRunTime("Um valor deve ser informado para posição");

        if(pos.getInvestimento() == null)
            throw new RegraNegocioRunTime("Um Posicao deve estar atrelado a um investimento válido");

        Optional<Investimento> temp = investimentoRep.findById(pos.getInvestimento().getId());
        if (!temp.isPresent())
            throw new RegraNegocioRunTime("Investimento informado não consta na base");

    }

}
