/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.lang.ProcessBuilder;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;


/**
 *
 * @author cj4
 * Aportes de codigo de:
 * https://mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 * https://mkyong.com/java/how-to-execute-shell-command-from-java/
 */
public class TraductorComandos {
    
    public static String d = "Resultado";
    public static String nombreP = "";
    public static int nScript = 0;
    public static String OS = System.getProperty("os.name").toLowerCase();
    
    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);

    }

    public static void execCommand(String comando) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (isWindows()) {
            processBuilder.command("cmd.exe", "/c", comando);
        } else if (isUnix()) {
            processBuilder.command("bash", "-c", comando);
        }

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    public static void execCommands(String comandos, String parteRuta) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String extension = "";
        int tamano;
        try {

            if (isWindows()) {
                archivo = new File("Script\\windows.txt");
                extension = ".bat";
                tamano = 19;
            } else if (isUnix()) {
                archivo = new File("Scripts/linux.txt");
                extension = ".sh";
                tamano = 17;
            } else {
                archivo = new File("Scripts/" + nScript + ".txt");
                extension = ".txt";
                tamano = 13;
            }

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String documento = "", linea;
            while ((linea = br.readLine()) != null) {
                documento += "\n" + linea;
            }

            documento = documento.replace("<-- ruta -->",
                    archivo.getAbsolutePath().substring(0,
                            archivo.getAbsolutePath().length() - tamano)
                    + parteRuta);
            documento = documento.replace("<-- comandos -->", comandos);
            //Crear el archivo
            File file = new File(d + nScript + extension);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(documento);
            bw.close();
            if (isWindows()) {
                execCommand(file.getAbsolutePath());
            } else if (isUnix()) {
                execCommand("chmod a+x ./" + d + nScript + extension);
                execCommand("./" + d + nScript + extension);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generarScripts() {
        if (isWindows()) {
            d = d + "\\";
            execCommands("django-admin startproject " + nombreP, d);
            nScript++;
            execCommands("python manage.py makemigrations\n"
                    + "python manage.py migrate\n"
                    + "django-admin startapp application", d + nombreP);
        } else if (isUnix()) {
            d = d + "/";
            execCommands("django-admin startproject " + nombreP, d);
            nScript++;
            execCommands("python3 manage.py makemigrations\n"
                    + "python3 manage.py migrate\n"
                    + "django-admin startapp application", d + nombreP);
        }
    }
}
