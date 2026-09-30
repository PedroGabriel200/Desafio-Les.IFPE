import view.SwitchScreen;
import javax.swing.*;
import java.awt.*;

public class main {

    public static void main(String[]args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame=new JFrame("LES.IFPE • Sistema de Vendas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(900,650)); frame.setSize(1120,760); frame.setLocationRelativeTo(null);
            frame.setContentPane(new SwitchScreen(panel -> {
                frame.setContentPane(panel);
                frame.revalidate();
                frame.repaint();
            }));
            frame.setVisible(true);
        });
    }
}
