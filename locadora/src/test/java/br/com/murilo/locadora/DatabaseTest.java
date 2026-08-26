package br.com.murilo.locadora;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {

    static Connection connection;

    @BeforeAll // vai ser executado antes de todos os testes, somente uma vez
    static void setUpDataBase() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdeb", "sa", "");
        connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR) ");
    }

    @BeforeEach
    void insertUserTest() throws Exception {
        connection.createStatement().execute("insert into users(id, name) values (1, 'José')");
    }

    @Test
//    @Disabled pode ser usado para desativar um teste especifico
    void testUserExists() throws Exception {
        var result = connection.createStatement().executeQuery("select * from users where id = 1");

        Assertions.assertTrue(result.next());
    }

    @AfterAll // executado uma única vez depois de todos os testes
    static void closeDataBase() throws Exception {
        connection.close();
    }

}
