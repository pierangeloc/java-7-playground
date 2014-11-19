package com.pierangeloc.foundation.ocp.jdbc;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Random;

/**
 *
 */
public class DriverManagerConcepts {
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mydb";

   public static void printAllCustomers() throws SQLException, ClassNotFoundException {
       Connection connection = getConnection();

       Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
       String query = "select * from Customer";
       ResultSet rs = stmt.executeQuery(query);
       while(rs.next()) {
           System.out.print("CustomerId: " + rs.getInt("CustomerId"));
           System.out.print(", FirstName: " + rs.getString("FirstName"));
           System.out.print(", LastName: " + rs.getString("LastName"));
           System.out.print(", EMail: " + rs.getString("EMail"));
           System.out.print(", Phone: " + rs.getString("Phone"));
           //this would throw a SQLException:
           //System.out.print(", Phone: " + rs.getString("Phonezzzz"));

           System.out.println();
       }
   }

    public static void printAllCustomersCyclicallyWithSensitiveRsType() throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = getConnection();

        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        String query = "select * from Customer";
        ResultSet rs = stmt.executeQuery(query);
        while(true) {
            while (rs.next()) {
                System.out.print("CustomerId: " + rs.getInt("CustomerId"));
                System.out.print(", FirstName: " + rs.getString("FirstName"));
                System.out.print(", LastName: " + rs.getString("LastName"));
                System.out.print(", EMail: " + rs.getString("EMail"));
                System.out.print(", Phone: " + rs.getString("Phone"));
                //this would throw a SQLException:
                //System.out.print(", Phone: " + rs.getString("Phonezzzz"));

                System.out.println();
            }
            Thread.sleep(1000);
            rs.beforeFirst();
            System.out.println();

        }
    }


    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver"); //mandatory for JDBC < 4.0
        return DriverManager.getConnection(CONNECTION_URL, "root", "pwd");
    }

    public static void updateCustomers() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();

        int resultCode = stmt.executeUpdate("UPDATE Customer set Phone='06123123123' where Phone='xxxx'");
        System.out.println("executed update with result code: " + resultCode);
    }

    public static void addBookAndAuthor() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        try {
            System.out.println("beginning tx");
            connection.setAutoCommit(false); //begin tx
            Statement stmt = connection.createStatement();
            Random random = new Random();
            String authorId = random.nextInt() + "";
            String firstName = random.nextInt() + "";
            String lastName = random.nextInt() + "";
//            stmt.execute("INSERT INTO Author VALUES (" + authorId + ", " + firstName + "," + lastName + ")");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Author VALUES (?, ?, ?)");
            preparedStatement.setString(1, authorId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.execute();

            String bookId = random.nextInt() + "";
            stmt.execute("INSERT INTO Book VALUES (" + bookId + ", 'Yet Another Title', '2012-12-12', 'Paperback', 8.99)");

            stmt.execute("INSERT INTO Books_by_Author VALUES(" + authorId + "," + bookId + ")");

            System.out.println("committing tx");
            connection.commit(); //commit tx
            System.out.println("tx committed");
        } catch (SQLException sqlException) {
            System.out.println("error occurred: " + sqlException.getSQLState() + "; " + sqlException.getMessage());
            System.out.println("rolling back...");
            connection.rollback();
            System.out.println("rolled back!!!");
        }
    }

    public static void executeGenericQuery(String query) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        boolean isQueryWithResultSet = stmt.execute(query);

        if(isQueryWithResultSet) {
            System.out.println("query returned a result set");
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            System.out.println("Query returned " + resultSetMetaData.getColumnCount() + " columns");
            for(int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                System.out.println("column #" + i);
                System.out.println("Table Name: " + resultSetMetaData.getTableName(i));
                System.out.println("Column Name: " + resultSetMetaData.getColumnName(i));
                System.out.println("Column Size: " + resultSetMetaData.getColumnDisplaySize(i));
                System.out.println("Column Class Name: " + resultSetMetaData.getColumnClassName(i));

            }

            //printing the result set in a nice way
            for(int i = 1; i < resultSetMetaData.getColumnCount(); i++);
        } else {
            int count = stmt.getUpdateCount();
            System.out.println("query affected " + count + " rows");
        }
    }

    public static void readCustomersWithRowSet() throws SQLException {
        JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();
        System.out.println("reading customers with RowSet");
        jrs.setUrl(CONNECTION_URL);
        jrs.setUsername("root");
        jrs.setPassword("eagle01");
        jrs.setCommand("select * from Customer");
//        jrs.setCommand("UPDATE Customer set Phone='06123123123' where Phone='xxxx'");
        jrs.execute();
        while(jrs.next()) {
                System.out.print("CustomerId: " + jrs.getInt("CustomerId"));
                System.out.print(", FirstName: " + jrs.getString("FirstName"));
                System.out.print(", LastName: " + jrs.getString("LastName"));
                System.out.print(", EMail: " + jrs.getString("EMail"));
                System.out.print(", Phone: " + jrs.getString("Phone"));
                //this would throw a SQLException:
                //System.out.print(", Phone: " + rs.getString("Phonezzzz"));

                System.out.println();
        }
        System.out.println("now going to update the customers with rowset, will insert a new customer at line 2");
        jrs.absolute(2);
        jrs.moveToInsertRow();
        int newCustomerId = new Random().nextInt();
        System.out.println("inserting new customer with id: " + newCustomerId);
        jrs.updateInt(1, newCustomerId);
        jrs.updateString(2, "firstName" + new Random().nextInt());
        jrs.updateString(3, "lastName" + new Random().nextInt());
        jrs.updateString(4, "email@" + new Random().nextInt());
        jrs.updateString(5, "phone" + new Random().nextInt());
        jrs.insertRow();
        System.out.printf("row inserted, but rowset didn't change, let's try to list again the customers in rowset:\n");
        jrs.beforeFirst();
        while(jrs.next()) {
            System.out.print("CustomerId: " + jrs.getInt("CustomerId"));
            System.out.print(", FirstName: " + jrs.getString("FirstName"));
            System.out.print(", LastName: " + jrs.getString("LastName"));
            System.out.print(", EMail: " + jrs.getString("EMail"));
            System.out.print(", Phone: " + jrs.getString("Phone"));
            //this would throw a SQLException:
            //System.out.print(", Phone: " + rs.getString("Phonezzzz"));

            System.out.println();
        }
    }


    public static void main(String[] args) throws Exception {
        printAllCustomers();
        updateCustomers();
        printAllCustomers();
        addBookAndAuthor();

//        printAllCustomersCyclicallyWithSensitiveRsType();
        readCustomersWithRowSet();
    }






}
