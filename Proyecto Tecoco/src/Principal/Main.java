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
        HashMap<Integer,Elemento> csv = CSV.leerArchivo();
        for (Elemento fila : csv.values()) {
            switch (fila.getArtefacto()){
                case "Tarea":
                    String tarea[] = fila.getContenido().split(" ");
                    
                    Rol rol = Rol.addRol(new Rol(tarea[0]));
                    
                    Clase clase = Clase.addClase(new Clase(tarea[2]));
                    
                    Metodo metodo = Metodo.addMetodo(new Metodo(tarea[1], clase),
                            fila.getId());
                    
                    clase.addMetodo(metodo);
                    
                    metodo.addRol(rol);
                    
                    rol.addMetodo(metodo);
                    
                    break;
                //case "Objeto de datos":
                //    String tarea[] = fila.getContenido();
                //    break;
            }
        }
        
        for (Metodo m : Metodo.metodos.values()) {
            System.out.println(m.getNombre());
            for (String string : m.getRoles().keySet()) {
                System.out.println(string);
            }
        }
        
        //System.out.println(new HashMap<Integer, Integer>());
    }
}
