package ejcsPersistenciaBBDDSiEv07;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.Empleado;
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
				
				if(!OficinaDAO.esaOficinaExiste(oficinaOrig)) {
					datosIntrodValidos = false;
					
					System.out.print("\nLa oficina introducida no está en la empresa."
							+ "\n\nIntroduzca de nuevo una oficina que esté en la empresa:  ");
				}
			} catch(InputMismatchException excep) {
				datosIntrodValidos = false;
				sc.nextLine();
				
				System.out.print("\nEl código de la oficina tiene que ser un número entero."
						+ "\n\nVuelva a introducirlo:  ");
			}
		} while(!datosIntrodValidos);
		
		// Oficina original
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
				} else if(!OficinaDAO.esaOficinaExiste(oficinaNueva)) {
					datosIntrodValidos = false;
					
					System.out.print("\nLa oficina introducida no está en la empresa."
							+ "\n\nIntroduzca de nuevo una oficina que esté en la empresa:  ");
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
		ArrayList<Empleado> listaEmpleadosDeLaOficinaOrig = EmpleadoDAO.selecEmpleadosDeEstaOficina(oficinaOrig);
		
		Iterator<Empleado> iteradorDeLosEmpleadosDeLaOficinaOrig = listaEmpleadosDeLaOficinaOrig.iterator();
		
		while(iteradorDeLosEmpleadosDeLaOficinaOrig.hasNext()) {
			Empleado esteEmpleado = iteradorDeLosEmpleadosDeLaOficinaOrig.next();
			
			System.out.println(esteEmpleado);
			
			if(iteradorDeLosEmpleadosDeLaOficinaOrig.hasNext()) {
				System.out.println();
			}
		}
		
		// Obtención del ´´numemp`` de los empleados de la ´´oficinaOrig``
		ArrayList<Integer> numEmpDeLosEmpleadosDeLaOficinaOrig = EmpleadoDAO.obtenerLosNumEmpDeLosEmpleadosDeEstaOficina(oficinaOrig);
		
		// Cambio de los empleados de la ´´oficinaOrig`` a la ´´oficinaNueva``
		EmpleadoDAO.cambiarEmpleadosDeUnaOficinaAOtra(oficinaOrig, oficinaNueva);
		
		// Visualización de los empleados cambiados a la ´´oficinaOrig``
		System.out.println("\nLos datos del/de los empleado(s) cambiado(s) a la oficina " + oficinaNueva + " son:\n");
		
		Iterator<Integer> iteradorDeLosNumEmpDeLosEmpleadosCambiadosALaOficinaNueva = numEmpDeLosEmpleadosDeLaOficinaOrig.iterator();
		
		while(iteradorDeLosNumEmpDeLosEmpleadosCambiadosALaOficinaNueva.hasNext()) {
			Integer esteNumEmp = iteradorDeLosNumEmpDeLosEmpleadosCambiadosALaOficinaNueva.next();
			
			Empleado esteEmpleado = EmpleadoDAO.selecEmpleadoConEsteNumEmp(esteNumEmp);
			
			System.out.println(esteEmpleado);
			
			if(iteradorDeLosNumEmpDeLosEmpleadosCambiadosALaOficinaNueva.hasNext()) {
				System.out.println();
			}
		}
		
		sc.close();
		
	}

}
