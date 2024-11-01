package com.makar.tenant.connection;

import com.makar.tenant.service.TokenIdentifierResolver;
import jakarta.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DelegatingDataSource;

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
        setSchema(connection);
        return connection;
    }

    @Nonnull
    @Override
    public Connection getConnection(@Nonnull String username, @Nonnull String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        setSchema(connection);
        return connection;
    }

    private void setSchema(Connection connection) throws SQLException {
        String tenantId = tokenIdentifierResolver.resolveTenant();
        if (tenantId != null) {
            connection.setSchema(tenantId);
        }
    }

}
