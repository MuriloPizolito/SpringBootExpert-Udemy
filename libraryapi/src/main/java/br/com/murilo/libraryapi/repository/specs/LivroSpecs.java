package br.com.murilo.libraryapi.repository.specs;

import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        // where isbn = :isbn
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        // upper(livro.titulo) like (%:param%) - usando o (%:titulo%) para comparar em qualquer lugar da string
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"); //colocando tudo maiúsculo para comparar as strings
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor) {

        return (root, query, cb) -> {
            // return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }


}
