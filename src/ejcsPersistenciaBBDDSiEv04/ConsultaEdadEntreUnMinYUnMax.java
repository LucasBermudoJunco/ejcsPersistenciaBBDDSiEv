package ejcsPersistenciaBBDDSiEv04;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsultaEdadEntreUnMinYUnMax {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Connection con;
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/Empresa";
        String user = "root";
        String password = "admin";
        String consulta;

        String nombre;
        int edad;

        int valorMin = 0, valorMax = 0;
        boolean datosIntrodValidos, hayAlgunEmpleadoEnEseRango = false, mostrarMensajeInicial = true;

        do{
            datosIntrodValidos = true;

            try {
                System.out.print("Introduce el valor mínimo para la edad que quieras consultar:  ");
                valorMin = sc.nextInt();
                System.out.print("Introduce el valor máximo para la edad que quieras consultar:  ");
                valorMax = sc.nextInt();

                if(valorMin > valorMax){
                    datosIntrodValidos = false;

                    System.out.println("\nEl valor mínimo no puede ser más grande que el máximo." +
                            "\nVuelve a introducirlos de nuevo.\n");
                }
            } catch(InputMismatchException excep){
                datosIntrodValidos = false;
                sc.nextLine();

                System.out.println("\nLos datos introducidos tienen que ser números enteros." +
                        "\nVuelva a introducirlos de nuevo.\n");
            }
        } while(!datosIntrodValidos);

        try{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            consulta = "select nombre, edad from empleados where edad between "
                    + valorMin + " and " + valorMax;
            rs = st.executeQuery(consulta);

            while(rs.next()){
                if(mostrarMensajeInicial){
                    hayAlgunEmpleadoEnEseRango = true;

                    if(valorMin < valorMax) {
                        System.out.println("\nEl nombre y la edad del/de los empleado(s) que tiene(n) entre "
                                + valorMin + " y " + valorMax + " años es/son:");
                    } else {
                        System.out.println("\nEl nombre y la edad del/de los empleado(s) que tiene(n) "
                                + valorMin + " años es/son:");
                    }

                    System.out.println("\nNombre\t\t\tEdad");
                    System.out.println("------------------------------");

                    mostrarMensajeInicial = false;
                }

                nombre = rs.getString("nombre");
                edad = rs.getInt("edad");

                String estaLineaDeLaConsulta = "";
                estaLineaDeLaConsulta += nombre;
                if(nombre.length() < 7){
                    estaLineaDeLaConsulta += "\t\t\t\t";
                } else if(nombre.length() < 9){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(nombre.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += edad;

                System.out.println(estaLineaDeLaConsulta);

            }

            if(!hayAlgunEmpleadoEnEseRango){
                if(valorMin < valorMax) {
                    System.out.println("\nNo hay ningún empleado que tenga entre "
                            + valorMin + " y " + valorMax + " años.");
                } else {
                    System.out.println("\nNo hay ningún empleado que tenga " + valorMin + " años.");
                }
            }
            
            con.close();

        } catch (SQLException excep){
            excep.printStackTrace();
        }
        
        sc.close();

    }

}
