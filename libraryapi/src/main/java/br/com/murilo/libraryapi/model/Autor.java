package br.com.murilo.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity // mapeamento jpa da entidade
@Table(name = "autor", schema = "public") //name = nome da tabela, schema = schema do postgres que a tabela se encontra
@Data  // faz o getter e setter mais algumas outras funcionalidade - hashCode, toString, requiredArgsConstructor
//@Getter
//@Setter
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class) // diz que essa classe vai ficar escutando toda vez que tiver alguma alteração nessa entidade e vai observar se tem as anotações (CreatedDate, LastModifiedDate)
public class Autor {

    @Id
    @Column(name = "id") //@Column não é obrigatório
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false) // 100 carac. e not null
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false) // 50 carac. e not null
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // nome da propriedade mapeada dentro da entidade livro
    // por padrão o relacionamento do ToMany já é o LAZY
    //@Transient // ignorar por enquanto essa coluna, mapeamento jpa
    private List<Livro> livros;

    @CreatedDate // coloca a dataHora atual automaticamente na persistência
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate // preenche com a data toda vez que houver uma atualizaçãp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

}
