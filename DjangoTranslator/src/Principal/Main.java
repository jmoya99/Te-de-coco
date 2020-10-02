package Principal;

import java.lang.ProcessBuilder;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

    private static String OS = System.getProperty("os.name").toLowerCase();
    public static String nombreP;

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

    public static void startProject() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String extension = "";
        int tamano;
        try {

            if (isWindows()) {
                archivo = new File("windows.txt");
                extension = ".bat";
                tamano = 11;
            } else if (isUnix()) {
                archivo = new File("linux.txt");
                extension = ".sh";
                tamano = 9;
            } else {
                archivo = new File("a.txt");
                extension = ".txt";
                tamano = 5;
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
                            archivo.getAbsolutePath().length() - tamano));
            documento = documento.replace("<-- comandos -->",
                    "django-admin startproject " + nombreP);
            //Crear el archivo
            File file = new File("a" + extension);
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
                execCommand("chmod +x a" + extension);
                execCommand("./a" + extension);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void execOCommand(String comandos) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String extension = "";
        int tamano;
        try {

            if (isWindows()) {
                archivo = new File("windows.txt");
                extension = ".bat";
                tamano = 11;
            } else if (isUnix()) {
                archivo = new File("linux.txt");
                extension = ".sh";
                tamano = 9;
            } else {
                archivo = new File("a.txt");
                extension = ".txt";
                tamano = 5;
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
                    + nombreP);
            documento = documento.replace("<-- comandos -->", comandos);
            //Crear el archivo
            File file = new File("a" + extension);
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
                execCommand("chmod +x a" + extension);
                execCommand("./a" + extension);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(OS);
        nombreP = "PROYECTO"; //NO se pueden utilizar caracteres especiales
        startProject();
        if (isWindows()) {
            execOCommand("python manage.py makemigrations\n"
                    + "python manage.py migrate\n"
                    + "django-admin startapp application");
        } else if (isUnix()) {
            execOCommand("python3 manage.py makemigrations\n"
                    + "python3 manage.py migrate\n"
                    + "django-admin startapp application");
        }
    }

}
