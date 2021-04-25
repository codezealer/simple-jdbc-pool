package com.codezealer.pool.connection;

import java.sql.Connection;

public interface IPooledConnection extends Connection {

    boolean isBusy();

    void setBusy(boolean busy);

    Connection getConnection();

    void setConnection(Connection connection);

}
