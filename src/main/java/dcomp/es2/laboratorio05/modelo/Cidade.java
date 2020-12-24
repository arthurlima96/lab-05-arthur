package dcomp.es2.laboratorio05.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="O nome deve ser preenchido")
    private String nome;

    @NotEmpty(message="A UF deve ser preenchida")
    private String uf;

    @DecimalMin(value = "0.0", inclusive = false, message="A taxa deve ser preenchida")
    private BigDecimal taxa;

    public Cidade() {}

    public Cidade(String nome, String uf, BigDecimal taxa) {
        this.nome = nome;
        this.uf = uf;
        this.taxa = taxa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(id, cidade.id) && Objects.equals(nome, cidade.nome) && Objects.equals(uf, cidade.uf) && Objects.equals(taxa, cidade.taxa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, uf, taxa);
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                ", taxa=" + taxa +
                '}';
    }
}
