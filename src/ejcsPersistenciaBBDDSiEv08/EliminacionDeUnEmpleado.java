package ejcsPersistenciaBBDDSiEv08;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.Empleado;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.EmpleadoDAO;

public class EliminacionDeUnEmpleado {
	
	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		
		int numemp = 0;
		boolean datoIntrodValido, elEmpleadoEstaEnLaEmpresa = true;
		
		// Visualización de la tabla Empleados en su estado inicial
		System.out.println("Empleados de la empresa:\n");
		
		ArrayList<Empleado> listaEmpleados = EmpleadoDAO.read();
		
		Iterator<Empleado> iteradorDeLosEmpleados = listaEmpleados.iterator();
		
		while(iteradorDeLosEmpleados.hasNext()) {
			Empleado esteEmpleado = iteradorDeLosEmpleados.next();
			
			System.out.println(esteEmpleado);
			
			if(iteradorDeLosEmpleados.hasNext()) {
				System.out.println();
			}
		}
		
		System.out.println();
		
		// Introducción del empleado a borrar
		System.out.print("\nIntroduce el número del empleado que quieras eliminar "
				+ "de la empresa:  ");
		do {
			datoIntrodValido = true;
			try {
				numemp = sc.nextInt();
				
				if(EmpleadoDAO.esNuevoEnLaEmpresa(numemp)) {
					elEmpleadoEstaEnLaEmpresa = false;
					
					System.out.println("\nNo hay ningún empleado con ese número en la empresa, "
							+ "por lo que\nno se puede realizar la eliminación del empleado "
							+ "con ese número de identificación.");
				}
			} catch(InputMismatchException excep) {
				datoIntrodValido = false;
				sc.nextLine();
				
				System.out.print("\nEl valor introducido tiene que ser un número entero."
						+ "\n\nVuelva a introducirlo:  ");
			}
		}while(!datoIntrodValido);
		
		// Eliminación
		if(elEmpleadoEstaEnLaEmpresa) {
			EmpleadoDAO.delete(numemp);
			
			// Visualización

			System.out.println("\n\n\nEmpleados de la empresa tras la eliminación del empleado "
					+ "con número ´´" + numemp + "``:\n");
			
			listaEmpleados = EmpleadoDAO.read();
			
			iteradorDeLosEmpleados = listaEmpleados.iterator();
			
			while(iteradorDeLosEmpleados.hasNext()) {
				Empleado esteEmpleado = iteradorDeLosEmpleados.next();
				
				System.out.println(esteEmpleado);
				
				if(iteradorDeLosEmpleados.hasNext()) {
					System.out.println();
				}
			}
		}
		
		sc.close();
	}

}