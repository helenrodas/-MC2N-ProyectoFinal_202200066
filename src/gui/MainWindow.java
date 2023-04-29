/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import algo.DijkstraAlgorithm;
import models.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;

    //Crea la ventana principal
    public MainWindow(){
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }

    //Crea el panel donde se ponen los puntos y se unen
    private void setGraphPanel(){
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        //setTopPanel();
        setButtons();
    }

    //Panel morado (Se puede borrar)
//    private void setTopPanel() {
//        JLabel info = new JLabel("Dijkstra Shortest Path Visualiser by iamareebjamal");
//        info.setForeground(new Color(230, 220, 250));
//        JPanel panel = new JPanel();
//        panel.setBackground(new Color(130, 50, 250));
//        panel.add(info);
//        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
//        add(panel, BorderLayout.NORTH);
//    }

    //Se ponen las imagenes para control del programa. reinicia, empezar, informacion
    private void setButtons(){
        JButton run = new JButton();
        setupIcon(run, "run");
        JButton reset = new JButton();
        setupIcon(reset, "reset");
        final JButton info = new JButton();
        setupIcon(info, "info");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(DrawUtils.parseColor("#DDDDDD"));
        buttonPanel.add(reset);
        buttonPanel.add(run);
        buttonPanel.add(info);

        //Accion cuando se presiona el boton reiniciar
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.reset(); //Crea un nuevo panel
            }
        });

        //Accion cuando se presiona el boton informacion
        info.addActionListener(new ActionListener() {
            @Override //Son las instrucciones del programa
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Crea un nuevo nodo: presiona click en un lugar vacio\n" +
                        "Arrastre de nodo a nodo para crear una arista\n" +
                        "Combinaciones:\n" +
                        "Shift + Click izquierdo      :    Establecer nodo como inicio\n" +
                        "Shift + Click derecho    :    Establecer nodo como destino\n" +
                        "Ctrl  + Arrastrar               :    Reposicionar nodo\n" +
                        "Ctrl  + Click                :    Obtener ruta de nodo\n" +
                        "Ctrl  + Shift + Click   :    Eliminar Node/Edge\n");
            }
        });

        //Accion para empezar el programa
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph); //Se inicializa la clase Dijkstra
                try{
                    dijkstraAlgorithm.run(); // El funcionamiento de la clase, aqui corre
                    graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
                } catch (IllegalStateException ise){
                    JOptionPane.showMessageDialog(null, ise.getMessage());
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    //Metodo para ir a traer las imagenes a resources y poder usarlas dentro de la clase.
    private void setupIcon(JButton button, String img){
        try {
            Image icon = ImageIO.read(getClass().getResource(
                    "/resources/" + img + ".png"));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
