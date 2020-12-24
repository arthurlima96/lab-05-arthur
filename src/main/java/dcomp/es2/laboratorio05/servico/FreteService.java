package dcomp.es2.laboratorio05.servico;


import dcomp.es2.laboratorio05.modelo.Frete;
import dcomp.es2.laboratorio05.repositorio.CidadeRepository;
import dcomp.es2.laboratorio05.repositorio.ClienteRepository;
import dcomp.es2.laboratorio05.repositorio.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Frete cadastro(Frete frete) throws Exception {
        try {
            if(cidadeRepository.findByNome(frete.getCidade().getNome()) == null) throw new Exception("Cidade não encontrada");
            if(clienteRepository.findByNome(frete.getCliente().getNome()) == null) throw new Exception("Cliente não encontrada");

            return freteRepository.save(frete);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Frete> buscarFretes(){
        return freteRepository.findAll();
    }

    public Optional<Frete> buscarFrete(Long id){
        return freteRepository.findById(id);
    }

    public void remover(Long id){
        freteRepository.deleteById(id);
    }

    public BigDecimal valorFrete(Frete frete){
        BigDecimal valorTaxaEntrega = frete.getCidade().getTaxa();
        BigDecimal valorTotalFrete = frete.getPeso().multiply(frete.getValor());
        return valorTaxaEntrega.add(valorTotalFrete);
    }

    public Frete freteComMaiorCusto (){
        return freteRepository.findTopByOrderByValorDesc();
    }

    public String cidadeCommaiorValor(){
        List<Object[]> nomeCidade = freteRepository.countTotalCitiesByFrete();
        return (String) nomeCidade.get(0)[0];
    }
}
