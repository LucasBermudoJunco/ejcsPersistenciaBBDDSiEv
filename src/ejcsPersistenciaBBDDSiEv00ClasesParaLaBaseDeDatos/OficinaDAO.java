package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OficinaDAO {
	
	public static Connection connect() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/Empresa";
		String user = "root";
		String password = "admin";
		
		try {
			con = DriverManager.getConnection(url,user,password);
		} catch(SQLException excep) {
			System.out.println("Conexión al SGBD fallida.");
			excep.printStackTrace();
		}
		
		return con;
	}
	
	public static boolean esaOficinaExiste(int oficinaAComprobar) {
		boolean esaOficinaExiste = false;
		
		try {
			Connection con = connect();
			String accionSQL = "select 'Sí existe una oficina con ese número de identificación' from "
					+ "oficinas where oficina = ? limit 1";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, oficinaAComprobar);
			
			ResultSet rs = sentPrep.executeQuery();
			
			if(rs.next()) {
				esaOficinaExiste = true;
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Comprobación del número de identificación de la oficina fallida.");
			excep.printStackTrace();
		}
		
		return esaOficinaExiste;
	}

}
