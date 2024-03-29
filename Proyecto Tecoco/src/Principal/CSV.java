/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CJ4
 */
public class CSV {

    //Recuperado de http://chuwiki.chuidiang.org/index.php?title=Leer_fichero_CSV_con_Java
    public static HashMap<Integer, Elemento> leerArchivo(File archivo) {
        int id, origen, destino;
        String artefacto, contenido;
        HashMap<Integer, Elemento> elementos = new HashMap<>();
        String separador = ",";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(archivo));
            String line = br.readLine();
            line = br.readLine();
            while (null != line) {
                String[] fields = line.split(separador);
                if (!fields[1].equals("Página")) {
                    id = Integer.parseInt(fields[0]);
                    artefacto = fields[1];
                    origen = Integer.parseInt((!fields[6].equals("") ? fields[6] : "0"));
                    destino = Integer.parseInt((!fields[7].equals("") ? fields[7] : "0"));
                    contenido = "";
                    if (fields.length == 11) {
                        contenido = fields[10];
                    } else if (fields.length == 12) {
                        contenido = fields[11];
                    }
                    elementos.put(id, new Elemento(id, artefacto, origen, destino, contenido.toLowerCase()));
                    
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return elementos;
    }
}
