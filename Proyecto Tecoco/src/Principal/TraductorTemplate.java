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
 * @author CJ4
 *
 * aportes de codigo de
 * http://chuwiki.chuidiang.org/index.php?title=Lectura_y_Escritura_de_Ficheros_en_Java
 * https://decodigo.com/java-crear-archivos-de-texto
 */
public class TraductorTemplate {

    public static final String o = "Plantillas/Plantilla HTML";
    public static String d = "Resultado/";
    public static String nombreP = "";
    public static String rutaStatic = "Resultado/";

    public static void direccionProyecto() {
        d += nombreP + "/templates/";
        rutaStatic += nombreP + "/static/";
    }

    public static void generarMenuRol() {
        for (Rol rol : Rol.roles.values()) {
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                archivo = new File("Plantillas/menuRol.txt");
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                // Lectura del fichero
                String documento = "", linea;
                while ((linea = br.readLine()) != null) {
                    documento += "\n" + linea;
                }
                documento = documento.replace("<--rol-->", rol.getNombre());
                //Crear el archivo
                File file = new File(TraductorTemplate.d + "/menu" + rol.getNombre() + ".html");
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

    public static void generarHeader() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Plantillas/header.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento = documento.replace("<-- TituloProyecto -->", nombreP.replace("_", " "));

            //Crear el archivo
            File file = new File(TraductorTemplate.d + "/header.html");
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

    public static void generarIndex() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Plantillas/index.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento = documento.replaceAll("<-- TituloProyecto -->", nombreP.replace("_", " "));

            //Crear el archivo
            File file = new File(TraductorTemplate.d + "/index.html");
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
            documento = documento.replace("<--Titulo-->", nombreP.replace("_", " "));

            //Crear el archivo
            File file = new File(TraductorTemplate.d + "/footer.html");
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
        for (Clase clase : Clase.clases.values()) {
            for (Metodo metodo : clase.getMetodos().values()) {
                for (Rol rol : metodo.getRoles().values()) {
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
                                campos += campoDeTexto(atributo.getNombre(), false) + "\n";
                            }
                            documento = documento.replace("<--rol-->", rol.getNombre());
                            documento = documento.replace("<--campos-->", campos);
                            documento = documento.replace("<--accion-->", metodo.getNombre());
                            documento = documento.replace("<--titulo-->", metodo.getNombre() + " " + clase.getNombre());

                            //Crear el archivo
                            File file = new File(TraductorTemplate.d + "/" + rol.getNombre() + metodo.getNombre() + clase.getNombre() + ".html");
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
    }

    // función para generar formulario de registro y modificación
    public static void generarRM() {
        for (Clase clase : Clase.clases.values()) {
            for (Metodo metodo : clase.getMetodos().values()) {
                for (Rol rol : metodo.getRoles().values()) {
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
                                if (metodo.getNombre().equals("modifica") && !atributo.isPrimary()) {
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
                                    campos += campoDeTextoM(atributo) + "\n";
                                } else {
                                    if (metodo.getClase().getAtributos().get(atributo).isPrimary()) {
                                        campos += campoDeTexto(atributo, true) + "\n";
                                    } else {
                                        campos += campoDeTexto(atributo, false) + "\n";
                                    }
                                }
                            }
                            documento = documento.replace("<--rol-->", rol.getNombre());
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
                            File file = new File(TraductorTemplate.d + "/" + rol.getNombre() + metodo.getNombre()
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
    }

    public static void generarBEM() {
        for (Rol rol : Rol.roles.values()) {
            for (Clase clase : Clase.getClases().values()) {
                if (clase.getMetodos().containsKey("muestra")
                        && clase.getMetodos().get("muestra").getRoles().containsKey(rol.getNombre())) {
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
                            if (atributo.isPrimary()) {
                                documento = documento.replaceAll("<-- Identificador -->", atributo.getNombre());
                            }
                        }

                        String columnas = "";
                        String fila = "";
                        Vector<String> Columnas = ordenarAtributos(muestra.getClase().getAtributos());

                        for (String atributo : Columnas) {
                            columnas += crearColumna(atributo);
                            fila += crearDatoColumna(atributo) + "\n";
                        }
                        documento = documento.replace("<--rol-->", rol.getNombre());
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
                            botonRegistra = "<a href=\"{% url '" + rol.getNombre() + "registra" + clase.getNombre() + "' %}\"><button class=\"btn btn-success\" id=\"btnRegistrar\" style=\"margin-left: 5px;\""
                                    + "type=\"button\"><p title=\"Registrar\"><i class=\"fa fa-plus\" style=\"font-size: 15px;\"></i>&nbsp;Registrar</p></button></a>";
                        }
                        if (clase.getMetodos().containsKey("modifica") || clase.getMetodos().containsKey("elimina")) {
                            String primary = null;
                            for (Atributo Att : clase.getAtributos().values()) {
                                if (Att.isPrimary()) {
                                    primary = Att.getNombre();
                                }
                            }
                            if (clase.getMetodos().containsKey("modifica")
                                    && clase.getMetodos().get("modifica").getRoles().containsKey(rol.getNombre())) {
                                botonModifica = "<a href=\"{% url '" + rol.getNombre() + "modifica" + clase.getNombre() + "' id=p." + primary + " %}\"><button class=\"btn btn-success\" style=\"margin-left: 5px;background: rgb(36,129,167);\" type=\"button\"><p "
                                        + "title=\"Modificar\"><i class=\"fa fa-pencil\" style=\"font-size: 15px;\"></i></p></button></a>";
                            }
                            if (clase.getMetodos().containsKey("elimina")
                                    && clase.getMetodos().get("elimina").getRoles().containsKey(rol.getNombre())) {
                                botonElimina = "<a href=\"{% url '" + rol.getNombre() + "elimina" + clase.getNombre() + "' id=p." + primary + " %}\"><button class=\"btn btn-danger\" style=\"margin-left: 5px;\" type=\"button\"><p "
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

                        File file = new File(TraductorTemplate.d + "/" + rol.getNombre() + muestra.getNombre()
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
    public static void generarMenus() {
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
                documento = documento.replaceAll("<-- TituloProyecto -->", nombreP.replace("_", " "));
                documento = documento.replace("<-- subMenus -->", subMenus);

                //Crear el archivo
                File file = new File(TraductorTemplate.d + "/" + rol.getNombre() + ".html");
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

    public static void generarPlantillasUsuario() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String documento = "", linea;
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            //muestra usuario
            archivo = new File("Plantillas/usuario.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero

            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento = documento.replace("<-- TituloProyecto -->", nombreP.replace("_", " "));
            file = new File(TraductorTemplate.d + "/usuario.html");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(documento);
            bw.close();

            archivo = new File("Plantillas/muestrausuario.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero

            documento = "";
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            file = new File(TraductorTemplate.d + "/muestrausuario.html");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(documento);
            bw.close();

            //registra usuario
            archivo = new File("Plantillas/registrausuario.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            documento = "";
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }

            String roles = "";

            for (Rol rol : Rol.roles.values()) {
                roles += opcionRol(rol.getNombre());
            }
            documento = documento.replace("<-- roles -->", roles);
            file = new File(TraductorTemplate.d + "/registrausuario.html");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(documento);
            bw.close();

            //modifica usuario
            archivo = new File("Plantillas/modificausuario.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            documento = "";
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }
            documento = documento.replace("<-- roles -->", roles);
            file = new File(TraductorTemplate.d + "/modificausuario.html");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
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

    public static String opcionRol(String rol) {
        return "<option value=\"" + rol + "\">" + rol.replace("_", " ")
                + "</option>";
    }

    public static String campoDeTexto(String nombre, boolean required) {
        String r = "";
        if (required) {
            r = "required";
        }
        return "<div class=\"form-group\"><label for=\"subject\">" + nombre.replace("_", " ") + "</label>"
                + "<input class=\"form-control item\" type=\"text\" id=\"" + nombre + "\" "
                + "name=\"" + nombre + "\" " + r + "></div>";
    }

    public static String campoDeTextoM(String nombre) {
        return "<div class=\"form-group\"><label for=\"subject\">" + nombre.replace("_", " ") + "</label>"
                + "<input class=\"form-control item\" type=\"text\" id=\"" + nombre + "\" "
                + "name=\"" + nombre + "\" value=\"{{pe." + nombre + "}}\"></div>";
    }

    public static String menuDropdown(String nomRol, String nomClase, HashMap<String, Metodo> metodos) {
        String menu = "<li class=\"nav-item dropdown\" role=\"presentation\">"
                + "<a data-toggle=\"dropdown\" aria-expanded=\"false\" class="
                + "\"nav-link dropdown-toggle\" href=\"#\">"
                + nomClase.replace("_", " ") + "</a>\n<div class=\""
                + "dropdown-menu\" role=\"presentation\">\n";
        for (Metodo metodo : metodos.values()) {
            menu += "<a class=\"dropdown-item\" href=\"{% url '" + nomRol
                    + metodo.getNombre() + nomClase + "' %}\" >"
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
