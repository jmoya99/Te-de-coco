/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.HashMap;

/**
 *
 * @author cj4
 */
public class Main {

    public static Metodo buscarMetodo(Elemento elemento, HashMap<Integer, Elemento> csv){
        Metodo metodo;
        for (Elemento e : csv.values()) {
            if(elemento.getId() == e.getOrigen()){
                String tarea[] = csv.get(e.getDestino()).getContenido().split(" ");

                Clase clase = Clase.addClase(new Clase(tarea[2]));

                metodo = clase.addMetodo(new Metodo(tarea[1], clase));
                return metodo;
            }
            
        }
        return null;
    }
    
    public static void main(String[] args) {
        HashMap<Integer, Elemento> csv = CSV.leerArchivo();
        for (Elemento fila : csv.values()) {
            if (fila.getArtefacto().equals("Tarea")) {
                String tarea[] = fila.getContenido().split(" ");

                Rol rol = Rol.addRol(new Rol(tarea[0]));

                Clase clase = Clase.addClase(new Clase(tarea[2]));

                Metodo metodo = clase.addMetodo(new Metodo(tarea[1], clase));

                metodo.addRol(rol);

                rol.addMetodo(metodo);
            }
        }

        
        for (Elemento fila : csv.values()) {
            if (fila.getArtefacto().equals("Almacén de datos")) {
                String almacen[] = fila.getContenido().split(" ");
                Clase clase = Clase.addClase(new Clase(almacen[0]));
                for(int i = 1; i < almacen.length; i++){
                    if(almacen[i].charAt(0) == '#'){
                        Atributo atributo = clase.addAtributo(new Atributo(almacen[i].substring(1), clase));
                        atributo.setIsPrimary(true);
                    } else {
                        clase.addAtributo(new Atributo(almacen[i], clase));
                    }
                }
            } else if (fila.getArtefacto().equals("Objeto de datos")) {
                Atributo atributo = new Atributo(fila.getContenido(), null);
                Metodo metodo = buscarMetodo(fila, csv);
                metodo.addParametro(atributo);
            }
        }
        
    }
}
