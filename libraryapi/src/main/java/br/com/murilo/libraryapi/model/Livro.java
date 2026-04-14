package br.com.murilo.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data // faz o getter e setter mais algumas outras funcionalidade - hashCode, toString, requiredArgsConstructor
//@Getter
//@Setter
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "genero", length = 30, nullable = false)
    @Enumerated(EnumType.STRING) // informa qual o tipo de enumeração
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2) //18 posicoes e 2 casas decimais
    private BigDecimal preco;

    // Many = se refere a entidade atual / to / One = refere ao mapeamento
    @ManyToOne//(cascade = CascadeType.ALL) // muitos para um - Um autor pode ter 1 ou mais livros / usando cascade. Operação em cascata, qualquer alteração no livro ele vai trazer o autor junto (executar na tabela de autor tbm)
    @JoinColumn(name = "id_autor") // relacionamento da coluna
    private Autor autor;

}
