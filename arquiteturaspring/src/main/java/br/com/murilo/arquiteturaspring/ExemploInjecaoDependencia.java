package br.com.murilo.arquiteturaspring;

import br.com.murilo.arquiteturaspring.todos.MailSender;
import br.com.murilo.arquiteturaspring.todos.TodoRepository;
import br.com.murilo.arquiteturaspring.todos.TodoService;
import br.com.murilo.arquiteturaspring.todos.TodoValidator;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ExemploInjecaoDependencia{
    public static void main(String[] args) throws SQLException {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("url");
        dataSource.setUsername("user");
        dataSource.setPassword("password");

        Connection connection = dataSource.getConnection();

        EntityManager entityManager = null;

        TodoRepository repository = null;// new SimpleJpaRepository<TodoEntity, Integer>(null, null);
        TodoValidator todoValidator = new TodoValidator(repository);
        MailSender sender = new MailSender();

        TodoService todoService = new TodoService(repository, todoValidator, sender);

//        BeanGerenciado beangerenciado = new BeanGerenciado(null);
//        beangerenciado.setValidator(todoValidator);
//        if (condicao == true){
//            beangerenciado.setValidator();
//        }

    }
}
