/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author juan Codigo recuperado de:
 * https://www.discoduroderoer.es/como-usar-el-componente-jfilechooser-en-una-aplicacion-grafica-en-java/
 */
public class InterfazDeEntrada extends JFrame {

    private JPanel contentPane;
    private JTextArea textArea;
    private JButton btnSeleccionar;
    private JButton btnEliminar;
    private JButton btnCrear;
    private JLabel lblArchivos;
    private ArrayList<File> csvs = new ArrayList<>();

    public InterfazDeEntrada() {
        //Parametros asociados a la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 410, 260);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        setTitle("Proyecto Tecoco");

        lblArchivos = new JLabel("Archivos");
        lblArchivos.setBounds(20, 10, 100, 10);
        contentPane.add(lblArchivos);

        btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(280, 30, 120, 40);
        contentPane.add(btnSeleccionar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(280, 100, 120, 40);
        contentPane.add(btnEliminar);

        btnCrear = new JButton("Generar");
        btnCrear.setBounds(280, 170, 120, 40);
        contentPane.add(btnCrear);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(20, 30, 250, 180);
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(20, 30, 250, 180);
        contentPane.add(scroll);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                generarProyecto();
            }

        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                String nom = JOptionPane.showInputDialog("Ingrese el nombre "
                        + "del archivo a eliminar(sin la extension)");
                textArea.setText("");
                int borr = 0;
                for (int i = 0; i < csvs.size(); i++) {
                    if (csvs.get(i - borr).getName().trim().equals(nom + ".csv")) {
                        csvs.remove(i - borr);
                        borr++;
                    } else {
                        textArea.setText(textArea.getText() + csvs.get(i - borr).getName() + "\n");
                    }
                }
            }
        });

        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Creamos el objeto JFileChooser
                JFileChooser fc = new JFileChooser();

                //Indicamos que podemos seleccionar varios ficheros
                fc.setMultiSelectionEnabled(true);

                //Indicamos lo que podemos seleccionar
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                //Creamos el filtro
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.CSV", "csv");

                //Le indicamos el filtro
                fc.setFileFilter(filtro);

                //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
                int seleccion = fc.showOpenDialog(contentPane);

                //Si el usuario, pincha en aceptar
                if (seleccion == JFileChooser.APPROVE_OPTION) {

                    //Seleccionamos el fichero
                    File[] ficheros = fc.getSelectedFiles();

                    for (File fichero : ficheros) {
                        textArea.setText(textArea.getText() + fichero.getName() + "\n");
                        csvs.add(fichero);
                    }
                }
            }
        });
    }

    private void generarProyecto() {
        btnSeleccionar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCrear.setEnabled(false);
        try {
            String nom = JOptionPane.showInputDialog("Ingrese el nombre del proyecto");
            TraductorTemplate.nombreP = nom;
            TraductorTemplate.direccionProyecto();
            for (File csv : csvs) {
                Main.interpretar(csv);
            }
            TraductorComandos.nombreP = nom + "D";
            TraductorComandos.generarScripts();
            TraductorTemplate.copiarDirectorio(TraductorTemplate.o, TraductorTemplate.d);
            TraductorTemplate.generarFooter();
            TraductorTemplate.generarFormulariosGenericos();
            TraductorTemplate.generarRM();
            TraductorTemplate.generarBEM();
            TraductorTemplate.generarMenu();
            TraductorDjango.generarModelo();
            TraductorDjango.modificarSettings();
            TraductorDjango.generarView();
            File fichero = new File("Resultado");
            JOptionPane.showMessageDialog(null, "Su proyecto fue creado en "+fichero.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un "
                    + "error al interpretar el archivo, confirme"
                    + " que es un archivo valido", "Error", JOptionPane.ERROR_MESSAGE);
            btnSeleccionar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnCrear.setEnabled(true);
        }
    }

}
