package com.lab.patrimonio.service;

import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Posicao;
import com.lab.patrimonio.entidade.Usuario;
import com.lab.patrimonio.entidade.dto.InvestimentoSaldo;
import com.lab.patrimonio.entidade.repository.InvestimentoRepo;
import com.lab.patrimonio.entidade.repository.PosicaoRepo;
import com.lab.patrimonio.entidade.repository.UsuarioRepo;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;
    @Autowired
    UsuarioRepo repository;
    @Autowired
    InvestimentoRepo invRepo;
    @Autowired
    PosicaoRepo posRep;

    @Test
    void deveSalvarUsuario() {
        //cenário
        Usuario usr = Usuario.builder().nome("Teste1").email("t@t.com").senha("123").build();
        //teste
        Usuario salvo = service.salvar(usr);
        //verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertNotNull(salvo.getId());

        //rollback
        repository.delete(salvo);
    }

    @Test
    void deveVerificarRetornoSaldoInvestimento(){
        //cenário
        Usuario usr = Usuario.builder().nome("Teste1").email("t@t.com").senha("123").build();
        Usuario usrSalvo = repository.save(usr);

        Investimento inv = Investimento.builder().usuario(usrSalvo).nome("Poupança").build();
        Investimento invSalvo = invRepo.save(inv);

        Posicao pos = Posicao.builder().investimento(invSalvo).valor(200.0).data(new Date()).build();
        Posicao posSalvo1 = posRep.save(pos);
        pos = Posicao.builder().investimento(invSalvo).valor(100.0).data(new Date()).build();
        Posicao posSalvo2 = posRep.save(pos);

        //teste
        List<InvestimentoSaldo> retorno =
                service.obterSaldos(usrSalvo);

        //verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(retorno.size(), 1);
        Assertions.assertEquals(retorno.get(0).getValor(), 300.0);

        //rollback
        posRep.delete(posSalvo1);
        posRep.delete(posSalvo2);
        invRepo.delete(invSalvo);
        repository.delete(usrSalvo);
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemNome() {
        //cenário: tentar salvar sem nome
        Usuario usr = Usuario.builder().email("t@t.com").senha("123").build();

        Assertions.assertThrows(RegraNegocioRunTime.class,
                () -> service.salvar(usr),
                "Nome do usuário deve ser informado");

    }

}
