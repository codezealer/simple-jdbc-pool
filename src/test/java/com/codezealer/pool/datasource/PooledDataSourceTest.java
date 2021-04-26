package com.codezealer.pool.datasource;

import com.sun.jndi.ldap.pool.Pool;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testNoWait() throws SQLException, InterruptedException {
        PooledDataSource source = new PooledDataSource();
        source.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8");
        source.setUser("root");
        source.setPassword("root");
        source.setMinSize(1);
        source.setMaxSize(1);
        source.setMaxWaitMills(5);

        // 初始化
        source.init();

        Connection connection = source.getConnection();
        System.out.println(connection.getCatalog());

        new Thread(() -> {
            try {
                Connection connection1 = source.getConnection();
                System.out.println(connection1.getCatalog());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }).start();

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void testWait() throws SQLException, InterruptedException {
        PooledDataSource source = new PooledDataSource();
        source.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8");
        source.setUser("root");
        source.setPassword("root");
        source.setMinSize(1);
        source.setMaxSize(1);
        source.setMaxWaitMills(100);

        // 初始化
        source.init();

        Connection connection = source.getConnection();
        System.out.println(connection.getCatalog());

        new Thread(() -> {
            try {
                Connection connection1 = source.getConnection();
                System.out.println(connection1.getCatalog());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }).start();

        TimeUnit.MILLISECONDS.sleep(10);

        connection.close();
        System.out.println("释放一个连接.");

        TimeUnit.MILLISECONDS.sleep(100);
    }

}
