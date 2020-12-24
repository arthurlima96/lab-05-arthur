package dcomp.es2.laboratorio05.repositorio;


import dcomp.es2.laboratorio05.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByNome(String nome);
    Cliente findByTelefone(String telefone);
}
