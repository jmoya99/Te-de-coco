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
public class Clase {
    
    private String nombre;
    private HashMap<String,Metodo> metodos = new HashMap<>();
    private HashMap<String, Atributo> atributos = new HashMap<>();
    public static HashMap<String, Clase> clases = new HashMap<>();

    public Clase(String nombre) {
        this.nombre = nombre;
        this.metodos = new HashMap<>();
        this.atributos = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(HashMap<String, Metodo> metodos) {
        this.metodos = metodos;
    }

    public HashMap<String, Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(HashMap<String, Atributo> atributos) {
        this.atributos = atributos;
    }

    public static HashMap<String, Clase> getClases() {
        return clases;
    }

    public static void setClases(HashMap<String, Clase> clases) {
        Clase.clases = clases;
    }
    
    public Metodo addMetodo(Metodo metodo){
        if(!this.metodos.containsKey(metodo.getNombre())){
            this.metodos.put(metodo.getNombre(), metodo);
            return metodo;
        }
        return this.metodos.get(metodo.getNombre());
    }
    
    public Atributo addAtributo(Atributo atributo){
        if(!this.atributos.containsKey(atributo.getNombre())){
            this.atributos.put(atributo.getNombre(), atributo);
            return atributo;
        }
        return this.atributos.get(atributo.getNombre());
    }
    
    public static Clase addClase(Clase clase){
        if(!clases.containsKey(clase.getNombre())){
            clases.put(clase.getNombre(), clase);
            return clase;
        }
        return clases.get(clase.getNombre());
    }

    @Override
    public String toString() {
        return "Clase{" + "nombre=" + nombre +'}';
    }
    
}