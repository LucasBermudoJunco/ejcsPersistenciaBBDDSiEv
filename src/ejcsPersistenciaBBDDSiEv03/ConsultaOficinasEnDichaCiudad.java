package ejcsPersistenciaBBDDSiEv03;

import java.sql.*;
import java.util.Scanner;

public class ConsultaOficinasEnDichaCiudad {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Connection con;
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/Empresa";
        String user = "root";
        String password = "admin";
        String consulta;

        String ciudad;
        int oficina = 0;

        boolean datoIntrodValido, tieneAlgunaOficina = false, mostrarMensajeInicial = true;

        do {
            datoIntrodValido = true;

            System.out.print("Introduce la ciudad de la cual desees saber las oficinas que tiene:  ");
            ciudad = sc.nextLine();

            if(ciudad.isEmpty()){
                datoIntrodValido = false;

                System.out.println("\nLa ciudad introducida tiene que tener al menos 1 caracter." +
                        "\nVuelva a introducirla.\n");
            }
        } while(!datoIntrodValido);

        try{
            con = DriverManager.getConnection(url,user,password);
            st = con.createStatement();

            consulta = "select * from oficinas where ciudad = '" + ciudad + "'";
            rs = st.executeQuery(consulta);

            while(rs.next()){
                if(mostrarMensajeInicial){
                    tieneAlgunaOficina = true;

                    System.out.print("\nLa(s) oficina(s) de esta empresa en " + ciudad + " es/son la(s) oficina(s) número:  ");

                    mostrarMensajeInicial = false;
                }

                oficina = rs.getInt("oficina");

                System.out.print(oficina);

                // Añadido de una coma en caso de que sigan quedando más filas por recorrer
                // (es decir, según el método usado (´´isLast()``): que el lector de las filas
                // no esté situado en la última fila)
                if(!rs.isLast()){
                    System.out.print(", ");
                }
            }

            if(!tieneAlgunaOficina){
                System.out.print("\nNo hay ninguna oficina de esta empresa en la ciudad ´´" + ciudad + "``.");
            }

        } catch(SQLException excep){
            excep.printStackTrace();
        }

    }

}
