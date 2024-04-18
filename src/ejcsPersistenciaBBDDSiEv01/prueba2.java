package ejcsPersistenciaBBDDSiEv01;

import java.sql.*;
import java.util.Scanner;

public class prueba2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dni, consulta, nombre, apellidos;
        Connection connection;
        System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/cuentas_bancarias?serverTimezone=UTC";
        String user = "root";
        String pass = "admin";
        try{
            System.out.print("Introduzca el DNI : ");
            dni=sc.nextLine();
            connection = DriverManager.getConnection(url ,user, pass);
            System.out.println("Connection success.");
            Statement st = connection.createStatement();
            ResultSet rs;
            consulta = "select nombre, apellidos from clientes where dni = ";
            consulta = consulta + "'" + dni + "'";
            rs=st.executeQuery(consulta);
            rs.next();
            nombre = rs.getString("nombre");
            apellidos = rs.getString("apellidos");
            System.out.println("NOMBRE    -> " + nombre);
            System.out.println("APElLIDOS -> " + apellidos);
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
    }
}

