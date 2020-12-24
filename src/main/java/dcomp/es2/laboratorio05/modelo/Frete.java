package dcomp.es2.laboratorio05.modelo;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Frete {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="O cliente deve ser preenchido")
    @ManyToOne
    private Cliente cliente;

    @NotNull(message="A cidade deve ser preenchida")
    @ManyToOne
    private Cidade cidade;

    @NotEmpty(message="A descricao deve ser preenchida")
    private String descricao;

    @DecimalMin(value = "0.0", inclusive = false, message="O peso deve ser preenchido")
    private BigDecimal peso;

    @DecimalMin(value = "0.0", inclusive = false, message="O valor deve ser preenchido")
    private BigDecimal valor;

    public Frete() {}

    public Frete(Cliente cliente, Cidade cidade, String descricao, BigDecimal peso, BigDecimal valor) {
        this.cliente = cliente;
        this.cidade = cidade;
        this.descricao = descricao;
        this.peso = peso;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frete frete = (Frete) o;
        return Objects.equals(id, frete.id) && Objects.equals(cliente, frete.cliente) && Objects.equals(cidade, frete.cidade) && Objects.equals(descricao, frete.descricao) && Objects.equals(peso, frete.peso) && Objects.equals(valor, frete.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, cidade, descricao, peso, valor);
    }

    @Override
    public String toString() {
        return "Frete{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", cidade=" + cidade +
                ", descricao='" + descricao + '\'' +
                ", peso=" + peso +
                ", valor=" + valor +
                '}';
    }
}
