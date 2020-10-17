/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author CJ4
 */
public class Elemento {
    
    private int id;
    private String artefacto;
    private int origen;
    private int destino;
    private String contenido;

    public Elemento(int id, String artefacto, int origen, int destino, String contenido) {
        this.id = id;
        this.artefacto = artefacto;
        this.origen = origen;
        this.destino = destino;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtefacto() {
        return artefacto;
    }

    public void setArtefacto(String artefacto) {
        this.artefacto = artefacto;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Elemento{" + "id=" + id + ", artefacto=" + artefacto + ", origen=" + origen + ", destino=" + destino + ", contenido=" + contenido + '}';
    }
   
    
}
