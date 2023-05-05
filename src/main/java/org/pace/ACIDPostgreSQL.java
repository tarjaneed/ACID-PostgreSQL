package org.pace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ACIDPostgreSQL {

    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        // Load the Postgres driver
        Class.forName("org.postgresql.Driver");


        // Connect to the database with credentials
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CS623ProjectTeam1", "tarjaneedesai", "Pace1234");

        // Disable AutoCommit for Atomicity
        conn.setAutoCommit(false);

        // For Isolation
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        Statement stmt = null;
        try {
            // Create statement object
            stmt = conn.createStatement();


            // Either all the statements will execute or none. This is Atomicity.

            // Create Product, Depot, Stock  Table if Not Exists
            String createProductTableQuery = "CREATE TABLE IF NOT EXISTS Product (" +
                        "prod_id CHAR(10), " +
                        "pname VARCHAR(30), " +
                        "price DECIMAL, " +
                        "PRIMARY KEY (prod_id)" +
                    ")";

            String createDepotTableQuery = "CREATE TABLE IF NOT EXISTS Depot (" +
                        "dep_id CHAR(10), " +
                        "addr VARCHAR(30), " +
                        "volume INTEGER, " +
                        "PRIMARY KEY (dep_id)" +
                    ")";

            String createStockTableQuery = "CREATE TABLE IF NOT EXISTS Stock (" +
                        "prod_id CHAR(10), " +
                        "dep_id CHAR(10), " +
                        "quantity NUMERIC, " +
                        "PRIMARY KEY(prod_id, dep_id), "+
                        "FOREIGN KEY(prod_id) REFERENCES Product(prod_id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                        "FOREIGN KEY(dep_id) REFERENCES Depot(dep_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ")";

            // Execute Create Tables Queries
            stmt.executeUpdate(createProductTableQuery);
            stmt.executeUpdate(createDepotTableQuery);
            stmt.executeUpdate(createStockTableQuery);

            // Reset all the data
            String deleteProductData = "DELETE FROM Product";
            stmt.executeUpdate(deleteProductData);

            String deleteDepotData = "DELETE FROM Depot";
            stmt.executeUpdate(deleteDepotData);

            // Insert Data Into Tables
            stmt.executeUpdate("INSERT INTO Product (prod_id, pname, price) VALUES " +
                        "('p1','tape',2.5)," +
                        "('p2', 'tv', 250)," +
                        "('p3', 'vcr', 80);" +
                    "");

            stmt.executeUpdate("INSERT INTO Depot (dep_id, addr, volume) VALUES " +
                        "('d1', 'New York', 9000)," +
                        "('d2', 'Syracuse', 6000)," +
                        "('d4', 'New York', 2000);" +
                    "");

            stmt.executeUpdate("INSERT INTO Stock (prod_id, dep_id, quantity) VALUES " +
                        "('p1', 'd1', 1000), " +
                        "('p1', 'd2', -100), " +
                        "('p1', 'd4', 1200), " +
                        "('p3', 'd1', 3000), " +
                        "('p3', 'd4', 2000), " +
                        "('p2', 'd4', 1500), " +
                        "('p2', 'd1', -400), " +
                        "('p2', 'd2', 2000);" +
                    "");

            // Transaction 1: The product p1 is deleted from Product and Stock.
            String deleteProduct = "DELETE FROM Product WHERE prod_id = 'p1'";
            stmt.executeUpdate(deleteProduct);

            // Transaction 3: The product p1 changes its name to pp1 in Product and Stock.
            String updateProduct = "UPDATE Product SET prod_id='pp1' WHERE prod_id = 'p1'";
            stmt.executeUpdate(updateProduct);

            // Transaction 5: We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.
            String insertNewProduct = "INSERT INTO Product VALUES ('p100', 'cd', 5)";
            stmt.executeUpdate(insertNewProduct);

            String insertNewStock = "INSERT INTO Stock VALUES ('p100', 'd2', 50)";
            stmt.executeUpdate(insertNewStock);

        } catch (SQLException e) {
            System.out.println("An exception was thrown " + e);
            // For Atomicity
            conn.rollback();
            stmt.close();
            conn.close();
            return;
        }
        conn.commit();
        stmt.close();
        conn.close();
    }
}
