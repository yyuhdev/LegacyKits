package club.revived.util.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlDataManager {
    private HikariDataSource dataSource;

    public SqlDataManager(String host, String database, String username, String password, int port) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://<host>:<port>/<database>?useSSL=false"
                .replace("<host>", host)
                .replace("<port>", String.valueOf(port))
                .replace("<database>", database)
        );
        config.setUsername(username);
        config.setMaximumPoolSize(20);
        config.setLeakDetectionThreshold(2000);
        config.setPoolName("MyPool");
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(15);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(300000);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
        SqlTablebuilder sqlTablebuilder = new SqlTablebuilder(this);
        sqlTablebuilder.createKitsTable();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
