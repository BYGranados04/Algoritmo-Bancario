import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChefInteractivo extends JPanel {
    private Inventario inventario;
    private JLabel ingredienteLabel;
    private Timer timer;
    private ImageIcon imagenChefInteractivo;
    private boolean mostrarChef = true;

    private int ingredienteActual = 0;
    private boolean buscando = true;

    private JLabel panLabel;
    private JLabel carneLabel;
    private JLabel lechugaLabel;
    private JLabel quesoLabel;
    private JLabel tocinoLabel;
    private JLabel tomateLabel;

    private JLabel necesarioPanLabel;
    private JLabel necesarioCarneLabel;
    private JLabel necesarioLechugaLabel;
    private JLabel necesarioQuesoLabel;
    private JLabel necesarioTocinoLabel;
    private JLabel necesarioTomateLabel;

    private int[] maximosIngredientes;
    private int[] actualesIngredientes;

    public ChefInteractivo(Inventario inventario, JLabel panLabel, JLabel carneLabel, JLabel lechugaLabel, JLabel quesoLabel, JLabel tocinoLabel, JLabel tomateLabel,
                           JLabel necesarioPanLabel, JLabel necesarioCarneLabel, JLabel necesarioLechugaLabel, JLabel necesarioQuesoLabel, JLabel necesarioTocinoLabel, JLabel necesarioTomateLabel,
                           int[] maximosIngredientes) {
        this.inventario = inventario;
        this.panLabel = panLabel;
        this.carneLabel = carneLabel;
        this.lechugaLabel = lechugaLabel;
        this.quesoLabel = quesoLabel;
        this.tocinoLabel = tocinoLabel;
        this.tomateLabel = tomateLabel;
        this.necesarioPanLabel = necesarioPanLabel;
        this.necesarioCarneLabel = necesarioCarneLabel;
        this.necesarioLechugaLabel = necesarioLechugaLabel;
        this.necesarioQuesoLabel = necesarioQuesoLabel;
        this.necesarioTocinoLabel = necesarioTocinoLabel;
        this.necesarioTomateLabel = necesarioTomateLabel;
        this.maximosIngredientes = maximosIngredientes.clone();
        this.actualesIngredientes = new int[maximosIngredientes.length];

        setLayout(new BorderLayout());
        setOpaque(false);

        try {
            Image chefImage = ImageIO.read(new File("imagenes/cocinero2.png"));
            Image scaledChefImage = chefImage.getScaledInstance(85, 85, Image.SCALE_SMOOTH);
            imagenChefInteractivo = new ImageIcon(scaledChefImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ingredienteLabel = new JLabel();
        ingredienteLabel.setHorizontalAlignment(JLabel.CENTER);
        add(ingredienteLabel, BorderLayout.SOUTH);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSiguienteIngrediente();
            }
        });
    }

    public void iniciarBusqueda() {
        timer.start();
    }

    public void buscarSiguienteIngrediente() {
        if (!buscando) return;

        mostrarChef = false;
        repaint();

        String ingrediente = "";
        boolean encontrado = false;
        ImageIcon imagenIngrediente = null;

        switch (ingredienteActual) {
            case 0:
                ingrediente = "Pan";
                if (actualesIngredientes[0] < maximosIngredientes[0] && inventario.getPan() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/pan.png");
                    inventario.setPan(inventario.getPan() - 1);
                    actualesIngredientes[0]++;
                }
                break;
            case 1:
                ingrediente = "Carne";
                if (actualesIngredientes[1] < maximosIngredientes[1] && inventario.getCarne() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/carne.png");
                    inventario.setCarne(inventario.getCarne() - 1);
                    actualesIngredientes[1]++;
                }
                break;
            case 2:
                ingrediente = "Lechuga";
                if (actualesIngredientes[2] < maximosIngredientes[2] && inventario.getLechuga() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/lechuga.png");
                    inventario.setLechuga(inventario.getLechuga() - 1);
                    actualesIngredientes[2]++;
                }
                break;
            case 3:
                ingrediente = "Queso";
                if (actualesIngredientes[3] < maximosIngredientes[3] && inventario.getQueso() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/queso.png");
                    inventario.setQueso(inventario.getQueso() - 1);
                    actualesIngredientes[3]++;
                }
                break;
            case 4:
                ingrediente = "Tocino";
                if (actualesIngredientes[4] < maximosIngredientes[4] && inventario.getTocino() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/tocino.png");
                    inventario.setTocino(inventario.getTocino() - 1);
                    actualesIngredientes[4]++;
                }
                break;
            case 5:
                ingrediente = "Tomate";
                if (actualesIngredientes[5] < maximosIngredientes[5] && inventario.getTomate() > 0) {
                    encontrado = true;
                    imagenIngrediente = cargarImagenIngrediente("imagenes/tomate.png");
                    inventario.setTomate(inventario.getTomate() - 1);
                    actualesIngredientes[5]++;
                }
                break;
        }

        if (encontrado) {
            ingredienteLabel.setIcon(imagenIngrediente);
            actualizarNecesarios();
        } else {
            JOptionPane.showMessageDialog(this, "No hay " + ingrediente + " disponible.", "Ingrediente no encontrado", JOptionPane.WARNING_MESSAGE);
        }

        actualizarEtiquetasInventario();

        if (todosLosIngredientesEncontrados()) {
            mostrarHamburguesa();
            resetearIngredientes();
            inventario.recargarInventario();
        } else {
            if (actualesIngredientes[ingredienteActual] >= maximosIngredientes[ingredienteActual]) {
                ingredienteActual = (ingredienteActual + 1) % 6;
            }
        }

        mostrarChef = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mostrarChef) {
            if (imagenChefInteractivo != null) {
                g.drawImage(imagenChefInteractivo.getImage(), 0, 0, this);
            }
        }
    }

    private boolean todosLosIngredientesEncontrados() {
        for (int i = 0; i < maximosIngredientes.length; i++) {
            if (actualesIngredientes[i] < maximosIngredientes[i]) {
                return false;
            }
        }
        return true;
    }

    private void mostrarHamburguesa() {
        try {
            Image imagenHamburguesa = ImageIO.read(new File("imagenes/hamburguesa.png"));
            Image scaledHamburguesaImage = imagenHamburguesa.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ingredienteLabel.setIcon(new ImageIcon(scaledHamburguesaImage));

            int respuesta = JOptionPane.showConfirmDialog(this, "¡Hamburguesa terminada! (Proceso)", "Proceso Completado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (respuesta == JOptionPane.OK_OPTION) {
                resetearIngredientes();
                inventario.recargarInventario();
                iniciarBusqueda();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarNecesarios() {
        necesarioPanLabel.setText("Pan: " + (maximosIngredientes[0] - actualesIngredientes[0]));
        necesarioCarneLabel.setText("Carne: " + (maximosIngredientes[1] - actualesIngredientes[1]));
        necesarioLechugaLabel.setText("Lechuga: " + (maximosIngredientes[2] - actualesIngredientes[2]));
        necesarioQuesoLabel.setText("Queso: " + (maximosIngredientes[3] - actualesIngredientes[3]));
        necesarioTocinoLabel.setText("Tocino: " + (maximosIngredientes[4] - actualesIngredientes[4]));
        necesarioTomateLabel.setText("Tomate: " + (maximosIngredientes[5] - actualesIngredientes[5]));
    }

    private void resetearIngredientes() {
        actualesIngredientes = new int[maximosIngredientes.length];
        actualizarNecesarios();
    }

    private ImageIcon cargarImagenIngrediente(String rutaImagen) {
        try {
            Image imagen = ImageIO.read(new File(rutaImagen));
            Image scaledImagen = imagen.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImagen);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void actualizarEtiquetasInventario() {
        panLabel.setText("Pan: " + inventario.getPan());
        carneLabel.setText("Carne: " + inventario.getCarne());
        lechugaLabel.setText("Lechuga: " + inventario.getLechuga());
        quesoLabel.setText("Queso: " + inventario.getQueso());
        tocinoLabel.setText("Tocino: " + inventario.getTocino());
        tomateLabel.setText("Tomate: " + inventario.getTomate());
    }
}




























