package com.codezealer.pool.datasource;

import com.codezealer.pool.connection.IPooledConnection;
import com.codezealer.pool.connection.PooledConnection;
import com.codezealer.pool.exception.JdbcPoolException;
import com.codezealer.pool.util.DriverClassUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PooledDataSource extends AbstractPooledDataSourceConfig {

    List<IPooledConnection> pool = new ArrayList<>();

    @Override
    public synchronized void init() {
        //根据jdbcUrl加载Driver
        DriverClassUtil.loadDriverClass(null, this.getJdbcUrl());
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
        IPooledConnection pooledConnection = getFreeConnection();
        if (pooledConnection != null) {
            return pooledConnection;
        }

        if (pool.size() >= super.maxSize) {
            if (this.maxWaitMills <= 0) {
                throw new JdbcPoolException("can't get connection from pool.");
            }

            long startMills = System.currentTimeMillis();
            long endMills = startMills + this.maxWaitMills;
            while (System.currentTimeMillis() < endMills) {
                IPooledConnection freeConnection = getFreeConnection();
                if (freeConnection != null) {
                    return freeConnection;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("等待连接池归还连接...");
            }
            throw new JdbcPoolException("连接池已满，同时已经等待了时间：" + this.maxWaitMills);
        }

        System.out.println("[grow] create a new connection.");
        IPooledConnection connection = createPooledConnection();
        connection.setBusy(true);
        pool.add(connection);

        return connection;
    }

    private IPooledConnection getFreeConnection() {
        for (IPooledConnection pooledConnection : pool) {
            if (!pooledConnection.isBusy()) {
                pooledConnection.setBusy(true);
                System.out.println("get the pool connection...");
                return pooledConnection;
            }
        }
        return null;
    }

}
