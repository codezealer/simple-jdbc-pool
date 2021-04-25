package com.codezealer.pool.datasource;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UnPooledDataSourceTest {

    @Test
    public void test() throws SQLException {
        UnPooledDatasource datasource = new UnPooledDatasource();
        datasource.setDriverClass("com.mysql.cj.jdbc.Driver");
        datasource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8");
        datasource.setUser("root");
        datasource.setPassword("root");

        Connection connection = datasource.getConnection();
        System.out.println(connection.getCatalog());
    }

}
