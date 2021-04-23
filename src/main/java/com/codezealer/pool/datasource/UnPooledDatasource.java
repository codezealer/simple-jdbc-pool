package com.codezealer.pool.datasource;

import com.codezealer.pool.api.IConfig;
import com.codezealer.pool.exception.JdbcPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UnPooledDatasource extends AbstractDatasource implements IConfig {

    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(this.driverClass);
        } catch (ClassNotFoundException e) {
            throw new JdbcPoolException();
        }
        return DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
