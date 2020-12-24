package dcomp.es2.laboratorio05.controle;

import com.fasterxml.jackson.core.JsonProcessingException;
import dcomp.es2.laboratorio05.modelo.Cidade;
import dcomp.es2.laboratorio05.modelo.Cliente;
import dcomp.es2.laboratorio05.modelo.Frete;
import dcomp.es2.laboratorio05.repositorio.CidadeRepository;
import dcomp.es2.laboratorio05.repositorio.ClienteRepository;
import dcomp.es2.laboratorio05.repositorio.FreteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreteControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private FreteRepository freteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	private Frete frete;

	@BeforeEach
	public void before() {
		frete = new Frete(new Cliente(),new Cidade(),"Casa no Vinhais",new BigDecimal(10.0),new BigDecimal(10.0));
	}

	@AfterEach
	public void end() {
		freteRepository.deleteAll();
		clienteRepository.deleteAll();
		cidadeRepository.deleteAll();
	}

	@Test
	public void deveMostrarTodosFretes() {
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

		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/rota/",HttpMethod.GET, null, String.class);

		System.out.println("######## " + resposta.getBody() );
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}


	@Test
	public void deveMostrarTodosFretesUsandoString() {
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

		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/rota/",HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json"));
		//String result = "[{\"id\":1,\"cliente\":{\"id\":1,\"nome\":\"Arthur Lima\",\"endereco\":\"Rua Quarenta e Cinco, 31\",\"telefone\":\"(98)98306-4375\"},\"cidade\":{\"id\":1,\"nome\":\"São Luis\",\"uf\":\"MA\",\"taxa\":40.00},\"descricao\":\"Casa no Vinhais\",\"peso\":10.00,\"valor\":10.00},{\"id\":2,\"cliente\":{\"id\":1,\"nome\":\"Arthur Lima\",\"endereco\":\"Rua Quarenta e Cinco, 31\",\"telefone\":\"(98)98306-4375\"},\"cidade\":{\"id\":1,\"nome\":\"São Luis\",\"uf\":\"MA\",\"taxa\":40.00},\"descricao\":\"Casa na Cohama\",\"peso\":40.00,\"valor\":30.00},{\"id\":3,\"cliente\":{\"id\":1,\"nome\":\"Arthur Lima\",\"endereco\":\"Rua Quarenta e Cinco, 31\",\"telefone\":\"(98)98306-4375\"},\"cidade\":{\"id\":3,\"nome\":\"Barrerinhas\",\"uf\":\"MA\",\"taxa\":60.00},\"descricao\":\"Casa no Cohafuma\",\"peso\":100.00,\"valor\":20.00}]";
		//assertTrue(resposta.getBody().contains(result));
	}


	@Test
	public void deveMostrarUmFrete() throws JsonProcessingException {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);

		frete = freteRepository.findById(frete.getId()).get();

		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/rota/frete/{id}",HttpMethod.GET,null, Frete.class, frete.getId());

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				MediaType.parseMediaType("application/json"));
		assertEquals(frete, resposta.getBody());
	}

	@Test
	public void deveMostrarTodosFretesUsandoList() {
		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);
		frete = freteRepository.findById(frete.getId()).get();

		ParameterizedTypeReference<List<Frete>> tipoRetorno =	new ParameterizedTypeReference<List<Frete>>() {};

		ResponseEntity<List<Frete>> resposta =
				testRestTemplate.exchange("/rota/",HttpMethod.GET,null, tipoRetorno);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json") );
		assertEquals(1, resposta.getBody().size());
		assertEquals(frete, resposta.getBody().get(0));
	}



	@Test
	public void deveRetornarFreteNaoEncontrado() {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);

		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/rota/frete/{id}",HttpMethod.GET,null, Frete.class,100 );

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	// --------------------- GET --------------------
	@Test
	public void deveMostrarUmFreteComGetForEntity() {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);
		frete = freteRepository.findById(frete.getId()).get();

		ResponseEntity<Frete> resposta =
				testRestTemplate.getForEntity("/rota/frete/{id}", Frete.class,frete.getId());

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json"));

		assertEquals(frete, resposta.getBody());
	}

	@Test
	public void deveMostrarUmFreteComGetForObject() {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);
		frete = freteRepository.findById(frete.getId()).get();

		Frete resposta =
				testRestTemplate.getForObject("/rota/frete/{id}", Frete.class,frete.getId());
		assertEquals(frete, resposta);
	}

	@Test
	public void deveRetornarFreteNaoEncontradoComGetForEntity() {
		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);

		ResponseEntity<Frete> resposta =
				testRestTemplate.getForEntity("/rota/frete/{id}", Frete.class,100);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	public void naoDeveEncontrarFreteInexistente() {
		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		Frete frete = new Frete(cliente,cidadeRepository.findByNome("São Luis"),"Casa no Vinhais",new BigDecimal(10.00),new BigDecimal(10.00));
		freteRepository.save(frete);

		Frete resposta = testRestTemplate.getForObject("/rota/frete/{id}", Frete.class,100);
		assertNull(resposta);
	}


	@Test
	public void deveSalvarFrete() {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		frete.setCliente(cliente);
		frete.setCidade(cidadeRepository.findByNome("Barrerinhas"));

		HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);

		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/rota/cadastrar",HttpMethod.POST,httpEntity, Frete.class);

		assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

		Frete resultado = resposta.getBody();

		assertNotNull(resultado.getId());
		assertEquals(frete.getCliente(), resultado.getCliente());
		assertEquals(frete.getCidade(), resultado.getCidade());
	}

	@Test
	public void deveExcluirFrete() {

		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		frete.setCliente(cliente);
		frete.setCidade(cidadeRepository.findByNome("Barrerinhas"));
		freteRepository.save(frete);

		ResponseEntity<Frete> resposta =
				testRestTemplate.exchange("/rota/remover/{id}",HttpMethod.DELETE,null
						, Frete.class,frete.getId());

		assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	public void deveExcluirContatoComMetodoDeleteFrete() {
		cidadeRepository.save(new Cidade("São Luis", "MA", new BigDecimal(40.0)));
		cidadeRepository.save(new Cidade("Bacabal", "MA", new BigDecimal(70.0)));
		cidadeRepository.save(new Cidade("Barrerinhas", "MA", new BigDecimal(60.0)));

		Cliente cliente = new Cliente("Arthur Lima", "Rua Quarenta e Cinco, 31","(98)98306-4375");
		clienteRepository.save(cliente);

		frete.setCliente(cliente);
		frete.setCidade(cidadeRepository.findByNome("Barrerinhas"));
		freteRepository.save(frete);

		testRestTemplate.delete("/rota/remover/"+frete.getId());

		final Optional<Frete> resultado = freteRepository.findById(frete.getId());
		assertEquals(Optional.empty(), resultado);
	}
}