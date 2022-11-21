package com.dh.ProyectoFinal.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    private static final String SQL_DROP_CREATE_PACIENTES="DROP TABLE IF EXISTS " +
            "PACIENTES; CREATE TABLE PACIENTES (ID INT AUTO_INCREMENT PRIMARY KEY," +
            " NOMBRE VARCHAR(100) NOT NULL," +
            " APELLIDO VARCHAR (100) NOT NULL," +
            " DNI VARCHAR(100) NOT NULL," +
            " FECHA_INGRESO DATE NOT NULL," +
            " DOMICILIO_ID INT NOT NULL," +
            " EMAIL VARCHAR(100) NOT NULL)";

    private static final String SQL_DROP_CREATE_DOMICILIOS="DROP TABLE IF EXISTS " +
            "DOMICILIOS; CREATE TABLE DOMICILIOS (ID INT AUTO_INCREMENT PRIMARY KEY," +
            " CALLE VARCHAR(100) NOT NULL," +
            " NUMERO INT NOT NULL," +
            " LOCALIDAD VARCHAR(100) NOT NULL," +
            " PROVINCIA VARCHAR(100) NOT NULL" +
            ")";
    private static final String SQL_DROP_CREATE_ODONTOLOGOS="DROP TABLE IF EXISTS ODONTOLOGOS;" +
            "CREATE TABLE ODONTOLOGOS (ID INT AUTO_INCREMENT PRIMARY KEY," +
            "MATRICULA VARCHAR(100)," +
            "NOMBRE VARCHAR(100)," +
            "APELLIDO VARCHAR(100)" +
            ")";

    private static final String SQL_INSERT="INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA)" +
            " VALUES ('Calle a',58,'Salta capital','Salta'); INSERT INTO PACIENTES (NOMBRE, APELLIDO," +
            "DNI, FECHA_INGRESO, DOMICILIO_ID, EMAIL) VALUES ('Maria','Cavallo','684','2022-11-10'," +
            "1,'prueba@gmail.com');INSERT INTO ODONTOLOGOS (MATRICULA,NOMBRE,APELLIDO) VALUES " +
            "('MN8471','Pedro','Muelas')";
    public static Connection getConnection() throws Exception{
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/Database/my",
                "root","myPassword");
    }
    public static void crearTablas(){
        Connection connection=null;
        try{
            connection=getConnection();
            Statement statement= connection.createStatement();
            statement.execute(SQL_DROP_CREATE_DOMICILIOS);
            statement.execute(SQL_DROP_CREATE_PACIENTES);
            statement.execute(SQL_DROP_CREATE_ODONTOLOGOS);
            statement.execute(SQL_INSERT);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
