import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class IntroSwing {

    private static JLabel titulo = new JLabel();
    private static JButton boton = new JButton();
    private static JTextField texto = new JTextField();
    private static JList<Path> lista = new JList<Path>();
    private static DefaultListModel modelo = new DefaultListModel<Path>();

    public static void initComponents(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Explorador");
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);

        titulo.setText("Explorador de archivos");
        titulo.setHorizontalAlignment(JLabel.CENTER);

        boton.setText("Buscar");
        texto.setColumns(40);

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setModel(modelo);

        frame.getContentPane().setLayout( new BorderLayout() );

        frame.getContentPane().add(titulo, BorderLayout.NORTH);

        var panel = new JPanel();
        panel.setLayout( new FlowLayout() );
        panel.add(boton);
        panel.add(texto);

        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.getContentPane().add(lista, BorderLayout.CENTER);
    }

    public static void initController(JFrame frame) {
        boton.addActionListener( (e) -> {
            String ruta = texto.getText();
            Path path = Path.of(ruta);
            modelo.addAll(listarArchivos(path));
        });

        lista.getSelectionModel().addListSelectionListener( (e) -> {
                if(!e.getValueIsAdjusting() ) {
                    if(lista.getSelectedValue() != null) {
                        Path nuevaRuta = lista.getSelectedValue();
                        modelo.clear();
                        modelo.addAll(listarArchivos(nuevaRuta));
                    }
                }
            }
        );
    }

    private static ArrayList<Path> listarArchivos(Path path) {
        var salida = new ArrayList<Path>(0);

        if (Files.isDirectory(path)) {
            try {
                for (Path p : Files.list(path).toList()) {
                    salida.add(p);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return salida;
    }

    public static void main(String[] args) {
        System.out.println("Hello World");

        JFrame frame = new JFrame();
        initComponents(frame);
        initController(frame);

        frame.setVisible(true);

    }
}
