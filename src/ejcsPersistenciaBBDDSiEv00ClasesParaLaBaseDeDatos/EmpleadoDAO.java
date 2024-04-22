package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO {
	
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
	
	public static void create(Empleado empleadoIntrod) {
		if(empleadoIntrod != null) {
			Connection con = connect();
			
			if(con != null) {
				String accionSQL = "insert into Empleados values (?,?,?,?,?,?)";
				
				try {
					PreparedStatement sentPrep = con.prepareStatement(accionSQL);
					
					sentPrep.setInt(1, empleadoIntrod.getNumemp());
					sentPrep.setString(2, empleadoIntrod.getNombre());
					sentPrep.setInt(3, empleadoIntrod.getEdad());
					sentPrep.setInt(4, empleadoIntrod.getOficina());
					sentPrep.setString(5, empleadoIntrod.getPuesto());
					sentPrep.setDate(6, empleadoIntrod.getContrato());
					
					sentPrep.executeUpdate();
					
					con.close();
				} catch(SQLException excep) {
					System.out.println("Inserción del Empleado fallida.");
					excep.printStackTrace();				
				}
			}
		}
	}
	
	public static ArrayList<Empleado> read() {
		ArrayList<Empleado> listaEmpleadosConsultados = new ArrayList<>();
		
		try {
			Connection con = connect();
			String accionSQL = "Select * from empleados";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			ResultSet rs = sentPrep.executeQuery();
			
			while(rs.next()) {
				int numemp = rs.getInt("numemp");
				String nombre = rs.getString("nombre");
				int edad = rs.getInt("edad");
				int oficina = rs.getInt("oficina");
				String puesto = rs.getString("puesto");
				Date contrato = rs.getDate("contrato");
				
				Empleado empleadoConsultado = new Empleado(numemp,nombre,edad,oficina,puesto,contrato);
				
				listaEmpleadosConsultados.add(empleadoConsultado);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta del empleado fallida.");
			excep.getStackTrace();
		}
		
		return listaEmpleadosConsultados;
	}
	
	public static void update(Empleado empleadoIntrod){
		if(empleadoIntrod != null) {
			try {
				Connection con = connect();
				String accionSQL = "update Empleados set numemp = ?, "
						+ "nombre = ?, edad = ?, oficina = ?, "
						+ "puesto = ?, contrato = ?";
				
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setInt(1, empleadoIntrod.getNumemp());
				sentPrep.setString(2, empleadoIntrod.getNombre());
				sentPrep.setInt(3, empleadoIntrod.getEdad());
				sentPrep.setInt(4, empleadoIntrod.getOficina());
				sentPrep.setString(5, empleadoIntrod.getPuesto());
				sentPrep.setDate(6, empleadoIntrod.getContrato());
				
				sentPrep.executeUpdate();
				
				con.close();
			} catch(SQLException excep) {
				System.out.println("Actualización de los empleados fallida.");
				excep.printStackTrace();
			}
		}
	}
	
	public static void delete(int numemp){
		try {
			Connection con = connect();
			String accionSQL = "delete from empleados where numemp = ?";
			
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, numemp);
			
			sentPrep.executeUpdate();
			
			con.close();
		} catch(SQLException excep) {
			excep.printStackTrace();
		}
	}
	
	public static boolean esNuevoEnLaEmpresa(int numEmpAComprobar) {
		boolean esNuevoEnLaEmpresa = true;
		
		try {
			Connection con = connect();
			String accionSQL = "select 'Ya hay un empleado con ese ´´numemp``' from "
					+ "empleados where numemp = ? limit 1";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, numEmpAComprobar);
			
			ResultSet rs = sentPrep.executeQuery();
			
			if(rs.next()) {
				esNuevoEnLaEmpresa = false;
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Comprobación del ´´numemp`` del empleado fallida.");
			excep.printStackTrace();
		}
		
		return esNuevoEnLaEmpresa;
	}
	
	public static Empleado selecEmpleadoConEsteNumEmp(int numemp) {
		Empleado empleadoConsultado = null;
		
		try {
			Connection con = connect();
			String accionSQL = "Select * from empleados where numemp = ?";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, numemp);
			
			ResultSet rs = sentPrep.executeQuery();
			
			if(rs.next()) {
				// Aquí iría el int numemp, pero es precisamente lo que
				// hemos introducido como parámetro
				String nombre = rs.getString("nombre");
				int edad = rs.getInt("edad");
				int oficina = rs.getInt("oficina");
				String puesto = rs.getString("puesto");
				Date contrato = rs.getDate("contrato");
				
				empleadoConsultado = new Empleado(numemp,nombre,edad,oficina,puesto,contrato);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta del empleado fallida.");
			excep.getStackTrace();
		}
		
		return empleadoConsultado;
	}
	
	public static ArrayList<Empleado> selecEmpleadosDeEstaOficina(int oficinaIntrod) {
		ArrayList<Empleado> listaEmpleadosDeEstaOficina = new ArrayList<Empleado>();
		Empleado empleadoConsultado = null;
		
		try {
			Connection con = connect();
			String accionSQL = "Select * from empleados where oficina = ?";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, oficinaIntrod);
			
			ResultSet rs = sentPrep.executeQuery();
			
			while(rs.next()) {
				int numemp = rs.getInt("numemp");
				String nombre = rs.getString("nombre");
				int edad = rs.getInt("edad");
				int oficina = rs.getInt("oficina");
				String puesto = rs.getString("puesto");
				Date contrato = rs.getDate("contrato");
				
				empleadoConsultado = new Empleado(numemp,nombre,edad,oficina,puesto,contrato);
				
				listaEmpleadosDeEstaOficina.add(empleadoConsultado);
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta del empleado fallida.");
			excep.getStackTrace();
		}
		
		return listaEmpleadosDeEstaOficina;
	}

	public static ArrayList<Integer> obtenerLosNumEmpDeLosEmpleadosDeEstaOficina(int oficinaOrig) {
		ArrayList<Integer> listaNumEmpDeLosEmpleadosDeEstaOficina = new ArrayList<Integer>();
		
		try {
			Connection con = connect();
			String accionSQL = "select numemp from empleados where oficina = ?";
			
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, oficinaOrig);
			
			ResultSet rs = sentPrep.executeQuery();
			
			while(rs.next()) {
				int numemp = rs.getInt("numemp");
				
				listaNumEmpDeLosEmpleadosDeEstaOficina.add(numemp);
			}
		} catch(SQLException excep) {
			System.out.println("Consulta de los ´´numemp`` de los empleados fallida.");
			excep.getStackTrace();
		}
		
		return listaNumEmpDeLosEmpleadosDeEstaOficina;
	}
	
	public static void actualizEmpleadoConEsteNumEmp(int numempEmpleado, Empleado datosNuevosDelEmpleado){
		if(datosNuevosDelEmpleado != null) {
			try {
				Connection con = connect();
				String accionSQL = "update Empleados set numemp = ?, "
						+ "nombre = ?, edad = ?, oficina = ?, "
						+ "puesto = ?, contrato = ?";
				
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setInt(1, datosNuevosDelEmpleado.getNumemp());
				sentPrep.setString(2, datosNuevosDelEmpleado.getNombre());
				sentPrep.setInt(3, datosNuevosDelEmpleado.getEdad());
				sentPrep.setInt(4, datosNuevosDelEmpleado.getOficina());
				sentPrep.setString(5, datosNuevosDelEmpleado.getPuesto());
				sentPrep.setDate(6, datosNuevosDelEmpleado.getContrato());
				
				sentPrep.executeUpdate();
				
				con.close();
			} catch(SQLException excep) {
				System.out.println("Actualización del empleado fallida.");
				excep.printStackTrace();
			}
		}
	}
	
	public static boolean cambiarEmpleadosDeUnaOficinaAOtra(int oficinaOrig, int oficinaNueva) {
		boolean oficinaCambiada = false;
		
		if(OficinaDAO.esaOficinaExiste(oficinaOrig) &&
				OficinaDAO.esaOficinaExiste(oficinaNueva)) {
			try {
				Connection con = connect();
				String accionSQL = "update empleados set oficina = ? where oficina = ?";
				
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);

				sentPrep.setInt(1, oficinaNueva);
				sentPrep.setInt(2, oficinaOrig);
				
				sentPrep.executeUpdate();
				
				con.close();
			} catch(SQLException excep) {
				System.out.println("Cambio de las oficinas de los empleados fallida.");
			}
		}
		
		return oficinaCambiada;
	}
	
}
