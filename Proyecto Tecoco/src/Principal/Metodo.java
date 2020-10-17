/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.HashMap;

/**
 *
 * @author CJ4
 */
public class Metodo {
    
    private String nombre;
    private HashMap<String,Atributo> parametros;
    private HashMap<String,Rol> roles;
    private Clase clase;

    public Metodo(String nombre, Clase clase) {
        this.nombre = nombre;
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
    
    public void addRol(Rol rol){
        if(!this.roles.containsKey(rol.getNombre())){
            this.roles.put(rol.getNombre(), rol);
        }
    }
    
    public Atributo addParametro(Atributo atributo){
        if(!this.parametros.containsKey(atributo.getNombre())){
            this.parametros.put(atributo.getNombre(), atributo);
            return atributo;
        }
        return this.parametros.get(atributo.getNombre());
    }

    @Override
    public String toString() {
        return "Metodo{" + "nombre=" + nombre + ", clase=" + clase.getNombre() + '}';
    }
    
    
}
