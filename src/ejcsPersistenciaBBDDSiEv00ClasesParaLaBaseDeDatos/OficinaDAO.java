package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	public static ArrayList<Oficina> read(){
		ArrayList<Oficina> listaOficinas = new ArrayList<>();
		
		try {
			Connection con = connect();
			String accionSQL = "select * from oficinas";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			ResultSet rs = sentPrep.executeQuery();
			
			while(rs.next()) {
				int oficina = rs.getInt("oficina");
				String ciudad = rs.getString("ciudad");
				int superficie = rs.getInt("superficie");
				double ventas = rs.getDouble("ventas");
				
				Oficina estaOficina = new Oficina(oficina,ciudad,superficie,ventas);
				
				listaOficinas.add(estaOficina);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta de las oficinas fallida");
		}
		
		return listaOficinas;
	}
	
	public static boolean mostrarTodasLasOficinasEnFormatoDeBBDD() {
		boolean hayOficinas = false;
		
		Connection con = connect();
		String accionSQL = "select * from oficinas";
		try {
			Statement sent = con.createStatement();
			
			ResultSet rs = sent.executeQuery(accionSQL);
			
			while(rs.next()) {
				hayOficinas = true;
				
				int oficina = rs.getInt("oficina");
				String ciudad = rs.getString("ciudad");
				int superficie = rs.getInt("superficie");
				double ventas = rs.getDouble("ventas");
				
				Oficina estaOficina = new Oficina(oficina,ciudad,superficie,ventas);
				
				System.out.println(estaOficina);
				
				if(!rs.isLast()) {
					System.out.println();
				}
			}
			
			con.close();
		} catch (SQLException excep) {
			excep.printStackTrace();
		}
		
		return hayOficinas;
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
	
//	public static boolean hayOficinas() {
//		boolean hayOficinas = false;
//		
//		try {
//			Connection con = connect();
//			Statement sent = con.createStatement();
//			
//			String accionSQL = "select count(*) as cantOficinas from oficinas";
//			ResultSet rs = sent.executeQuery(accionSQL);
//			
//			rs.next();
//			
//			int cantOficinas = rs.getInt("cantOficinas");
//			
//			if(cantOficinas > 0) {
//				hayOficinas = true;
//			}
//		} catch(SQLException excep) {
//			System.out.println("Consulta de las oficinas fallida.");
//			excep.printStackTrace();
//		}
//		
//		return hayOficinas;
//	}
	
	public static ArrayList<Oficina> selecOficinasConSuperfiMayorA(int superficieIntrod){
		ArrayList<Oficina> listaOficinasConSuperfMayor = new ArrayList<>();
		
		try {
			Connection con = connect();
			String accionSQL = "select * from oficinas where superficie > ?";
			
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1,superficieIntrod);
			
			ResultSet rs = sentPrep.executeQuery();
			
			while(rs.next()) {
				int oficina = rs.getInt("oficina");
				String ciudad = rs.getString("ciudad");
				int superficie = rs.getInt("superficie");
				double ventas = rs.getDouble("ventas");
				
				Oficina estaOficina = new Oficina(oficina,ciudad,superficie,ventas);
				
				listaOficinasConSuperfMayor.add(estaOficina);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta de las oficinas fallida.");
			excep.printStackTrace();
		}
		
		return listaOficinasConSuperfMayor;
	}
	
	public static boolean modificarCiudad(int oficina, String ciudad) {
		boolean oficinaModificada = false;
		
		if(esaOficinaExiste(oficina)) {
			try {
				Connection con = connect();
				String accionSQL = "update oficinas set ciudad = ? where oficina = ?";
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setString(1, ciudad);
				sentPrep.setInt(2, oficina);
				
				sentPrep.executeUpdate();
				
				oficinaModificada = true;
				
				con.close();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
		}
		
		return oficinaModificada;
	}
	
	public static boolean incrementarVentas(int oficina, int incrementoVentas) {
		boolean ventasIncrementadas = false;
		
		if(esaOficinaExiste(oficina)) {
			try {
				Connection con = connect();
				String accionSQL = "update oficinas set ventas = ventas+? where oficina = ?";
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setInt(1, incrementoVentas);
				sentPrep.setInt(2, oficina);
				
				sentPrep.executeUpdate();
				
				ventasIncrementadas = true;
				
				con.close();
			} catch(SQLException excep) {
				excep.printStackTrace();
			}
		}
		
		return ventasIncrementadas;
	}

}
