package com.makar.tenant.connection;

import com.makar.tenant.security.TokenIdentifierResolver;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TenantAwareDataSource extends DelegatingDataSource {

    private final TokenIdentifierResolver tokenIdentifierResolver;

    public TenantAwareDataSource(DataSource targetDataSource, TokenIdentifierResolver tokenIdentifierResolver) {
        super(targetDataSource);
        this.tokenIdentifierResolver = tokenIdentifierResolver;
    }

    @Nonnull
    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        tokenIdentifierResolver.resolveTenant()
                .ifPresent(tenantName -> setSchema(connection, tenantName));
        return connection;
    }

    @Nonnull
    @Override
    public Connection getConnection(@Nonnull String username, @Nonnull String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        tokenIdentifierResolver.resolveTenant()
                .ifPresent(tenantName -> setSchema(connection, tenantName));
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
