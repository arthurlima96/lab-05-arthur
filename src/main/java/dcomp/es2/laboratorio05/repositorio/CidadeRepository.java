package dcomp.es2.laboratorio05.repositorio;

import dcomp.es2.laboratorio05.modelo.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Cidade findByNome(String nome);
}
