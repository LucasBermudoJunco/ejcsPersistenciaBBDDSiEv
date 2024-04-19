package ejcsPersistenciaBBDD00Pruebas;

import java.sql.*;
import java.util.Scanner;

public class prueba4_Insert {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String insercion, nombre1, nombre2, tabla;
        Connection connection = null;
        System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bdprueba1?serverTimezone=UTC";
        String user = "root";
        String pass = "admin";
        boolean conexionIniciadaCorrectamente = false;

        try {
            System.out.print("Introduzca la tabla que quiere insertar datos: ");
            tabla = sc.nextLine();
            System.out.print("\nIntroduzca el 1º nombre que quiere insertar:  ");
            nombre1 = sc.nextLine();
            System.out.print("\nIntroduzca el 2º nombre que quiere insertar:  ");
            nombre2 = sc.nextLine();

            connection = DriverManager.getConnection(url, user, pass);
            conexionIniciadaCorrectamente = true;
            System.out.println("\nConnection success.");
            Statement st = connection.createStatement();

            insercion = "insert into " + tabla + " values ('" + nombre1 + "'),('" + nombre2 + "');";
            int cantFilasAfectadas = st.executeUpdate(insercion);
            System.out.println("\nHan sido afectadas " + cantFilasAfectadas + " fila(s).");

            connection.close();
            System.out.println("\nConnection closed.");
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
