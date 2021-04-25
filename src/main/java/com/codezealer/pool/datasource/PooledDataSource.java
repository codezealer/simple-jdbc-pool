package com.codezealer.pool.datasource;

import com.codezealer.pool.connection.IPooledConnection;
import com.codezealer.pool.connection.PooledConnection;
import com.codezealer.pool.exception.JdbcPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PooledDataSource extends AbstractPooledDataSourceConfig {

    List<IPooledConnection> pool = new ArrayList<>();

    @Override
    public synchronized void init() {

        try {
            Class.forName(super.getDriverClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i=0; i<this.minSize; i++) {
            IPooledConnection pooledConnection = createPooledConnection();
            pool.add(pooledConnection);
        }
    }

    private IPooledConnection createPooledConnection() {
        Connection connection = createConnection();
        IPooledConnection pooledConnection = new PooledConnection();
        pooledConnection.setBusy(false);
        pooledConnection.setConnection(connection);
        return pooledConnection;
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(super.getJdbcUrl(), super.getUser(), super.getPassword());
        } catch (SQLException throwables) {
            throw new JdbcPoolException(throwables);
        }
    }

    @Override
    public void setMaxSize(int size) {
        super.setMaxSize(size);
    }

    @Override
    public void setMinSize(int size) {
        super.setMinSize(size);
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        //先查找是否存在不忙的连接，存在则直接返回
        for (IPooledConnection pooledConnection : pool) {
            if (!pooledConnection.isBusy()) {
                pooledConnection.setBusy(true);
                System.out.println("get the pool connection...");
                return pooledConnection;
            }
        }

        if (pool.size() >= super.maxSize) {
            throw new JdbcPoolException("连接池已满，不能再创建链接了。连接数：" + pool.size());
        }

        System.out.println("[grow] create a new connection.");
        IPooledConnection connection = createPooledConnection();
        connection.setBusy(true);
        pool.add(connection);

        return connection;
    }

}
