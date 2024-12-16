package com.makar.tenant.connection;

import com.makar.tenant.context.TenantNameContextHolder;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TenantAwareDataSource extends DelegatingDataSource {

    public TenantAwareDataSource(DataSource targetDataSource) {
        super(targetDataSource);
    }

    @Nonnull
    @Override
    public Connection getConnection() throws SQLException {
        var connection = super.getConnection();
        TenantNameContextHolder.find().ifPresent(name -> setSchema(connection, name));
        return connection;
    }

    @Nonnull
    @Override
    public Connection getConnection(@Nonnull String username, @Nonnull String password) throws SQLException {
        var connection = super.getConnection(username, password);
        TenantNameContextHolder.find().ifPresent(name -> setSchema(connection, name));
        return connection;
    }

    private void setSchema(Connection connection, String tenantName) {
        try {
            connection.setSchema(tenantName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
