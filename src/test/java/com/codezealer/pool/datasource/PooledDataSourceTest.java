package com.codezealer.pool.datasource;

import com.sun.jndi.ldap.pool.Pool;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PooledDataSourceTest {

    @Test
    public void test() throws SQLException {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setMinSize(1);

        dataSource.init();

        Connection connection1 = dataSource.getConnection();
        System.out.println(connection1.getCatalog());

        Connection connection2 = dataSource.getConnection();
        System.out.println(connection2.getCatalog());


    }

}
