package ejcsPersistenciaBBDDSiEv05y06;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.Empleado;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.EmpleadoDAO;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.OficinaDAO;

public class InsercionEmpleado {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Empleado trabajador;
		int numemp = 0, edad = 0, oficina = 0, anyoContrato = 0, mesContrato = 0, diaContrato = 0;
		String nombre, puesto;
		Date contrato = null;
		
		boolean datoIntrodValido;
		
		// Inserción de los datos del Empleado
		System.out.println("INSERCIÓN DE UN EMPLEADO EN LA BASE DE DATOS.");
		
		// Número de identificación	
		System.out.print("\nIntroduce el número de identificación del empleado:  ");
		do {
			datoIntrodValido = true;
			try {
				numemp = sc.nextInt();
				
				if(EmpleadoDAO.esNuevoEnLaEmpresa(numemp)) {
					System.out.println("\nNúmero de empleado válido. Continúe introduciendo "
							+ "los datos del empleado.");
				} else {
					datoIntrodValido = false;
					
					System.out.print("\nYa hay un empleado con ese número de identificación."
							+ "\n\nIntroduzca otro número de identificación:  ");
				}
			} catch(InputMismatchException excep) {
				datoIntrodValido = false;
				
				System.out.print("\nEl valor introducido tiene que ser un número entero."
						+ "\n\nVuelva a introducir el número de identificación:  ");
			}
			
			sc.nextLine();
		} while(!datoIntrodValido);
		
		// Nombre
		System.out.print("\nIntroduce el nombre del empleado:  ");
		do {
			datoIntrodValido = true;
			
			nombre = sc.nextLine();
			
			if(nombre.isEmpty()) {
				datoIntrodValido = false;
				
				System.out.print("\nEl nombre no puede estar vacío."
						+ "\n\nVuelva a introducir el nombre:  ");
			}
		} while(!datoIntrodValido);
		
		// Edad
		System.out.print("\nIntroduce la edad del empleado:  ");
		do {
			datoIntrodValido = true;
			try {
				edad = sc.nextInt();
				
				if(edad < 16) {
					datoIntrodValido = false;
					
					System.out.print("\nLos empleados no pueden tener menos de 16 años."
							+ "\n\nIntroduzca otra edad del empleado:  ");
				}
			} catch(InputMismatchException excep) {
				datoIntrodValido = false;
				sc.nextLine();
				
				System.out.print("\nEl valor introducido tiene que ser un número entero."
						+ "\n\nVuelva a introducir la edad del empleado:  ");
			}
		} while(!datoIntrodValido);
		
		// Oficina
		System.out.print("\nIntroduce el número de la oficina en la que trabaja el empleado:  ");
		do {
			datoIntrodValido = true;
			try {
				oficina = sc.nextInt();
				
				if(oficina < 1) {
					datoIntrodValido = false;
					
					System.out.print("\nEl número de identificación de la oficina tiene que ser mayor que 0."
							+ "\n\nIntroduzca un número de oficina mayor que 0:  ");
				} else if(!OficinaDAO.esaOficinaExiste(oficina)) {
					datoIntrodValido = false;
					
					System.out.print("\nNo hay ninguna oficina con ese número de identificación."
							+ "\n\nIntroduzca otro número de oficina que sí exista:  ");
				} else {
					System.out.println("\nNúmero de oficina válido. Continúe introduciendo "
							+ "los datos del empleado.");
				}
			} catch(InputMismatchException excep) {
				datoIntrodValido = false;
				
				System.out.print("\nEl valor introducido tiene que ser un número entero."
						+ "\n\nVuelva a introducir el número de identificación:  ");
			}

			sc.nextLine();
		} while(!datoIntrodValido);
		
		// Puesto
		System.out.print("\nIntroduce el puesto del empleado:  ");
		do {
			datoIntrodValido = true;
			
			puesto = sc.nextLine();
			
			if(puesto.isEmpty()) {
				datoIntrodValido = false;
				
				System.out.print("\nEl puesto no puede estar vacío."
						+ "\n\nVuelva a introducir el puesto:  ");
			}
		} while(!datoIntrodValido);
		
		// Contrato
		System.out.println("\nIntroduce la fecha del contrato del empleado.");
		do {
			datoIntrodValido = true;
			
			try {
				System.out.print("\nDía:  ");
				diaContrato = sc.nextInt();

				System.out.print("Mes:  ");
				mesContrato = sc.nextInt();
				System.out.print("Año:  ");
				anyoContrato = sc.nextInt();
				
				LocalDate fechaLocalDate = LocalDate.of(anyoContrato, mesContrato, diaContrato);
				
				contrato = Date.valueOf(fechaLocalDate);
			} catch(InputMismatchException excep) {
				datoIntrodValido = false;
				sc.nextInt();
				
				System.out.println("\nLos datos introducidos tienen que ser números enteros."
						+ "\nVuelva a introducir la fecha.");
			} catch(DateTimeException excep) {
				datoIntrodValido = false;
				
				System.out.println("\nLa fecha tiene que pertenecer al calendario gregoriano."
						+ "\nVuelva a introducir una fecha válida");
			}
		} while(!datoIntrodValido);
		
		// Creación del Empleado con los datos introducidos
		trabajador = new Empleado(numemp,nombre,edad,oficina,puesto,contrato);
		
		// Inserción del empleado en la base de datos
		EmpleadoDAO.create(trabajador);
		
		// Comprobación de que el empleado ha sido insertado correctamente en la base de datos
		if(EmpleadoDAO.selecEmpleadoConEsteNumEmp(trabajador.getNumemp()) != null) {
			System.out.println("\nEl empleado ha sido insertado correctamente");
			
			Empleado empleadoConsultado = EmpleadoDAO.selecEmpleadoConEsteNumEmp(trabajador.getNumemp());
			
			System.out.println("\nLos datos del empleado, tomados de la base de datos, son:\n\n"
					+ empleadoConsultado);
		}
		
		sc.close();
	}

}
