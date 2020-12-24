package dcomp.es2.laboratorio05.service;


import dcomp.es2.laboratorio05.modelo.Cidade;
import dcomp.es2.laboratorio05.modelo.Cliente;
import dcomp.es2.laboratorio05.modelo.Frete;
import dcomp.es2.laboratorio05.repositorio.CidadeRepository;
import dcomp.es2.laboratorio05.repositorio.ClienteRepository;
import dcomp.es2.laboratorio05.repositorio.FreteRepository;
import dcomp.es2.laboratorio05.servico.FreteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class FreteServiceTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private FreteService freteService;

    private Frete frete;

    @BeforeEach
    public void before() {

        frete = new Frete(new Cliente(),new Cidade(),"Casa no Vinhais",new BigDecimal(10.0),new BigDecimal(10.0));
    }

    @Test
    public void cadastrarComClienteNuloDeveLancarException(){
        Cidade cidade = new Cidade("São Luis", "MA", new BigDecimal(40.0));
        cidadeRepository.save(cidade);

        Exception exception = assertThrows(Exception.class,
                () -> {
                    frete.setCidade(cidade);
                    freteService.cadastro(frete);
                },
                "Cliente não encontrada");

        assertTrue(exception.getMessage().contains("Cliente não encontrada"));
    }

    @Test
    public void cadastrarComCidadeNuloDeveLancarException(){

        Exception exception = assertThrows(Exception.class,
                () -> {
                    freteService.cadastro(frete);
                },
                "Cidade não encontrada");

        assertTrue(exception.getMessage().contains("Cidade não encontrada"));
    }

    @Test
    public void cadastrarFreteComSucesso() throws Exception {
        Cidade cidade = new Cidade("São Luis", "MA", new BigDecimal(40.0));
        cidadeRepository.save(cidade);

        Cliente cliente = new Cliente("Arthu Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
        clienteRepository.save(cliente);

        frete.setCidade(cidade);
        frete.setCliente(cliente);
        freteService.cadastro(frete);

        List<Frete> fretes = freteRepository.findAll();
        Assertions.assertEquals(1, fretes.size());
    }

    @Test
    public void calcularValorFreteComSucesso() throws Exception {
        Cidade cidade = new Cidade("São Luis", "MA", new BigDecimal(40.0));
        cidadeRepository.save(cidade);

        Cliente cliente = new Cliente("Arthu Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
        clienteRepository.save(cliente);

        List<Frete> fretes =
            Arrays.asList(
                new Frete(cliente,cidade,"Casa no Vinhais",new BigDecimal(10.0),new BigDecimal(10.0)),
                new Frete(cliente,cidade,"Casa na Cohama",new BigDecimal(40.0),new BigDecimal(30.0)),
                new Frete(cliente,cidade,"Casa no Cohafuma",new BigDecimal(100.0),new BigDecimal(20.0))
            );

        freteRepository.saveAll(fretes);
        Assertions.assertEquals(new BigDecimal(30.0), freteService.freteComMaiorCusto().getValor());
    }

    @Test
    public void recuperarCidadeMaiorDestinataraComSucesso() throws Exception {
        cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
        cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
        cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

        Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
        clienteRepository.save(cliente);

        List<Frete> fretes =
                Arrays.asList(
                        new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.0),new BigDecimal(10.0)),
                        new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa na Cohama",new BigDecimal(40.0),new BigDecimal(30.0)),
                        new Frete(cliente,cidadeRepository.findByNome("Barrerinhas"),"Casa no Cohafuma",new BigDecimal(100.0),new BigDecimal(20.0))
                );

        freteRepository.saveAll(fretes);
        Assertions.assertEquals("São Luis",freteService.cidadeCommaiorValor());
    }





}