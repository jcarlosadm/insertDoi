package insertdoi.util.windows.finishWindow;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FinishWindow {
    
    private static final String MESSAGE = "Complete!";

    private FinishWindow() {}
    
    public static void run(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, MESSAGE);
        frame.dispose();
    }
}
