package com.tr.epay.dao.pool;

import com.tr.epay.utils.ErrorMessage;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance;

    static {
        try {
            instance = new ConnectionPool();
        } catch (ConnectionPoolException ex) {
            log.error(ErrorMessage.RUNTIME_ERROR);
            throw new RuntimeException(ErrorMessage.RUNTIME_ERROR, ex);
        }
    }


    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private final String driverName;
    private final String url;
    private final String user;
    private final String password;
    private int poolSize;

    private ConnectionPool() throws ConnectionPoolException {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
        initPoolData();     //
    }

    public static ConnectionPool getInstance(){
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException {

        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_WITH_DATABASE);
            throw new ConnectionPoolException(ErrorMessage.ERROR_WITH_DATABASE, e);
        } catch (ClassNotFoundException e) {
            log.error(ErrorMessage.DRIVER_ERROR);
            throw new ConnectionPoolException(ErrorMessage.DRIVER_ERROR, e);
        }
    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION, e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            log.error(ErrorMessage.ERROR_CONNECTING_TO_DATA_SOURCE);
            throw new ConnectionPoolException(ErrorMessage.ERROR_CONNECTING_TO_DATA_SOURCE, e);
        }
        return connection;
    }

    public void closeConnection(Connection conn, Statement st, ResultSet rs) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            rs.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            st.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }

    }

    public void closeConnection(Connection conn, Statement st1, Statement st2, ResultSet rs) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            rs.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            st1.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
        try {
            st2.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }

    }

    public void closeConnection(Connection conn, Statement st1, Statement st2, Statement st3, ResultSet rs1, ResultSet rs2){
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            rs1.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            rs2.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            st1.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
        try {
            st2.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
        try {
            st3.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
    }

    public void closeConnection(Connection conn, Statement st) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            st.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }

    }

    public void closeConnection(Connection conn, Statement st1, Statement st2) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            st1.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
        try {
            st2.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }

    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }

    }

    public void closeConnection(Connection connection, PreparedStatement preparedStatementFrom, PreparedStatement preparedStatementTo, ResultSet resultSetFrom, ResultSet resultSetTo) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_CONNECTION);
        }
        try {
            resultSetFrom.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            resultSetTo.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_RESULT_SET);
        }
        try {
            preparedStatementFrom.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }
        try {
            preparedStatementTo.close();
        } catch (SQLException e) {
            log.error(ErrorMessage.ERROR_CLOSING_STATEMENT);
        }

    }



    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if(connection.isClosed()){
                throw new SQLException("Attempting to close closed connection.");
            }

            if(connection.isReadOnly()){
                connection.setReadOnly(false);
            }

            if(!givenAwayConQueue.remove(this)){
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }

            if(!connectionQueue.offer(this)){
                throw new SQLException("Error allocating connection in the pool.");
            }

        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        @Override
        public void setHoldability(int holdability) throws SQLException {

        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {

        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {

        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {

        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }


        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {

        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {

        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {

        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);

        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException{
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();

        }


        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }
}




