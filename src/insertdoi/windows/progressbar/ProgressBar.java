package insertdoi.windows.progressbar;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressBar {
    private int totalOperations = 0;
    private int currentOperation = 0;
    
    private String title = "";
    private String message = "";
    
    private JFrame frame = null;
    private Container content = null;
    private JProgressBar jProgressBar = null;
    
    private static ProgressBar progressBarInstance = null;
    
    private ProgressBar(String title, String message) {
        this.title = title;
        this.message = message;
        
        this.frame = new JFrame(this.title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.content = this.frame.getContentPane();
        this.jProgressBar = new JProgressBar();
        this.jProgressBar.setValue(0);
        
        this.jProgressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder(this.message);
        this.jProgressBar.setBorder(border);
        this.content.add(this.jProgressBar, BorderLayout.NORTH);
        this.frame.setSize(300, 100);
        this.frame.setVisible(true);
    }
    
    public static synchronized ProgressBar getInstance(String title, String message){
        if (progressBarInstance == null) {
            progressBarInstance = new ProgressBar(title, message);
        } else {
            progressBarInstance.setTitle(title);
            progressBarInstance.setMessage(message);
        }
        
        return progressBarInstance;
    }
    
    public static synchronized ProgressBar getInstance(){
        if (progressBarInstance == null) {
            progressBarInstance = new ProgressBar("","");
        }
        
        return progressBarInstance;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public void clearOperations(){
        this.currentOperation = 0;
        this.totalOperations = 0;
    }
    
    public void defineTotalOperations(int total){
        this.totalOperations = total;
    }
    
    public void finishOneOperation(){
        this.currentOperation += 1;
        updateValueProgressBar();
    }
    
    private void updateValueProgressBar(){
        int value = (int)(this.currentOperation * 100.0)/this.totalOperations;
        this.jProgressBar.setValue(value);
    }
    
    public void closeProgressBar(){
        this.frame.dispose();
    }
}
