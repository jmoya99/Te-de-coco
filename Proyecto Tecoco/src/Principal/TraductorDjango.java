/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

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

    public static String d = "Resultado/" + TraductorTemplate.nombreP;

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
                codigoModelo += "\n\t" + atributo.getNombre() + " = models.CharField(max_length=200";
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
            File file = new File(TraductorDjango.d + "/application/models.py");
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

    public static void modificarSettings() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(TraductorDjango.d + "/" + TraductorTemplate.nombreP + "/settings.py");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento += "\nSTATICFILES_DIRS = [os.path.join(BASE_DIR,'static')]";
            int ini, fin;
            documento = "import os\n" + documento;
            ini = documento.indexOf("INSTALLED_APPS");
            fin = documento.indexOf("]", ini);
            documento = documento.replace(documento.substring(ini, fin), documento.substring(ini, fin) + "\t'application',\n");
            documento = documento.replace("en-us", "es-co");
            br.close();
            //Crear el archivo
            File file = new File(TraductorDjango.d + "/" + TraductorTemplate.nombreP + "/settings.py");
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
        String codigoView = "from django.shortcuts import render, redirect\n"
                + "from .models import *\n"
                + "from django.contrib import messages\n"
                + "from django.contrib.auth import authenticate\n"
                + "from django.contrib.auth.forms import AuthenticationForm\n"
                + "from django.contrib.auth import login as do_login\n"
                + "from django.contrib.auth import logout as do_logout\n\n"
                + "def registrausuario(request):\n" // No tiene control de rol.
                + "\tif request.method == 'POST':\n"
                + "\t\tpe = usuario()\n"
                + "\t\tpe.username = request.POST['username']\n"
                + "\t\tpe.password = request.POST['password']\n"
                + "\t\tpe.rol = request.POST['rol']\n"
                + "\t\ttry:\n"
                + "\t\t\tpe.save()\n"
                + "\t\t\tmessages.success(request, 'usuario registrado')\n"
                + "\t\texcept:\n"
                + "\t\t\tmessages.warning(request, 'Error al registrar')\n"
                + "\t\treturn redirect('muestrausuario')\n"
                + "\treturn render(request, 'registrausuario.html', {})\n\n"
                + "def muestrausuario(request):\n" // No tiene control de rol.
                + "\tif request.method == 'POST' and request.POST['username']:\n" 
                + "\t\tpa = usuario.objects.filter(id = request.POST['username'])\n"
                + "\telse:\n"
                + "\t\tpa = usuario.objects.all()\n"
                + "\tcontext = { 'pa': pa }\n"
                + "\treturn render(request, 'muestrausuario.html', context)\n\n"
                + "def modificiarusuario(request, id):\n" // No tiene control de rol.
                + "\tpe = usuario.objects.get(id = username)\n" 
                + "\tif request.method == 'GET':\n" 
                + "\t\tcontext = {'usuario': pe}\n" 
                + "\t\treturn render(request, 'modificarusuario.html', context)\n" 
                + "\telse:\n" 
                + "\t\tpe.password = request.POST['password']\n"
                + "\t\tpe.rol = request.POST['rol']\n" 
                + "\t\ttry:\n" 
                + "\t\t\tpe.save()\n" 
                + "\t\t\tmessages.success(request, 'Usuario modificado')\n" 
                + "\t\texcept:\n" 
                + "\t\t\tmessages.warning(request, 'Error al modificar')\n" 
                + "\treturn redirect('mostrarusuario')\n\n"
                + "def eliminarusuario(request, id):\n" // No tiene control de rol.
                + "\tpe = usuario.objects.get(id = id)\n" 
                + "\ttry:\n" 
                + "\t\tpe.delete()\n" 
                + "\t\tmessages.success(request, 'Usuario eliminada')\n" 
                + "\texcept:\n" 
                + "\t\tmessages.warning(request, 'Error al eliminar')\n" 
                + "\treturn redirect('mostrarusuario')\n\n";
        
        for (Clase clase : Clase.getClases().values()) {
            // Vistas de formularios genéricos
            for (Metodo metodo : clase.getMetodos().values()) {
                if (!(metodo.getNombre().equals("registra") || metodo.getNombre().equals("muestra")
                        || metodo.getNombre().equals("modifica") || metodo.getNombre().equals("elimina")
                        || metodo.getNombre().equals("busca"))) {
                    for (Rol rol : metodo.getRoles().values()) {
                        codigoView += "def " + rol.getNombre() + metodo.getNombre() 
                                + clase.getNombre() + "(request):\n" 
                                + "\tif request.session['rol'] is None:\n" 
                                + "\t\tmessages.warning(request,'Por favor inicie sesion')\n" 
                                + "\t\treturn redirect('index')\n" 
                                + "\telif request.session['rol'] != '" + rol.getNombre() + "':\n" 
                                + "\t\tmessages.warning(request,'Inicie sesion como " + rol.getNombre() + "')\n" 
                                + "\t\treturn redirect('index')\n" 
                                + "\tif request.method == 'POST':\n" 
                                + "\t\treturn redirect('')\n" // Acá se debe poner una ruta genérica para el Rol.
                                + "\treturn render(request,'" + rol.getNombre() + metodo.getNombre() 
                                + clase.getNombre() + ".html',{})\n\n";
                    }
                }
            }
            // Vistas de registra
            if (clase.getMetodos().containsKey("registra")) {
                for (Rol rol : clase.getMetodos().get("registra").getRoles().values()) {
                    codigoView += "def " + rol.getNombre() + "registra" + clase.getNombre()
                            + "(request):\n"
                            + "\tif request.session['rol'] is None:\n"
                            + "\t\tmessages.warning(request,'Por favor inicie sesion')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\telif request.session['rol'] != \"" + rol.getNombre() + "\":\n"
                            + "\t\tmessages.warning(request,'Inicie sesion como "
                            + rol.getNombre() + "')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\tif request.method == 'POST':\n"
                            + "\t\tpe = " + clase.getNombre() + "()\n";

                    for (Atributo atributo : clase.getAtributos().values()) {
                        codigoView += "\t\tpe." + atributo.getNombre() + " = "
                                + "request.POST['" + atributo.getNombre() + "']\n";
                    }

                    codigoView += "\t\ttry:\n"
                            + "\t\t\tpe.save()\n"
                            + "\t\t\tmessages.success(request,'" + clase.getNombre() + " registrado')\n"
                            + "\t\texcept:\n"
                            + "\t\t\tmessages.warning(request,'Error al registrar')\n"
                            + "\t\treturn redirect('" + rol.getNombre() + "muestra"
                            + clase.getNombre() + "')\n"
                            + "\treturn render(request,'" + rol.getNombre() + "registra"
                            + clase.getNombre() + ".html',{})\n\n";
                }
            }
            // Vistas de elimina
            if (clase.getMetodos().containsKey("elimina")) {
                for (Rol rol : clase.getMetodos().get("elimina").getRoles().values()) {
                    codigoView += "def " + rol.getNombre() + "elimina" + clase.getNombre()
                            + "(request, id):\n"
                            + "\tif request.session['rol'] is None:\n"
                            + "\t\tmessages.warning(request,'Por favor inicie sesion')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\telif request.session['rol'] != \"" + rol.getNombre() + "\":\n"
                            + "\t\tmessages.warning(request,'Inicie sesion como "
                            + rol.getNombre() + "')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\tpe = " + clase.getNombre() + ".objects.get(id = id)\n"
                            + "\ttry:\n"
                            + "\t\tpe.delete()\n"
                            + "\t\tmessages.success(request,'" + clase.getNombre() + " eliminado')\n"
                            + "\texcept:\n"
                            + "\t\tmessages.warning(request,'Error al Eliminar')\n"
                            + "\treturn redirect('" + rol.getNombre() + "muestra"
                            + clase.getNombre() + "')\n\n";
                }
            }
            // Vistas de muestra
            if (clase.getMetodos().containsKey("muestra")) {
                for (Rol rol : clase.getMetodos().get("muestra").getRoles().values()) {
                    // obtengo el identificaro único
                    String identificador = null;
                    for (Atributo atributo : clase.getAtributos().values()) {
                        if (atributo.isIsPrimary()) {
                            identificador = atributo.getNombre();
                        }
                    }
                    codigoView += "def " + rol.getNombre() + "muestra" + clase.getNombre()
                            + "(request):\n"
                            + "\tif request.session['rol'] is None:\n"
                            + "\t\tmessages.warning(request,'Por favor inicie sesion')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\telif request.session['rol'] != \"" + rol.getNombre() + "\":\n"
                            + "\t\tmessages.warning(request,'Inicie sesion como "
                            + rol.getNombre() + "')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\tif request.method == 'POST' and request.POST['" + identificador + "']:\n"
                            + "\t\tpa = " + clase.getNombre() + ".objects.filter(id = request.POST['" + identificador + "'])\n"
                            + "\telse:\n"
                            + "\t\tpa = " + clase.getNombre() + ".objects.all()\n"
                            + "\tcontext = { 'pa': pa }\n"
                            + "\treturn render(request, '" + rol.getNombre() + "muestra"
                            + clase.getNombre() + ".html', context)\n\n";
                }
            }
            
            // Vistas de modifica
            if (clase.getMetodos().containsKey("modifica")) {
                for (Rol rol : clase.getMetodos().get("modifica").getRoles().values()) {
                    codigoView += "def " + rol.getNombre() + "modifica" + clase.getNombre()
                            + "(request, id):\n"
                            + "\tif request.session['rol'] is None:\n"
                            + "\t\tmessages.warning(request,'Por favor inicie sesion')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\telif request.session['rol'] != \"" + rol.getNombre() + "\":\n"
                            + "\t\tmessages.warning(request,'Inicie sesion como "
                            + rol.getNombre() + "')\n"
                            + "\t\treturn redirect('index')\n"
                            + "\tpe = " + clase.getNombre() + ".objects.get(id = id)\n"
                            + "\tif request.method == 'GET':\n"
                            + "\t\tcontext = {'" + rol.getNombre() + "':pe}\n"
                            + "\t\treturn render(request,'" + rol.getNombre() + "modifica"
                            + clase.getNombre() + ".html',context)\n"
                            + "\telse:\n";
                    
                    for (Atributo atributo : clase.getAtributos().values()) {
                        if (!atributo.isIsPrimary()) {
                            codigoView += "\t\tpe." + atributo.getNombre() + " = "
                                    + "request.POST['" + atributo.getNombre() + "']\n";
                        }
                    }
                    codigoView += "\t\ttry:\n"
                            + "\t\t\tpe.save()\n"
                            + "\t\t\tmessages.success(request,'" + clase.getNombre() + " modificado')\n"
                            + "\t\texcept:\n"
                            + "\t\t\tmessages.warning(request,'Error al modificar')\n"
                            + "\treturn redirect('" + rol.getNombre() + "muestra"
                            + clase.getNombre() + "')\n\n";
                }
            }
        }
        // Login
        codigoView += "def login(request):\n"
                + "\tif request.method == \"POST\":\n"
                + "\t\tuser = request.POST['usuario']\n"
                + "\t\tpas = request.POST['contrasena']\n"
                + "\t\ttry:\n" + "\t\t\tusuario = usuario.objects.get(username = user, password = pas)\n";
        
        for (Rol rol : Rol.getRoles().values()) {
            codigoView += "\t\t\tif usuario.rol == \"" + rol.getNombre() + "\":\n"
                    + "\t\t\t\trequest.session['rol'] = \"" + rol.getNombre() + "\"\n"
                    + "\t\t\t\treturn redirect('menu" + rol.getNombre() + "')\n";
        }
        
        codigoView += "\t\texcept:\n"
                + "\t\t\tmessages.warning(request,'Usuario o contraseña incorrectos')\n"
                + "\treturn render(request, \"index.html\",{})\n";
        
        // Logout
        codigoView += "def logout(request):\n"
                + "\trequest.session['rol'] = None\n"
                + "\treturn redirect('index')";

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //Crear el archivo
            File file = new File(d + "/application/views.py");
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

    public static void modificarUrls() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            String documento = "from django.contrib import admin\n"
                    + "from django.urls import path\n"
                    + "from django.conf.urls import include, url\n"
                    + "from aplicacion.views import *\n\n"
                    + "urlpatterns = [\n"
                    + "path('admin/', admin.site.urls),\n"
                    + "url(r'^$',login,name = \"index\"),\n"
                    + "url(r'^logout/',logout,name = \"logout\"),\n"
                    + "url(r'^registrausuario/',registrausuario,name = \"registrausuario\"),\n"
                    + "url(r'^muestrausuario/',muestrausuario,name = \"muestrausuario\"),\n"
                    + "url(r'^modificausuario/(?P<id>\\w+)/$',modificausuario,name = \"modificausuario\"),\n"
                    + "url(r'^eliminausuario/(?P<id>\\w+)/$',eliminausuario,name = \"eliminausuario\"),\n";
            for (Rol rol : Rol.roles.values()) {
                for (Metodo metodo : rol.getMetodos().values()) {
                    String nombre = rol.getNombre() + metodo.getNombre() + metodo.getClase().getNombre();
                    if (metodo.getNombre().equals("elimina") || metodo.getNombre().equals("modifica")) {
                        documento += "url(r'^" + nombre + "/(?P<id>\\w+)/$'," + nombre + ",name = \"" + nombre + "\"),\n";
                    } else if (!metodo.getNombre().equals("busca")) {
                        documento += "url(r'^" + nombre + "/'," + nombre + ",name = \"" + nombre + "\"),\n";
                    }
                }
            }
            documento += "]";
            //Crear el archivo
            File file = new File(TraductorDjango.d + "/" + TraductorTemplate.nombreP + "/urls.py");
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
}
