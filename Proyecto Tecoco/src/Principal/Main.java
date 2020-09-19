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
public class Main {

    /**
     * @param args the command line arguments
     */
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
            System.out.println(fila);
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
            } 
        }
    }
}
