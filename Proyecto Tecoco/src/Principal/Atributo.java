/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.HashMap;

/**
 *
 * @author juan
 */
public class Atributo {
    
    private String nombre;
    private Clase clase;
    private boolean isPrimary = false;

    public Atributo(String nombre, Clase clase) {
        this.nombre = nombre;
        this.clase = clase;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    @Override
    public String toString() {
        return "Atributo{" + "nombre=" + nombre + ", clase=" + clase + ", isPrimary=" + isPrimary + '}';
    }
    
    
    
}
