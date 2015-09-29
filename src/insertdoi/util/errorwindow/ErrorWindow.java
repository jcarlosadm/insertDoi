package insertdoi.util.errorwindow;

import insertdoi.util.progressbar.ProgressBar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ErrorWindow {
    private ErrorWindow() {}
    
    public static void run(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message);
        
        ProgressBar proBar = ProgressBar.getInstance();
        proBar.closeProgressBar();
        
        frame.dispose();
        System.exit(0);
    }
}
