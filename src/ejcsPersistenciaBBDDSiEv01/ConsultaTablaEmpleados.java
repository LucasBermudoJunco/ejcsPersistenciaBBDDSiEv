package ejcsPersistenciaBBDDSiEv01;

import java.sql.*;

public class ConsultaTablaEmpleados {

    public static void main(String[] args) {

        Connection con;
        Statement st;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/Empresa";
        String user = "root";
        String password = "admin";
        String consulta;

        String nombre, puesto;
        int numEmp, edad, oficina;
        Date contrato;

        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("\nConexión realizada con éxito.");
            st = con.createStatement();

            consulta = "select * from empleados";
            rs = st.executeQuery(consulta);

            System.out.println("\nInformación de la tabla Empleados:\n");
            System.out.println("NumEmp\tNombre\t\t\tEdad\tOficina\tPuesto\t\t\t\t\t\tContrato");
            System.out.println("------------------------------------------" +
                    "-----------------------------------------");

            // Recorrido de la tabla Empleados
            while(rs.next()) {
                // Recogida de los campos de la tabla en variables
                numEmp = rs.getInt("numemp");
                nombre = rs.getString("nombre");
                edad = rs.getInt("edad");
                oficina = rs.getInt("oficina");
                puesto = rs.getString("puesto");
                contrato = rs.getDate("contrato");

                // Creación del texto de cada línea de la consulta
                String estaLineaDeLaConsulta = "";
                estaLineaDeLaConsulta += numEmp + "\t";
                estaLineaDeLaConsulta += nombre;
                if(nombre.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t\t";
                } else if(nombre.length() < 12){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(nombre.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += edad + "\t\t";
                estaLineaDeLaConsulta += oficina + "\t\t";
                estaLineaDeLaConsulta += puesto + "\t\t";
                if(puesto.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t\t";
                } else if(puesto.length() < 12){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(puesto.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else if(puesto.length() < 21){
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += contrato;

                // Impresión de cada línea de la consulta
                System.out.println(estaLineaDeLaConsulta);
            }

            con.close();

        } catch(SQLException excep){
            excep.printStackTrace();
        }

    }

}