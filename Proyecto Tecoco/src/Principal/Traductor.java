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
    public static String nombreP = "";

    public static void direccionProyecto() {
        d += nombreP;
    }

    public static void generarFooter() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Plantillas/footer.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento = documento.replace("<--Titulo-->", Traductor.nombreP);

            //Crear el archivo
            File file = new File(d + "/footer.html");
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
                        File file = new File(d + "/" + rol.getNombre() + metodo.getNombre() + metodo.getClase().getNombre() + ".html");
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
                            if (metodo.getNombre().equals("modifica") && !atributo.isIsPrimary()) {
                                if (atributo.getNombre().equals("nombre")) {
                                    Campos.add(0, atributo.getNombre());
                                } else {
                                    Campos.add(atributo.getNombre());
                                }
                            } else if (metodo.getNombre().equals("registra")) {
                                if (atributo.getNombre().equals("nombre") || atributo.getNombre().equals("identificacion")) {
                                    Campos.add(0, atributo.getNombre());
                                } else {
                                    Campos.add(atributo.getNombre());
                                }
                            }

                        }

                        for (String atributo : Campos) {
                            if (metodo.getNombre().equals("modifica")) {
                                campos += campoDeTextoM(atributo.replace("_", " ")) + "\n";
                            } else {
                                campos += campoDeTexto(atributo.replace("_", " ")) + "\n";
                            }                                                        
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
                        }

                        //Crear el archivo
                        File file = new File(d + "/" + rol.getNombre() + metodo.getNombre()
                                + metodo.getClase().getNombre() + ".html");
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

    public static void generarBEM() {
        for (Rol rol : Rol.roles.values()) {
            for (Clase clase : Clase.getClases().values()) {
                if (clase.getMetodos().containsKey("muestra") 
                        && clase.getMetodos().get("muestra").getRoles().containsKey(rol.getNombre())){
                    Metodo muestra = clase.getMetodos().get("muestra");
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

                        documento = documento.replace("<-- Clase -->", muestra.getClase().getNombre());
                        
                        for (Atributo atributo : muestra.getClase().getAtributos().values()) {
                            if (atributo.isIsPrimary()) {
                                documento = documento.replace("<-- Identificador -->", atributo.getNombre());
                            }                          
                        }

                        String columnas = "";
                        String fila = "";
                        Vector<String> Columnas = ordenarAtributos(muestra.getClase().getAtributos());                        

                        for (String atributo : Columnas) {
                            columnas += crearColumna(atributo);
                            fila += crearDatoColumna(atributo) + "\n";
                        }
                        documento = documento.replace("<-- Columnas -->", columnas);
                        documento = documento.replace("<-- Fila -->", fila);
                        
                        // <th>Gestionar</th>
                        String botonRegistra = "";
                        String botonModifica = "";
                        String botonElimina = "";
                        String td = "";
                        String endTd = "";
                        String gestionar = "";
                        if (clase.getMetodos().containsKey("registra")
                                && clase.getMetodos().get("registra").getRoles().containsKey(rol.getNombre())) {
                            botonRegistra = "<a href=\"{{% url 'registra' %}}\"><button class=\"btn btn-success\" id=\"btnRegistrar\" style=\"margin-left: 5px;\""
                                    + "type=\"submit\"><p title=\"Registrar\"><i class=\"fa fa-plus\" style=\"font-size: 15px;\"></i>&nbsp;Registrar</p></button></a>";
                        }
                        if (clase.getMetodos().containsKey("modifica") || clase.getMetodos().containsKey("elimina")) {
                            String primary = null;
                            for (Atributo Att: clase.getAtributos().values()) {
                                if(Att.isIsPrimary()){
                                    primary = Att.getNombre();
                                }
                            }
                            if (clase.getMetodos().containsKey("modifica")
                                    && clase.getMetodos().get("modifica").getRoles().containsKey(rol.getNombre())) {
                                botonModifica = "<a href=\"{{% url 'modifica' id = p." + primary + " %}}\"><button class=\"btn btn-success\" style=\"margin-left: 5px;background: rgb(36,129,167);\" type=\"submit\"><p "
                                        + "title=\"Modificar\"><i class=\"fa fa-pencil\" style=\"font-size: 15px;\"></i></p></button></a>";
                            }
                            if (clase.getMetodos().containsKey("elimina")
                                    && clase.getMetodos().get("elimina").getRoles().containsKey(rol.getNombre())) {
                                botonElimina = "<a href=\"{{% url 'elimina' id = p." + primary + " %}}\"><button class=\"btn btn-danger\" style=\"margin-left: 5px;\" type=\"submit\"><p "
                                        + "title=\"Eliminar\"><i class=\"fa fa-trash\" style=\"font-size: 15px;\"></i></p></button></a>";
                            }
                            td = "<td>";
                            endTd = "</td>";
                            gestionar = "<th> Gestionar </th>";
                        }
                        documento = documento.replace("<-- Registra -->", botonRegistra);
                        documento = documento.replace("<-- Modifica -->", botonModifica);
                        documento = documento.replace("<-- Elimina -->", botonElimina);
                        documento = documento.replace("<-- td -->", td);
                        documento = documento.replace("<-- /td -->", endTd);
                        documento = documento.replace("<-- Gestionar -->", gestionar);

                        File file = new File(d + "/" + rol.getNombre() + muestra.getNombre()
                                + muestra.getClase().getNombre() + ".html");
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
                if (!metodosValidos.isEmpty()) {
                    subMenus += menuDropdown(rol.getNombre(), clase.getNombre(), metodosValidos);
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
                documento = documento.replaceAll("<-- TituloProyecto -->", nombreP);
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
    
    public static String campoDeTextoM(String nombre) {
        return "<div class=\"form-group\"><label for=\"subject\">" + nombre + "</label>"
                + "<input class=\"form-control item\" type=\"text\" id=\"" + nombre + "\""
                + "name=\"" + nombre + "\" value=\"{{pe." + nombre + "}}\"></div>";
    }

    public static String menuDropdown(String nomRol, String nomClase, HashMap<String, Metodo> metodos) {
        String menu = "<li class=\"nav-item dropdown\" role=\"presentation\">"
                + "<a data-toggle=\"dropdown\" aria-expanded=\"false\" class="
                + "\"nav-link dropdown-toggle\" href=\"#\">"
                + nomClase.replace("_", " ") + "</a>\n<div class=\""
                + "dropdown-menu\" role=\"presentation\">\n";
        for (Metodo metodo : metodos.values()) {
            menu += "<a class=\"dropdown-item\" href=\"" + nomRol
                    + metodo.getNombre() + nomClase + ".html\">"
                    + metodo.getNombre().replace("_", " ") + "</a>\n";
        }
        menu += "</div>\n</li>";
        return menu;
    }

    public static String crearColumna(String nombreColumna) {
        String columna = "<th>" + nombreColumna.replace("_", " ") + "</th>";
        return columna;
    }

    public static String crearDatoColumna(String dato) {
        String Dato = "<td>{{p." + dato + "}}</td>";
        return Dato;
    }
    
    public static Vector<String> ordenarAtributos(HashMap<String, Atributo> atributos) {
        Vector<String> Columnas = new Vector<>();
        for (Atributo atributo : atributos.values()) {            
            if (atributo.getNombre().equals("nombre") || atributo.getNombre().equals("identificacion")) {
                Columnas.add(0, atributo.getNombre());
            } else {
                Columnas.add(atributo.getNombre());
            }
        }
        
        return Columnas;
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
