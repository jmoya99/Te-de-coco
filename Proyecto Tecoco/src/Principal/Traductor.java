/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author juan
 *
 * aportes de codigo de
 * http://chuwiki.chuidiang.org/index.php?title=Lectura_y_Escritura_de_Ficheros_en_Java
 * https://decodigo.com/java-crear-archivos-de-texto
 */
public class Traductor {

    public static final String o = "Plantillas/Plantilla HTML";
    public static String d = "Resultado/";

    public static void nombreProyecto(String nombre) {
        d += nombre;
    }

    public static void generarFormulariosGenericos() {
        for (Rol rol : Rol.roles.values()) {
            for (Metodo metodo : rol.getMetodos().values()) {
                if (!(metodo.getNombre().equals("registra") || metodo.getNombre().equals("muestra")
                        || metodo.getNombre().equals("modifica") || metodo.getNombre().equals("elimina")
                        || metodo.getNombre().equals("busca"))) {
                    File archivo = null;
                    FileReader fr = null;
                    BufferedReader br = null;
                    try {
                        // Apertura del fichero y creacion de BufferedReader para poder
                        // hacer una lectura comoda (disponer del metodo readLine()).
                        archivo = new File("Plantillas/formulario.txt");
                        fr = new FileReader(archivo);
                        br = new BufferedReader(fr);
                        // Lectura del fichero
                        String documento = "", linea;
                        while ((linea = br.readLine()) != null) {
                            documento += "\n" + linea;
                        }
                        String campos = "";
                        for (Atributo atributo : metodo.getParametros().values()) {
                            campos += campoDeTexto(atributo.getNombre()) + "\n";
                        }
                        documento = documento.replace("<--campos-->", campos);
                        documento = documento.replace("<--accion-->", metodo.getNombre());
                        documento = documento.replace("<--titulo-->", metodo.getNombre() + " " + metodo.getClase().getNombre());

                        //Crear el archivo
                        File file = new File(d + "/" + rol.getNombre() + metodo.getNombre() + ".html");
                        // Si el archivo no existe es creado
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
        }
    }

    // función para generar formulario de registro y modificación
    public static void generarRM() {
        for (Rol rol : Rol.roles.values()) {
            for (Metodo metodo : rol.getMetodos().values()) {
                if (metodo.getNombre().equals("registra") || metodo.getNombre().equals("modifica")) {
                    File archivo = null;
                    FileReader fr = null;
                    BufferedReader br = null;
                    try {
                        // Apertura del fichero y creacion de BufferedReader para poder
                        // hacer una lectura comoda (disponer del metodo readLine()).
                        archivo = new File("Plantillas/formulario.txt");
                        fr = new FileReader(archivo);
                        br = new BufferedReader(fr);
                        // Lectura del fichero
                        String documento = "", linea;
                        while ((linea = br.readLine()) != null) {
                            documento += "\n" + linea;
                        }
                        String campos = "";
                        Vector<String> Campos = new Vector<>();
                        for (Atributo atributo : metodo.getClase().getAtributos().values()) {
                            if (atributo.getNombre().equals("nombre") || atributo.getNombre().equals("identificacion")) {
                                Campos.add(0, atributo.getNombre());
                            } else {
                                Campos.add(atributo.getNombre());
                            }
                        }

                        for (String atributo : Campos) {
                            campos += campoDeTexto(atributo.replace("_", " ")) + "\n";
                        }
                        documento = documento.replace("<--campos-->", campos);

                        switch (metodo.getNombre()) {
                            case "modifica":
                                documento = documento.replace("<--titulo-->", "MODIFICAR " + metodo.getClase().getNombre());
                                documento = documento.replace("<--accion-->", "Modificar");
                                break;
                            case "registra":
                                documento = documento.replace("<--titulo-->", "REGISTRAR " + metodo.getClase().getNombre());
                                documento = documento.replace("<--accion-->", "Registrar");
                                break;
                            default:
                                documento = documento.replace("<--titulo-->", metodo.getNombre() + " " + metodo.getClase().getNombre());
                                documento = documento.replace("<--accion-->", metodo.getNombre());
                                break;
                        }

                        //Crear el archivo
                        File file = new File(d + "/" + rol.getNombre() + metodo.getNombre() + ".html");
                        // Si el archivo no existe es creado
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
        }
    }
    
    public static void generarBEM(){
        for (Rol rol : Rol.roles.values()) {
            for (Metodo metodo : rol.getMetodos().values()) {
                if (metodo.getNombre().equals("muestra")) {
                    File archivo = null;
                    FileReader fr = null;
                    BufferedReader br = null;
                    try {
                        // Apertura del fichero y creacion de BufferedReader para poder
                        // hacer una lectura comoda (disponer del metodo readLine()).
                        archivo = new File("Plantillas/mostrar.txt");
                        fr = new FileReader(archivo);
                        br = new BufferedReader(fr);
                        // Lectura del fichero
                        String documento = "", linea;
                        while ((linea = br.readLine()) != null) {
                            documento += "\n" + linea;
                        }
                        
                        documento = documento.replace("<-- Clase -->", metodo.getClase().getNombre());
                        
                        String columnas = "";
                        for (Atributo atributo : metodo.getClase().getAtributos().values()){
                            columnas += columnaTabla(atributo.getNombre());
                            
                            if (atributo.isIsPrimary()){
                                documento = documento.replace("<-- Identificador -->", atributo.getNombre());
                            }
                        }
                        
                        documento = documento.replace("<-- Columnas -->", columnas);
                        
                        File file = new File(d + "/" + rol.getNombre() + metodo.getNombre() 
                                + metodo.getClase().getNombre() + ".html");
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
        }
    }

    //funcion para generar menus
    public static void generarMenu() {
        for (Rol rol : Rol.roles.values()) {
            HashMap<String, Metodo> metodosValidos = new HashMap<String, Metodo>();
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;
            String subMenus = "";
            for (Clase clase : Clase.getClases().values()) {
                for (Metodo metodo : clase.getMetodos().values()) {
                    if (metodo.getRoles().containsKey(rol.getNombre())
                            && !(metodo.getNombre().equals("registra")
                            || metodo.getNombre().equals("modifica")
                            || metodo.getNombre().equals("elimina")
                            || metodo.getNombre().equals("busca"))) {
                        metodosValidos.put(metodo.getNombre(), metodo);
                    }
                }
                if(!metodosValidos.isEmpty()){
                    subMenus += menuDropdown(clase.getNombre(), metodosValidos);
                    metodosValidos.clear();
                }
            }
            
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                archivo = new File("Plantillas/menu.txt");
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                // Lectura del fichero
                String documento = "", linea;
                while ((linea = br.readLine()) != null) {
                    documento += "\n" + linea;
                }
                documento = documento.replace("<-- subMenus -->", subMenus);

                //Crear el archivo
                File file = new File(d + "/" + rol.getNombre() + ".html");
                // Si el archivo no existe es creado
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

    public static String campoDeTexto(String nombre) {
        return "<div class=\"form-group\"><label for=\"subject\">" + nombre + "</label>"
                + "<input class=\"form-control item\" type=\"text\" id=\"" + nombre + "\""
                + "name=\"" + nombre + "\"></div>";
    }

    public static String menuDropdown(String nomClase, HashMap<String, Metodo> metodos) {
        String menu = "<li class=\"nav-item dropdown\" role=\"presentation\">"
                + "<a data-toggle=\"dropdown\" aria-expanded=\"false\" class="
                + "\"nav-link dropdown-toggle\" href=\"#\">" + nomClase + "</a>\n"
                + "<div class=\"dropdown-menu\" role=\"presentation\">\n";
        for (Metodo metodo : metodos.values()) {
            menu += "<a class=\"dropdown-item\" href=\"#\">" + metodo.getNombre() + "</a>\n";
        }
        menu += "</div>\n</li>";
        return menu;
    }
    
    public static String columnaTabla(String nombreColumna){
        String columna = "<th>" + nombreColumna + "</th>";
        return columna;
    }

    //Recuperado de https://prlazarus.tk/md_2014_06_10.html
    public static void copiarDirectorio(String origen, String destino) {
        comprobarCrearDirectorio(destino);
        File directorio = new File(origen);
        File f;
        if (directorio.isDirectory()) {
            comprobarCrearDirectorio(destino);
            String[] files = directorio.list();
            if (files.length > 0) {
                for (String archivo : files) {
                    f = new File(origen + File.separator + archivo);
                    if (f.isDirectory()) {
                        comprobarCrearDirectorio(destino + File.separator + archivo + File.separator);
                        copiarDirectorio(origen + File.separator + archivo + File.separator, destino + File.separator + archivo + File.separator);
                    } else { //Es un archivo
                        copiarArchivo(origen + File.separator + archivo, destino + File.separator + archivo);
                    }
                }
            }
        }
    }

    public static void copiarArchivo(String sOrigen, String sDestino) {
        try {
            File origen = new File(sOrigen);
            File destino = new File(sDestino);
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void comprobarCrearDirectorio(String ruta) {
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

}
