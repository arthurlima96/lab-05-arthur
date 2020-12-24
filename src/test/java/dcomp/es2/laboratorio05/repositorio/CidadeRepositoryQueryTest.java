package dcomp.es2.laboratorio05.repositorio;


import dcomp.es2.laboratorio05.modelo.Cidade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CidadeRepositoryQueryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    @BeforeEach
    public void start() {
        cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
        cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
        cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));
    }

    @AfterEach
    public void after() {
        cidadeRepository.deleteAll();
    }

    @Test
    public void deveBuscarContatoPeloNome() {
        Cidade cidade= cidadeRepository.findByNome("Barrerinhas");
        Assertions.assertTrue(cidade.getNome().equals("Barrerinhas"));
    }
}