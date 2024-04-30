package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class EmpleadoDAO {
	
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
	
	public static Empleado read(int numemp) {
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
	
	public static void update(Empleado empleadoIntrod){
		if(empleadoIntrod != null) {
			try {
				Connection con = connect();
				String accionSQL = "update Empleados set "
						+ "nombre = ?, edad = ?, oficina = ?, "
						+ "puesto = ?, contrato = ?"
						+ "where numemp = ?";
				
				PreparedStatement sentPrep = con.prepareStatement(accionSQL);
				
				sentPrep.setString(1, empleadoIntrod.getNombre());
				sentPrep.setInt(2, empleadoIntrod.getEdad());
				sentPrep.setInt(3, empleadoIntrod.getOficina());
				sentPrep.setString(4, empleadoIntrod.getPuesto());
				sentPrep.setDate(5, empleadoIntrod.getContrato());
				sentPrep.setInt(6, empleadoIntrod.getNumemp());
				
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
	
	// MÉTODOS ESPECÍFICOS
	
	public static String obtenerLaCabeceraDeTodosLosCamposDeLaTablaEmpleado() {
		return "NumEmp\tNombre\t\t\tEdad\tOficina\tPuesto\t\t\tContrato"
				+ "\n---------------------------------------------"
        		+ "---------------------------------------";
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
	
	public static String obtenerTodosLosEmpleadosDeLaTablaEmpleados() {
		String consultaTotal = "";
		
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
				
				String estaLineaDeLaConsulta = "";
                estaLineaDeLaConsulta += numemp + "\t";
                estaLineaDeLaConsulta += nombre;
                if(nombre.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(nombre.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += edad + "\t";
                estaLineaDeLaConsulta += oficina + "\t";
                estaLineaDeLaConsulta += puesto;
                if(puesto.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(puesto.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += contrato;
                
                consultaTotal += estaLineaDeLaConsulta;
                
                if(!rs.isLast()) {
                	consultaTotal += "\n";
                }
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta de los empleados fallida.");
			excep.getStackTrace();
		}
		
		return consultaTotal;
	}
	
	public static String obtenerTodosLosDatosDeEsteEmpleado(int numEmpIntrod) {
		String consultaTotal = "";
		
		try {
			Connection con = connect();
			String accionSQL = "Select * from empleados where numemp = ?";
			PreparedStatement sentPrep = con.prepareStatement(accionSQL);
			
			sentPrep.setInt(1, numEmpIntrod);
			
			ResultSet rs = sentPrep.executeQuery();
			
			if(rs.next()) {
				int numemp = rs.getInt("numemp");
				String nombre = rs.getString("nombre");
				int edad = rs.getInt("edad");
				int oficina = rs.getInt("oficina");
				String puesto = rs.getString("puesto");
				Date contrato = rs.getDate("contrato");
				
				String estaLineaDeLaConsulta = "";
                estaLineaDeLaConsulta += numemp + "\t";
                estaLineaDeLaConsulta += nombre;
                if(nombre.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(nombre.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += edad + "\t";
                estaLineaDeLaConsulta += oficina + "\t";
                estaLineaDeLaConsulta += puesto;
                if(puesto.length() < 8){
                    estaLineaDeLaConsulta += "\t\t\t";
                } else if(puesto.length() < 16){
                    estaLineaDeLaConsulta += "\t\t";
                } else{
                    estaLineaDeLaConsulta += "\t";
                }
                estaLineaDeLaConsulta += contrato;
                
                consultaTotal += estaLineaDeLaConsulta;
			}
			
			con.close();
		} catch(SQLException excep) {
			System.out.println("Consulta de los empleados fallida.");
			excep.getStackTrace();
		}
		
		return consultaTotal;
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
	
	public static String obtenerLosEmpleadosDeEstaOficina(int oficinaIntrod) {
		String texto = "";
		
		ArrayList<Empleado> listaEmpleadosDeEstaOficina = selecEmpleadosDeEstaOficina(oficinaIntrod);
		
		if(!listaEmpleadosDeEstaOficina.isEmpty()) {
            texto += "NumEmp\tNombre\t\t\tEdad\tOficina\tPuesto\t\t\tContrato\n";
			
			Iterator<Empleado> iteradorEmpleados = listaEmpleadosDeEstaOficina.iterator();
			
			while(iteradorEmpleados.hasNext()) {
				Empleado esteEmpleado = iteradorEmpleados.next();
				
				texto += esteEmpleado.getNumemp() + "\t";
				texto += esteEmpleado.getNombre();
                if(esteEmpleado.getNombre().length() < 8){
                	texto += "\t\t\t";
                } else if(esteEmpleado.getNombre().length() < 16){
                	texto += "\t\t";
                } else{
                	texto += "\t";
                }
                texto += esteEmpleado.getEdad() + "\t";
                texto += esteEmpleado.getOficina() + "\t";
                texto += esteEmpleado.getPuesto();
                if(esteEmpleado.getPuesto().length() < 8){
                	texto += "\t\t\t";
                } else if(esteEmpleado.getPuesto().length() < 16){
                	texto += "\t\t";
                } else{
                	texto += "\t";
                }
                texto += esteEmpleado.getContrato();
				
				if(iteradorEmpleados.hasNext()) {
					texto += "\n";
				}
			}
		}
		
		return texto;
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
		
		if(OficinaDAO.hayYaUnaOficinaConEseCodigo(oficinaOrig) &&
				OficinaDAO.hayYaUnaOficinaConEseCodigo(oficinaNueva)) {
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
