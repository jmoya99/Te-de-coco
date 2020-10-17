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
public class Rol {
    
    private String nombre;
    private HashMap<String, Metodo> metodos;
    public static HashMap<String, Rol> roles = new HashMap<>();

    public Rol(String nombre) {
        this.nombre = nombre;
        this.metodos = new HashMap<>();
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

    public static HashMap<String, Rol> getRoles() {
        return roles;
    }

    public static void setRoles(HashMap<String, Rol> roles) {
        Rol.roles = roles;
    }
    
    public void addMetodo(Metodo metodo){
        if(!this.metodos.containsKey(metodo.getNombre())){
            this.metodos.put(metodo.getNombre(), metodo);
        }   
    }
    
    public static Rol addRol(Rol rol){
        if(!roles.containsKey(rol.getNombre())){
            roles.put(rol.getNombre(), rol);
            return rol;
        }
        return roles.get(rol.getNombre());
    }

    @Override
    public String toString() {
        return "Rol{" + "nombre=" + nombre + '}';
    }
}
