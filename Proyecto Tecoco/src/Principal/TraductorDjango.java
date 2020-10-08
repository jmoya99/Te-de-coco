/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author juanpma
 */
public class TraductorDjango {
    
    public static void generarModelo() {
        String codigoModelo = "from django.db import models";
        for (Clase clase : Clase.clases.values()) {
            if (clase.getAtributos().isEmpty()) {
                continue;
            }
            codigoModelo += "\n";
            codigoModelo += "class " + clase.getNombre() + "(models.Model):";
            for (Atributo atributo : clase.getAtributos().values()) {
                codigoModelo += "\n\t"+atributo.getNombre()+" = models.CharField(max_length=200";
                if (atributo.isIsPrimary()) {
                    codigoModelo += ",primary_key=True";
                }
                codigoModelo += ")";
            }
        }
        System.out.println(codigoModelo);//mientras no esta el archivo, al metodo le falta
    }
    
}
