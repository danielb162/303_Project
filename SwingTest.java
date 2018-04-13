// Class made to experiment with the Swing library 

// Used for Swing/display:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingTest {
  private class ButtonClickListener implements ActionListener {
    public void actionPerformed( ActionEvent e) {
      String command = e.getActionCommand();
      
      if( command.equals( "OK" ) ) {
        statusLabel.setText("Ok button clicked!");
      }
      else if ( command.equals( "Submit" ) ) {
        statusLabel.setText("Submit Button clicked!");
      }
      else statusLabel.setText("Cancel Button clicked!");
    }
  }
  
  
  private JFrame mainFrame; // The window
  private JLabel headerLabel; // Displays text
  private JLabel statusLabel; // Ditto
  private JPanel controlPanel; // Panel (container) which can be populated
  
  public SwingTest(){
    prepareGUI();
  }
  
  private void prepareGUI(){
    mainFrame = new JFrame("Java Swing Examples");
    mainFrame.setSize(400, 400);
    mainFrame.setLayout(new GridLayout(3,1)); // Have mainFrame have 3 columns, 1 row
    
    headerLabel = new JLabel("", JLabel.CENTER);
    statusLabel = new JLabel("", JLabel.CENTER);
    statusLabel.setSize(350, 100);
    
    // Anonymous WindowEvent to instruct mainFrame what to do on close
    mainFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent wE){
        System.exit(0);
      }
    });
    
    // Define a container area
    controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());
    
    // Populate the window
    mainFrame.add(headerLabel);
    mainFrame.add(controlPanel);
    mainFrame.add(statusLabel);
  }
  
  private void showDemo(){
    headerLabel.setText("Control in action: Button");
    
    // Defines buttons
    JButton okButton = new JButton("One");
    JButton submitButton = new JButton("Two");
    JButton cancelButton = new JButton("Three");
    
    // Callback functions
    okButton.setActionCommand("OK");
    submitButton.setActionCommand("Submit");
    cancelButton.setActionCommand("Cancel");
    
    // Defines listeners, callback is 'setActionCommand'
    okButton.addActionListener( new ButtonClickListener() );
    submitButton.addActionListener( new ButtonClickListener() );
    cancelButton.addActionListener( new ButtonClickListener() );
    
    // Populate panel as a seperate area within the JFrame
    controlPanel.add(okButton);
    controlPanel.add(submitButton);
    controlPanel.add(cancelButton);
    
    // 'Activate' window so that it is visible
    mainFrame.setVisible(true);
  }
  
  
  public static void main(String[] args){
    SwingTest swingDemo = new SwingTest();
    swingDemo.showDemo();
  }
    
}

