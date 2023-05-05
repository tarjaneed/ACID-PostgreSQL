# ACID-PostgreSQL Project

This project demonstrates the use of ACID (Atomicity, Consistency, Isolation, Durability) properties in PostgreSQL using Java JDBC.

## Overview

The ACID-PostgreSQL project provides a Java program that showcases the atomicity, consistency, isolation, and durability features of PostgreSQL database transactions. It performs various operations on three tables: Product, Depot, and Stock, demonstrating the behavior of ACID properties.

## Prerequisites

To run this project, you need to have the following:

- Java Development Kit (JDK)
- PostgreSQL database
- PostgreSQL JDBC driver

## Installation

1. Clone the repository to your local machine:

   ```shell
   git clone https://github.com/your-username/ACID-PostgreSQL.git
   ```

2. Configure the PostgreSQL database:

    - Create a new database named "CS623ProjectTeam1" in your PostgreSQL server.

3. Update database credentials:

    - Open the `ACIDPostgreSQL.java` file.
    - Locate the following line:

      ```java
      Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CS623ProjectTeam1", "tarjaneedesai", "Pace1234");
      ```

    - Modify the connection URL, username, and password according to your PostgreSQL configuration.

4. Import required libraries:

    - Make sure you have the PostgreSQL JDBC driver (JAR file) added to your project's classpath.

5. Build and run the project:

6. Verify the results:

    - The program will execute various SQL statements, demonstrating the behavior of ACID properties.
    - Check your PostgreSQL database to observe the changes made during the transactions.

## Transactions

The Java application performs the following transactions on the sample database:

1. Creates the `Product`, `Depot`, and `Stock` tables if they don't exist.
2. Deletes all existing data from the `Product` and `Depot` tables.
3. Inserts sample data into the `Product` and `Depot` tables.
4. Inserts sample data into the `Stock` table.
5. Provides commented-out code for three additional transactions:
    - Transaction 1: Deletes the product `p1` from the `Product` and `Stock` tables.
    - Transaction 3: Changes the name of product `p1` to `pp1` in the `Product` and `Stock` tables.
    - Transaction 5: Adds a new product (`p100`, `cd`, `5`) to the `Product` table and corresponding entry (`p100`, `d2`, `50`) in the `Stock` table.

**Note:** 

- The additional transactions are commented out to prevent accidental execution. Uncomment the desired transaction code before running the application.


- Deletes all existing data from the `Product` and `Depot` tables. This is for resetting data to add it again after executing 
the delete transaction to avoid commenting/uncommenting of the insert statements for showcasing other transactions.