package ejcsPersistenciaBBDDSiEv00ClasesParaLaBaseDeDatos;

public class Oficina {

    private int oficina;
    private String ciudad;
    private int superficie;
    private double ventas;

    public Oficina(){ }

    public Oficina(int oficina, String ciudad, int superficie, double ventas) {
        this.oficina = oficina;
        this.ciudad = ciudad;
        this.superficie = superficie;
        this.ventas = ventas;
    }

    public int getOficina() {
        return oficina;
    }

    public void setOficina(int oficina) {
        this.oficina = oficina;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return  "Oficina = " + oficina +
                "\nCiudad = " + ciudad +
                "\nSuperficie = " + superficie +
                "\nVentas = " + ventas;
    }
}
