/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author juanpma
 */
public class TraductorDjango {
    
    public static String d = "Resultado/";
    public static String nombreP;
    
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
    
    public static void generarView() {
        String codigoView = "from django.shortcuts import render, redirect\n" +
                        "from .models import *\n" +
                        "from django.contrib import messages\n" +
                        "from django.contrib.auth import authenticate\n" +
                        "from django.contrib.auth.forms import AuthenticationForm\n" +
                        "from django.contrib.auth import login as do_login\n" +
                        "from django.contrib.auth import logout as do_logout\n\n";
        
        for (Clase clase : Clase.getClases().values()) {
            // Todos las vistas de registrar
            if (clase.getMetodos().containsKey("registra")){
                for (Rol rol : clase.getMetodos().get("registra").getRoles().values()) {
                    codigoView += "def " + rol.getNombre() + "registra" + clase.getNombre() + 
                                "(request):\n" +
                            "\tif request.session['rol'] is None:\n" +
                            "\t\tmessages.warning(request,'Por favor inicie sesion')\n" +
                            "\t\treturn redirect('index')\n" +
                            "\telif request.session['rol'] != \"" + rol.getNombre() + "\":\n" +
                            "\t\tmessages.warning(request,'Inicie sesion como " + 
                                rol.getNombre() + "')\n" +
                            "\t\treturn redirect('index')\n" +
                            "\tif request.method == 'POST':\n" + 
                            "\t\tpe = " + clase.getNombre() + "()\n";
                    
                    for (Atributo atributo : clase.getAtributos().values()) {
                        codigoView += "\t\tpe." + atributo.getNombre() + " = " +
                                "request.POST['" + atributo.getNombre() + "']\n";
                    }
                    
                    codigoView += "\t\ttry:\n" + 
                            "\t\t\tpe.save()\n" +
                            "\t\t\tmessages.success(request,'" + clase.getNombre() + " registrado')\n" +
                            "\t\texcept:\n" + 
                            "\t\t\tmessages.warning(request,'Error al registrar')\n" + 
                            "\t\treturn redirect('" + rol.getNombre() + "muestra" + 
                                clase.getNombre() + "')\n" + 
                            "\treturn render(request,'" + rol.getNombre() + "registra" + 
                                clase.getNombre() + ".html',{})\n\n";
                    
                    /*
                    Código para los demás métodos de view.
                    */
                    
                }
            }
        }
        
        /*
        Código para login.
        */
        
        //Logout
        codigoView += "def logout(request):\n" +
                "\trequest.session['rol'] = None\n" +
                "\treturn redirect('index')";
        
        try {
            File file = new File(d + "/" + nombreP + "/application/views.py");

            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigoView);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
