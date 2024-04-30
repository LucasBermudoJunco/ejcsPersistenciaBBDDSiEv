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
        
    	String datosDeLaConsulta = "Oficina\tCiudad\t\tSuperficie\tVentas\n";
        String ciudad;
        int oficina = 0, superficie = 0;
        double ventas = 0.0;

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

                    System.out.println("\nLa(s) oficina(s) de esta empresa en " + ciudad + " es/son la(s) oficina(s):\n");

                    mostrarMensajeInicial = false;
                }

                oficina = rs.getInt("oficina");
                ciudad = rs.getString("ciudad");
                superficie = rs.getInt("superficie");
                ventas = rs.getDouble("ventas");

                // Creación del texto de cada línea de la consulta
                datosDeLaConsulta += oficina + "\t";
                datosDeLaConsulta += ciudad + "\t";
                if(ciudad.length() < 8){
                	datosDeLaConsulta += "\t";
                } 
                datosDeLaConsulta += superficie + "\t\t";
                datosDeLaConsulta += ventas;

                // Añadido de una coma en caso de que sigan quedando más filas por recorrer
                // (es decir, según el método usado (´´isLast()``): que el lector de las filas
                // no esté situado en la última fila)
                if(!rs.isLast()){
                	datosDeLaConsulta += "\n";
                }
            }

            if(tieneAlgunaOficina){
                System.out.print(datosDeLaConsulta);
            } else {
                System.out.print("\nNo hay ninguna oficina de esta empresa en la ciudad ´´" + ciudad + "``.");
            }
            
            con.close();

        } catch(SQLException excep){
            excep.printStackTrace();
        }
        
        sc.close();

    }

}