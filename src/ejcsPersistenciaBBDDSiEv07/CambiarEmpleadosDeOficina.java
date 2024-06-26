package ejcsPersistenciaBBDDSiEv07;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.EmpleadoDAO;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.OficinaDAO;

public class CambiarEmpleadosDeOficina {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int oficinaOrig = 0, oficinaNueva = 0;
		
		boolean datosIntrodValidos;
		
		// Introducción de las oficinas
		
		// Oficina original
		System.out.print("Introduce la oficina original de los empleados a los "
				+ "que quieras cambiar de oficina:  ");
		do {
			datosIntrodValidos = true;
			
			try {
				oficinaOrig = sc.nextInt();
				
				if(!OficinaDAO.hayYaUnaOficinaConEseCodigo(oficinaOrig)) {
					datosIntrodValidos = false;
					
					System.out.print("\nLa oficina introducida no está en la empresa."
							+ "\n\nIntroduzca de nuevo una oficina que esté en la empresa:  ");
				} else {
					System.out.println("\nNúmero de oficina válido.");
				}
			} catch(InputMismatchException excep) {
				datosIntrodValidos = false;
				sc.nextLine();
				
				System.out.print("\nEl código de la oficina tiene que ser un número entero."
						+ "\n\nVuelva a introducirlo:  ");
			}
		} while(!datosIntrodValidos);
		
		// Oficina nueva
		System.out.print("\nIntroduce la oficina nueva a la que los empleados de la otra oficina "
				+ "van a trasladarse:  ");
		do {
			datosIntrodValidos = true;
			
			try {
				oficinaNueva = sc.nextInt();
				
				if(oficinaNueva == oficinaOrig) {
					datosIntrodValidos = false;
					
					System.out.print("\nLa oficina nueva no puede ser la misma que la oficina original."
							+ "\n\nIntroduzca de nuevo una oficina que esté en la empresa:  ");
				} else if(!OficinaDAO.hayYaUnaOficinaConEseCodigo(oficinaNueva)) {
					datosIntrodValidos = false;
					
					System.out.print("\nLa oficina introducida no está en la empresa."
							+ "\n\nIntroduzca de nuevo una oficina que esté en la empresa:  ");
				} else {
					System.out.println("\nNúmero de oficina válido.");
				}
			} catch(InputMismatchException excep) {
				datosIntrodValidos = false;
				sc.nextLine();
				
				System.out.print("\nEl código de la oficina tiene que ser un número entero."
						+ "\n\nVuelva a introducirlo:  ");
			}
		} while(!datosIntrodValidos);
		
		// Visualización de los empleados de la ´´oficinaOrig``
		System.out.println("\nLos datos del/de los empleado(s) que estaba(n) en la oficina " + oficinaOrig + " son:\n");
		System.out.println(EmpleadoDAO.obtenerLaCabeceraDeTodosLosCamposDeLaTablaEmpleado() + "\n");
		System.out.println(EmpleadoDAO.obtenerLosEmpleadosDeEstaOficina(oficinaOrig));
		
		// Obtención del ´´numemp`` de los empleados de la ´´oficinaOrig``
		ArrayList<Integer> numEmpDeLosEmpleadosDeLaOficinaOrig = EmpleadoDAO.obtenerLosNumEmpDeLosEmpleadosDeEstaOficina(oficinaOrig);
		
		// Cambio de los empleados de la ´´oficinaOrig`` a la ´´oficinaNueva``
		EmpleadoDAO.cambiarEmpleadosDeUnaOficinaAOtra(oficinaOrig, oficinaNueva);
		
		// Visualización de los empleados cambiados a la ´´oficinaOrig``
		System.out.println("\n\n\nLos datos del/de los empleado(s) cambiado(s) a la oficina " + oficinaNueva + " son:\n");
		System.out.println(EmpleadoDAO.obtenerLaCabeceraDeTodosLosCamposDeLaTablaEmpleado() + "\n");
		System.out.println(EmpleadoDAO.obtenerTodosLosEmpleadosDeEstaListaDeEmpleados(numEmpDeLosEmpleadosDeLaOficinaOrig));
		
		
		sc.close();
	}

}
