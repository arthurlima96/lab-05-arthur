package dcomp.es2.laboratorio05.repositorio;


import dcomp.es2.laboratorio05.modelo.Cidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    @BeforeEach
    public void start() {
        cidade = new Cidade("São Luis", "MA", new BigDecimal(40.0));
    }

    @Test
    public void saveComNomeNuloDeveLancarException() throws Exception {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> {  cidade.setNome(null);
                    cidadeRepository.save(cidade);
                },
                "Deveria lançar um ConstraintViolationException");

        Assertions.assertTrue(exception.getMessage().contains("O nome deve ser preenchido"));
    }

    @Test
    public void saveComUFNuloDeveLancarException() throws Exception {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> {  cidade.setUf(null);
                    cidadeRepository.save(cidade);
                },
                "Deveria lançar um ConstraintViolationException");

        Assertions.assertTrue(exception.getMessage().contains("A UF deve ser preenchida"));
    }

    @Test
    public void saveComTaxaNulaDeveLancarException() throws Exception {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> {  cidade.setTaxa(new BigDecimal(0.0));
                    cidadeRepository.save(cidade);
                },
                "Deveria lançar um ConstraintViolationException");

        Assertions.assertTrue(exception.getMessage().contains("A taxa deve ser preenchida"));
    }
}