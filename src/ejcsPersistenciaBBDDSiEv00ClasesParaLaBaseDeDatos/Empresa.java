package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

import java.util.*;

public class Empresa {

    private String id;
    private String nombre;
    private HashMap<Integer,Oficina> listaOficinas;

    public Empresa(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        listaOficinas = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<Integer, Oficina> getListaOficinas() {
        return listaOficinas;
    }

    public void setListaOficinas(List<Oficina> listaOficinas) {
        this.listaOficinas = new HashMap<>();

        Iterator<Oficina> iteradorDeOficinas = listaOficinas.iterator();

        while(iteradorDeOficinas.hasNext()){
            Oficina estaOficina = iteradorDeOficinas.next();

            this.listaOficinas.put(estaOficina.getOficina(), estaOficina);
        }
    }

    public void setListaOficinas(Set<Oficina> listaOficinas) {
        this.listaOficinas = new HashMap<>();

        Iterator<Oficina> iteradorDeOficinas = listaOficinas.iterator();

        while(iteradorDeOficinas.hasNext()){
            Oficina estaOficina = iteradorDeOficinas.next();

            this.listaOficinas.put(estaOficina.getOficina(), estaOficina);
        }
    }

    @Override
    public String toString() {
        String texto = "ID = " + id;
        texto += "\nNombre = " + nombre;
        texto += "\nLista de Oficinas =";
        int contador = 0;
        for(Map.Entry<Integer,Oficina> entrada : listaOficinas.entrySet()){
            Oficina estaOficina = entrada.getValue();

            contador++;

            texto += "\n\n" + contador + "ª oficina:\n" + estaOficina;
        }

        return  texto;
    }

    public void anyadirOficina(Oficina oficina) {
        listaOficinas.put(oficina.getOficina(),oficina);
    }

}
