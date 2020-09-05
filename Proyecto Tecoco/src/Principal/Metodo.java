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
public class Metodo {
    
    private String nombre;
    private String contenido;
    private HashMap<String,Atributo> parametros;
    private HashMap<String,Rol> roles;
    private Clase clase;
    public static HashMap<Integer, Metodo> metodos = new HashMap<>();

    public Metodo(String nombre, Clase clase) {
        this.nombre = nombre;
        this.contenido = "";
        this.parametros = new HashMap<>();
        this.roles = new HashMap<>();
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public HashMap<String, Atributo> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, Atributo> parametros) {
        this.parametros = parametros;
    }

    public HashMap<String, Rol> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<String, Rol> roles) {
        this.roles = roles;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public static HashMap<Integer, Metodo> getMetodos() {
        return metodos;
    }

    public static void setMetodos(HashMap<Integer, Metodo> metodos) {
        Metodo.metodos = metodos;
    }
    
    public void addRol(Rol rol){
        if(!this.roles.containsKey(rol.getNombre())){
            this.roles.put(rol.getNombre(), rol);
        }
    }
    
    public void addParametro(Atributo atributo){
        if(!this.parametros.containsKey(atributo.getNombre())){
            this.parametros.put(atributo.getNombre(), atributo);
        }
    }
    
    public static Metodo addMetodo(Metodo metodo, int id){
        if(!metodos.containsKey(id)){
            metodos.put(id, metodo);
            return metodo;
        }
        return metodos.get(id);
    }

    @Override
    public String toString() {
        return "Metodo{" + "nombre=" + nombre + ", contenido=" + contenido + ", parametros=" + parametros + ", roles=" + roles + ", clase=" + clase.getNombre() + '}';
    }
    
    
}
