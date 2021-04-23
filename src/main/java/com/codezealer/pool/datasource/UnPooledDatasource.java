package com.codezealer.pool.datasource;

import com.codezealer.pool.api.IConfig;
import com.codezealer.pool.exception.JdbcPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UnPooledDatasource extends AbstractDatasource {

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(this.getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new JdbcPoolException();
        }
        return DriverManager.getConnection(this.getJdbcUrl(), this.getUser(), this.getPassword());
    }

}
