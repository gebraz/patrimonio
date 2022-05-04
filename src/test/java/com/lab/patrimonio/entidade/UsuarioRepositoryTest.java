package com.lab.patrimonio.entidade;

import com.lab.patrimonio.entidade.repository.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepo repo;

    @Test
    public void deveSalvarUsuario() {
        //cenário
        Usuario novo = Usuario.builder().nome("geraldo")
                                        .email("a@a.com")
                                        .senha("123").build();
        //ação
        Usuario retorno = repo.save(novo);

        //verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());
        Assertions.assertEquals(novo.getEmail(), retorno.getEmail());
        Assertions.assertEquals(novo.getSenha(), retorno.getSenha());
        Assertions.assertEquals(novo.getNome(), retorno.getNome());

        //rollback
        repo.delete(retorno);
    }
}
