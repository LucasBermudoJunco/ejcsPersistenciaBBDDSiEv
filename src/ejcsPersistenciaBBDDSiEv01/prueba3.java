package ejcsPersistenciaBBDDSiEv01;

import java.sql.*;
import java.util.Scanner;

public class prueba3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String dni, consulta, nombre, apellidos, tabla;
        Connection connection = null;
        System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bdprueba1?serverTimezone=UTC";
        String user = "root";
        String pass = "admin";
        boolean conexionIniciadaCorrectamente = false;
        try {
            System.out.print("Introduzca el nombre a introducir : ");
            nombre = sc.nextLine();
            System.out.print("Introduzca la tabla en la que quiere introdicurlo: ");
            tabla = sc.nextLine();

            connection = DriverManager.getConnection(url, user, pass);
            conexionIniciadaCorrectamente = true;
            System.out.println("Connection success.");
            Statement st = connection.createStatement();
            ResultSet rs;
            consulta = "insert into " + tabla + " values('" + nombre + "')";
            rs = st.executeQuery(consulta);
            rs.next();
            nombre = rs.getString("nombre");
            apellidos = rs.getString("apellidos");
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            if(conexionIniciadaCorrectamente){
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
