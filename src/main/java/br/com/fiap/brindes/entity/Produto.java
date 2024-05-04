package br.com.fiap.brindes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "TB_PRODUTO" , uniqueConstraints = {
        @UniqueConstraint(name = "UK_Nome_Produto_Categoria",columnNames = {"NM_PRODUTO","CATEGORIA"})
})
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
    @SequenceGenerator(
            name = "SQ_PRODUTO",
            sequenceName = "SQ_PRODUTO",
            allocationSize = 1
    )
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "NM_PRODUTO")
    private String nome;

    @Column(name = "PRECO_PROD")
    private Double preco;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "CATEGORIA",
            referencedColumnName = "ID_CATEGORIA",
            foreignKey = @ForeignKey(
                    name = "FK_CATEGORIA_PRODUTO"
            )
    )
    private Categoria categoria;


}
