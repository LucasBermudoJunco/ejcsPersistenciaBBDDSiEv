package ejcsPersistenciaBBDDSiEv10;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.Oficina;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.OficinaDAO;

public class ModificacionCiudadEIncrementoVentasDeOficinas {

	public static void main(String[] args) {
		
		ArrayList<Oficina> listaDeOficinas = OficinaDAO.obtenerArrayListDeTodasLasOficinas();
		
		if(!listaDeOficinas.isEmpty()) {
			Scanner sc = new Scanner(System.in);
			
			int oficina = 0, incrementoVentas = 0;
			String ciudad = "";
			boolean datoIntrodValido;
			
			// Modificación de la ciudad
			System.out.print("Introduce el código de la oficina que quieras mover de ciudad:  ");
			do {
				datoIntrodValido = true;
				
				try {
					oficina = sc.nextInt();
					
					if(OficinaDAO.hayYaUnaOficinaConEseCodigo(oficina)) {
						System.out.println("\nOficina válida.");
					} else {
						datoIntrodValido = false;
					
						System.out.print("\nNo hay ninguna oficia con ese número de identificación."
							+ "\n\nVuelve a introducir otro número que sí pertenezca a alguna oficina:  ");						
					}
				} catch(InputMismatchException excep) {
					datoIntrodValido = false;
					
					System.out.print("\nEl valor introducido tiene que ser un número entero."
							+ "\n\nVuelve a introducirlo:  ");
				}
				
				sc.nextLine();
			} while(!datoIntrodValido);
			
			System.out.print("\nIntroduce la nueva ciudad para esa oficina:  ");
			do {
				datoIntrodValido = true;
				
				ciudad = sc.nextLine();
				
				if(ciudad.isEmpty()) {
					datoIntrodValido = false;
					
					System.out.print("\nEl nombre de la ciudad no puede estar vacío."
							+ "\n\nVuelve a introducirlo:  ");
				}
			} while(!datoIntrodValido);
		
			OficinaDAO.modificarCiudad(oficina, ciudad);
			
			System.out.println("\nLos datos de las oficinas, con la oficina " + oficina + " cambiada a "
					+ ciudad + ", son:\n");
			OficinaDAO.mostrarTodasEstasOficinasEnFormatoDeBBDD(OficinaDAO.obtenerArrayListDeTodasLasOficinas());
			
			// Modificación de las ventas
			System.out.print("\nIntroduce el código de la oficina que quieras aumentar sus ventas:  ");
			do {
				datoIntrodValido = true;
				
				try {
					oficina = sc.nextInt();
					
					if(OficinaDAO.hayYaUnaOficinaConEseCodigo(oficina)) {
						System.out.println("\nOficina válida.");
					} else {
						datoIntrodValido = false;
					
						System.out.print("\nNo hay ninguna oficia con ese número de identificación."
							+ "\n\nVuelve a introducir otro número que sí pertenezca a alguna oficina:  ");						
					}
				} catch(InputMismatchException excep) {
					datoIntrodValido = false;
					
					System.out.print("\nEl valor introducido tiene que ser un número entero."
							+ "\n\nVuelve a introducirlo:  ");
				}
				
				sc.nextLine();
			} while(!datoIntrodValido);
			
			System.out.print("\nIntroduce el aumento de ventas para esa oficina (para una pérdida de ventas, "
					+ "\nintroduce un aumento negativo):  ");
			do {
				datoIntrodValido = true;
				
				try {
					incrementoVentas = sc.nextInt();
				} catch(InputMismatchException excep) {
					datoIntrodValido = false;
					
					System.out.print("\nEl valor introducido tiene que ser un número entero."
							+ "\n\nVuelve a introducirlo:  ");
				}
				
				sc.nextLine();
			} while(!datoIntrodValido);		
			
			OficinaDAO.incrementarVentas(oficina, incrementoVentas);
			
			if(incrementoVentas > 0) {
			System.out.println("\nLos datos de las oficinas, con las ventas de la oficina " + oficina
					+ " incrementadas en " + incrementoVentas + ", son:\n");
			} else if(incrementoVentas == 0) {
				System.out.println("\nLos datos de las oficinas, con las ventas de la oficina " + oficina
						+ " iguales, son:\n");
			} else {
				System.out.println("\nLos datos de las oficinas, con las ventas de la oficina " + oficina
						+ " disminuidas en " + (incrementoVentas * -1) + ", son:\n");
			}
			
			OficinaDAO.mostrarTodasEstasOficinasEnFormatoDeBBDD(OficinaDAO.obtenerArrayListDeTodasLasOficinas());
			
			sc.close();
		} else {
			System.out.println("No hay oficinas en la empresa, por lo que "
					+ "no se puede modificar los datos de ninguna oficina.");
		}
		
	}

}

