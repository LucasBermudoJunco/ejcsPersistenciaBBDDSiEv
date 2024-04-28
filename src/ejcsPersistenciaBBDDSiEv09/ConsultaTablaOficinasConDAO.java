package ejcsPersistenciaBBDDSiEv09;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.Oficina;
import ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos.OficinaDAO;

public class ConsultaTablaOficinasConDAO {
	
	public static void main(String[] args) {
		
		// Obtención de todas las oficinas
		ArrayList<Oficina> listaDeOficinas = OficinaDAO.read();
		
		// Comprobación de si hay por lo menos 1 oficina
		if(!listaDeOficinas.isEmpty()) {
			Scanner sc = new Scanner(System.in);
			
			int superficieIntrod = 0;
			boolean datoIntrodValido;
			
			// Visualización de todas las oficinas
			
			System.out.println("Las oficinas de la empresa son:\n");
			
			Iterator<Oficina> iteradorDeOficinas = listaDeOficinas.iterator();
			
			while(iteradorDeOficinas.hasNext()) {
				Oficina estaOficina = iteradorDeOficinas.next();
				
				System.out.println(estaOficina);
				
				if(iteradorDeOficinas.hasNext()) {
					System.out.println();
				}
			}
			
			// Filtrado de las Oficinas por superficie mayor a una determinada superficie
			System.out.print("\n\nIntroduce una superficie para que se muestren las oficinas "
					+ "con superficie mayor a la introducida:  ");
			do {
				datoIntrodValido = true;
				
				try {
					superficieIntrod = sc.nextInt();
					
					if(superficieIntrod <= 0) {
						datoIntrodValido = false;
						
						System.out.print("\nLa superficie introducida tiene que ser mayor que 0."
								+ "\n\nVuelva a introducir la superficie:  ");
					}
				} catch(InputMismatchException excep) {
					datoIntrodValido = false;
					
					System.out.print("\nEl valor introducido tiene que ser un número entero."
							+ "\n\nVuelva a introducir la superficie:  ");
				}
			} while(!datoIntrodValido);
			
			ArrayList<Oficina> listaOficinasConSuperfMayor = 
					OficinaDAO.selecOficinasConSuperfiMayorA(superficieIntrod);
			
			if(!listaOficinasConSuperfMayor.isEmpty()) {
				System.out.println("\n\nLa(s) oficina(s) con superficie mayor a "
						+ superficieIntrod + " es/son:\n");
				
				iteradorDeOficinas = listaOficinasConSuperfMayor.iterator();
				
				while(iteradorDeOficinas.hasNext()) {
					Oficina estaOficina = iteradorDeOficinas.next();
					
					System.out.println(estaOficina);
					
					if(iteradorDeOficinas.hasNext()) {
						System.out.println();
					}
				}
			} else {
				System.out.println("\n\nNo hay ninguna oficina con superficie mayor a "
						+ superficieIntrod + ".");
			}
			
			sc.close();
		} else {
			System.out.println("No hay oficinas en la empresa, por lo que "
					+ "no se puede seleccionar ninguna oficina.");
		}
	}
	
}
