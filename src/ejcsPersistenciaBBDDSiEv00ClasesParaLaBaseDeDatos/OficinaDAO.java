package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class OficinaDAO {
	
	// MÉTODOS COMUNES
	
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
	
	public static void create(Oficina oficinaIntrod) {
		if(oficinaIntrod != null) {
			Connection con = connect();
			
			if(con != null) {
				String accionSQL = "insert into oficinas values (?,?,?,?)";
				
				try {
					PreparedStatement sentPrep = con.prepareStatement(accionSQL);

					sentPrep.setInt(1, oficinaIntrod.getOficina());
					sentPrep.setString(2, oficinaIntrod.getCiudad());
					sentPrep.setInt(3, oficinaIntrod.getSuperficie());
					sentPrep.setDouble(4, oficinaIntrod.getVentas());
					
					sentPrep.executeQuery();
					
					con.close();
				} catch(SQLException excep) {
					System.out.println("Inserción de la oficina fallida.");
					excep.printStackTrace();
				}
			}
		}
	}
	
	public static Oficina read(int idOficina) {
		Oficina oficinaConsultada = null;
		
		try {
			Connection con = connect();
			String accionSQL = "Select * from oficinas where oficina = ?";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, idOficina);
			
			ResultSet rs = sentPrep.executeQuery();
			
			if(rs.next()) {
				// Aquí iría el int numemp, pero es precisamente lo que
				// hemos introducido como parámetro
				int oficina = rs.getInt("oficina");
				String ciudad = rs.getString("ciudad");
				int superficie = rs.getInt("superficie");
				double ventas = rs.getDouble("ventas");
				
				oficinaConsultada = new Oficina(oficina,ciudad,superficie,ventas);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta del empleado fallida.");
			excep.getStackTrace();
		}
		
		return oficinaConsultada;
	}
	
	public static void update(Oficina oficinaIntrod) {
		if(oficinaIntrod != null) {
			try {
				Connection con = connect();
				String accionSQL = "update Oficinas set "
						+ "ciudad = ?, superficie = ?, ventas = ?"
						+ "where oficina = ?";
				
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setString(1, oficinaIntrod.getCiudad());
				sentPrep.setInt(2, oficinaIntrod.getSuperficie());
				sentPrep.setDouble(3, oficinaIntrod.getVentas());
				sentPrep.setInt(4, oficinaIntrod.getOficina());
				
				sentPrep.executeUpdate();
				
				con.close();
			} catch(SQLException excep) {
				System.out.println("Actualización de los empleados fallida.");
				excep.printStackTrace();
			}
		}
	}
	
	public static void delete(int idOficina) {
		try {
			Connection con = connect();
			String accionSQL = "delete from oficinas where oficina = ?";
			
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, idOficina);
			
			sentPrep.executeUpdate();
			
			con.close();
		} catch(SQLException excep) {
			excep.printStackTrace();
		}
	}
	
	// MÉTODOS ESPECÍFICOS
	
	public static String obtenerLaCabeceraDeTodosLosCamposDeLaTablaOficinas() {
		return "Oficina\tCiudad\t\tSuperficie\tVentas"
				+ "\n------------------------"
        		+ "------------------------";
	}
	
	public static ArrayList<Oficina> obtenerArrayListDeTodasLasOficinas(){
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
	
	public static boolean mostrarTodasEstasOficinasEnFormatoDeBBDD(ArrayList<Oficina> listaDeOficinas) {
		boolean hayOficinas = false;
		
		if(!listaDeOficinas.isEmpty()) {
			hayOficinas = true;
			
	    	String texto = obtenerLaCabeceraDeTodosLosCamposDeLaTablaOficinas() + "\n";
	    	
			Iterator<Oficina> iteradorOficinas = listaDeOficinas.iterator();
			
			while(iteradorOficinas.hasNext()) {
				Oficina estaOficina = iteradorOficinas.next();

	            texto += estaOficina.getOficina() + "\t";
	            texto += estaOficina.getCiudad() + "\t";
	            if(estaOficina.getCiudad().length() < 8){
	            	texto += "\t";
	            } 
	            texto += estaOficina.getSuperficie() + "\t\t";
	            texto += estaOficina.getVentas();
	            
	            if(iteradorOficinas.hasNext()) {
	            	texto += "\n";
	            }
			}
			
			System.out.println(texto);
		}
		
		return hayOficinas;
	}
	
	public static boolean hayYaUnaOficinaConEseCodigo(int oficinaAComprobar) {
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
		
		if(hayYaUnaOficinaConEseCodigo(oficina)) {
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
		
		if(hayYaUnaOficinaConEseCodigo(oficina)) {
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
