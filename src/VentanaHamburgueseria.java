import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class VentanaHamburgueseria {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Simulación del Algoritmo del Banquero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 525);

        Inventario inventario = new Inventario();

        JPanel panelConFondo = new JPanel() {
            BufferedImage image;

            {
                try {
                    image = ImageIO.read(new File("imagenes/Comida.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                setBackground(Color.LIGHT_GRAY);

                if (image != null) {

                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panelConFondo.setBackground(Color.LIGHT_GRAY);

        panelConFondo.setLayout(new BorderLayout());

        JLabel etiqueta = new JLabel("¡Bienvenido a la venta de hamburguesas!");
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        etiqueta.setForeground(Color.BLACK);
        panelConFondo.add(etiqueta, BorderLayout.NORTH);

        JPanel panelInventario = new JPanel();
        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));
        panelInventario.setBorder(BorderFactory.createTitledBorder("Disponible"));
        panelInventario.setBackground(Color.WHITE);

        JLabel panLabel = new JLabel("Pan: " + inventario.getPan());
        JLabel carneLabel = new JLabel("Carne: " + inventario.getCarne());
        JLabel lechugaLabel = new JLabel("Lechuga: " + inventario.getLechuga());
        JLabel quesoLabel = new JLabel("Queso: " + inventario.getQueso());
        JLabel tocinoLabel = new JLabel("Tocino: " + inventario.getTocino());
        JLabel tomateLabel = new JLabel("Tomate: " + inventario.getTomate());

        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));
        panelInventario.add(panLabel);
        panelInventario.add(carneLabel);
        panelInventario.add(lechugaLabel);
        panelInventario.add(quesoLabel);
        panelInventario.add(tocinoLabel);
        panelInventario.add(tomateLabel);

        JPanel panelInventarioWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInventarioWrapper.add(panelInventario);
        panelInventarioWrapper.setOpaque(false);

        panelInventarioWrapper.setBounds(540, 0, 150, 200);
        JPanel panelMaximos = new JPanel();
        panelMaximos.setLayout(new BoxLayout(panelMaximos, BoxLayout.Y_AXIS));
        panelMaximos.setBorder(BorderFactory.createTitledBorder("Máximo"));
        panelMaximos.setBackground(Color.WHITE);

        JLabel maxPanLabel = new JLabel("Pan: 2");
        JLabel maxCarneLabel = new JLabel("Carne: 3");
        JLabel maxLechugaLabel = new JLabel("Lechuga: 5");
        JLabel maxQuesoLabel = new JLabel("Queso: 4");
        JLabel maxTocinoLabel = new JLabel("Tocino: 2");
        JLabel maxTomateLabel = new JLabel("Tomate: 2");

        panelMaximos.add(maxPanLabel);
        panelMaximos.add(maxCarneLabel);
        panelMaximos.add(maxLechugaLabel);
        panelMaximos.add(maxQuesoLabel);
        panelMaximos.add(maxTocinoLabel);
        panelMaximos.add(maxTomateLabel);

        JPanel panelMaximosWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMaximosWrapper.add(panelMaximos);
        panelMaximosWrapper.setOpaque(false);

        panelMaximosWrapper.setBounds(10, 0, 150, 200);

        JPanel panelNecesarios = new JPanel();
        panelNecesarios.setLayout(new BoxLayout(panelNecesarios, BoxLayout.Y_AXIS));
        panelNecesarios.setBorder(BorderFactory.createTitledBorder("Necesarios"));
        panelNecesarios.setBackground(Color.WHITE);

        JLabel necesarioPanLabel = new JLabel("Pan: 2");
        JLabel necesarioCarneLabel = new JLabel("Carne: 3");
        JLabel necesarioLechugaLabel = new JLabel("Lechuga: 5");
        JLabel necesarioQuesoLabel = new JLabel("Queso: 4");
        JLabel necesarioTocinoLabel = new JLabel("Tocino: 2");
        JLabel necesarioTomateLabel = new JLabel("Tomate: 2");

        panelNecesarios.add(necesarioPanLabel);
        panelNecesarios.add(necesarioCarneLabel);
        panelNecesarios.add(necesarioLechugaLabel);
        panelNecesarios.add(necesarioQuesoLabel);
        panelNecesarios.add(necesarioTocinoLabel);
        panelNecesarios.add(necesarioTomateLabel);

        panelConFondo.add(panelNecesarios, BorderLayout.SOUTH);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(700, 495));

        panelConFondo.setBounds(0, 0, 700, 495);
        layeredPane.add(panelConFondo, JLayeredPane.DEFAULT_LAYER);

        layeredPane.add(panelInventarioWrapper, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(panelMaximosWrapper, JLayeredPane.PALETTE_LAYER);

        JLabel cocinero1Label = new JLabel();
        try {
            ImageIcon cocinero1Image = new ImageIcon(ImageIO.read(new File("imagenes/cocinero2.png"))); // Ruta del cocinero1
            Image scaledCocinero1Image = cocinero1Image.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
            cocinero1Label.setIcon(new ImageIcon(scaledCocinero1Image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cocinero1Label.setBounds(240, 250, 85, 85);
        layeredPane.add(cocinero1Label, JLayeredPane.PALETTE_LAYER);

        int[] maximosIngredientes = {2, 3, 5, 4, 2, 2};
        ChefInteractivo chefInteractivo = new ChefInteractivo(inventario, panLabel, carneLabel, lechugaLabel, quesoLabel, tocinoLabel, tomateLabel,
                necesarioPanLabel, necesarioCarneLabel, necesarioLechugaLabel, necesarioQuesoLabel, necesarioTocinoLabel, necesarioTomateLabel,
                maximosIngredientes);
        chefInteractivo.setBounds(325, 250, 85, 85);
        layeredPane.add(chefInteractivo, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);

        chefInteractivo.iniciarBusqueda();
    }
}

























