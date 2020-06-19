package ca.jrvs.apps.jdbc;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
                                    "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
