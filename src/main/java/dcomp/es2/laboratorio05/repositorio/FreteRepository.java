package dcomp.es2.laboratorio05.repositorio;

import dcomp.es2.laboratorio05.modelo.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreteRepository extends JpaRepository<Frete, Long> {

    Frete findTopByOrderByValorDesc();

    @Query("SELECT f.cidade.nome, count (f.cidade.nome) FROM Frete as f GROUP BY f.cidade order by count (f.cidade.nome) desc")
    List<Object[]> countTotalCitiesByFrete();
}
