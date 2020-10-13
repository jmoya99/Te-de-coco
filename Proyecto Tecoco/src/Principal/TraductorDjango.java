/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import static Principal.TraductorTemplate.d;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author juanpma
 */
public class TraductorDjango {
    
    public static void generarModelo() {
        String codigoModelo = "from django.db import models\n\n"
                + "class usuario(models.Model):\n"
                + "\tusername = models.CharField(max_length=200,primary_key=True)\n"
                + "\tpassword = models.CharField(max_length=200)\n"
                + "\trol = models.CharField(max_length=200)";
        for (Clase clase : Clase.clases.values()) {
            if (clase.getAtributos().isEmpty()) {
                continue;
            }
            codigoModelo += "\n\n";
            codigoModelo += "class " + clase.getNombre() + "(models.Model):";
            for (Atributo atributo : clase.getAtributos().values()) {
                codigoModelo += "\n\t"+atributo.getNombre()+" = models.CharField(max_length=200";
                if (atributo.isIsPrimary()) {
                    codigoModelo += ",primary_key=True";
                }
                codigoModelo += ")";
            }
        }
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //Crear el archivo
            File file = new File(d+"D/application/models.py");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigoModelo);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public static void modificarSettings(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(d+"D/"+TraductorTemplate.nombreP+"D/settings.py");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento += "\nSTATICFILES_DIRS = [os.path.join(BASE_DIR,'static')]";
            int ini = documento.indexOf("INSTALLED_APPS");
            int fin = documento.indexOf("]",ini);
            documento = documento.replace(documento.substring(ini, fin), documento.substring(ini, fin)+"\t'application',\n");
            documento = documento.replace("en-us", "es-co");
            br.close();
            //Crear el archivo
            File file = new File(d+"D/"+TraductorTemplate.nombreP+"D/settings.py");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(documento);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
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
        
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //Crear el archivo
            File file = new File(d+"D/application/views.py");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigoView);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
