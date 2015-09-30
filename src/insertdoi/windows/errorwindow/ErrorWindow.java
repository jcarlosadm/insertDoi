package insertdoi.windows.errorwindow;

import insertdoi.windows.progressbar.ProgressBar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ErrorWindow {
    private ErrorWindow() {}
    
    public static void run(String message){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message);
        
        ProgressBar proBar = ProgressBar.getInstance();
        if (proBar != null) {
            proBar.closeProgressBar();
        }
        
        frame.dispose();
        System.exit(0);
    }
}
