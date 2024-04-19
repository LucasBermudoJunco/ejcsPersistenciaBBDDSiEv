package ejcsPersistenciaBBDDSiEv02;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class ConsultaTablaOficinas {

    public static void main(String[] args) {

        Connection con;
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/Empresa";
        String user = "root";
        String password = "admin";
        String consulta;

        Empresa corporacion = new Empresa("1","Oracle");

        int oficina, superficie;
        String ciudad;
        double ventas;

        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("\nConexión realizada con éxito.");
            st = con.createStatement();

            consulta = "select * from oficinas";
            rs = st.executeQuery(consulta);

            // Recorrido de la tabla Oficinas
            while (rs.next()) {
                // Recogida de los campos de la tabla en variables
                oficina = rs.getInt("oficina");
                ciudad = rs.getString("ciudad");
                superficie = rs.getInt("superficie");
                ventas = rs.getDouble("ventas");

                // Creación de un objeto Oficina con los campos recogidos
                Oficina estaOficina = new Oficina(oficina,ciudad,superficie,ventas);

                // Añadido de la oficina al objeto Empresa
                corporacion.anyadirOficina(estaOficina);
            }

        } catch(SQLException excep){
            excep.printStackTrace();
        }

        // Visualización del contenido de la empresa tras haber leído la base de datos
        System.out.println("Oficinas de la empresa:");

        System.out.println(corporacion);

//        int contador = 0;
//        for(Map.Entry<Integer,Oficina> entrada : corporacion.getListaOficinas().entrySet()){
//            Oficina estaOficina = entrada.getValue();
//
//            contador++;
//
//            System.out.println("\n" + contador + "ª oficina:\n" + estaOficina);
//        }

    }

}
