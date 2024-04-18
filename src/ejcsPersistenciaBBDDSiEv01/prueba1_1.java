package ejcsPersistenciaBBDDSiEv01;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class prueba1_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dni, consulta, nombre, apellidos, cuenta;
        double saldo = 0, importe= 0, importe_total = 0;
        LocalDateTime fecha;
        Connection connection;
        Statement st;
        ResultSet rs;

//            System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/cuentas_bancarias?serverTimezone=UTC";
        String user = "root";
        String pass = "admin";
        int opcion;
        try{
            connection = DriverManager.getConnection(url ,user, pass);
            System.out.println("Connection success.");
            do {
                System.out.println("\nCajero Automático");
                System.out.println("1- Retirar fondos");
                System.out.println("2- Ingresar fondos");
                System.out.println("3- Consultar movimientos");
                System.out.println("0- Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1: // Retirar fondos
                        System.out.println("Introduzca la cuenta: ");
                        cuenta = scanner.next();
                        //cuenta = "12345678901234567890";
                        System.out.print("Ingrese la cantidad a retirar: ");
                        importe = scanner.nextDouble();
                        fecha = LocalDateTime.now();
                        consulta = "insert into movimientos values ("
                                + cuenta + ", " + "'" + fecha + "'" + " , " + importe + ")";
                        st = connection.createStatement();
                        st.executeUpdate(consulta);
                        break;
                    case 2: // Ingresar fondos
                        System.out.print("Ingrese la cantidad a depositar: ");
                        break;
                    case 3: // Consultar movimientos
                        break;
                    case 0: // Salir
                        System.out.println("Saliendo del sistema.");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } while (opcion != 0);

            scanner.close();

        } catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }
    }
}

