package dcomp.es2.laboratorio05.controle;


import dcomp.es2.laboratorio05.modelo.Frete;
import dcomp.es2.laboratorio05.servico.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/rota")
public class FreteController {

	@Autowired
	private FreteService freteService;

	@GetMapping("/")
	public ResponseEntity<List<Frete>> fretes(){
		List<Frete> frestes = freteService.buscarFretes();
		return ResponseEntity.ok(frestes);
	}

	@GetMapping("/frete/{id}")
	public ResponseEntity<Frete> frete(@PathVariable Long id){
		return freteService.buscarFrete(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Frete> inserir(@RequestBody @Valid Frete frete) throws Exception {
		frete = freteService.cadastro(frete);
		return new ResponseEntity<>(frete, HttpStatus.CREATED);
	}

	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Frete> remover(@PathVariable Long id) {
		freteService.remover(id);
		return ResponseEntity.noContent().build();
	}

}