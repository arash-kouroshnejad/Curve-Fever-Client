package UI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Navigable extends JFrame implements MouseListener {
    protected FrameController controller;
    protected final JPanel mainPanel;


    public Navigable() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        setBackground(Color.BLACK);
    }

    public void setController(FrameController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.select(e.getComponent().getName());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setForeground(Color.WHITE);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setForeground(Color.DARK_GRAY);
    }
}
