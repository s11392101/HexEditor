import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.JOptionPane;

public class HexEditor extends JFrame implements ActionListener {
  private JMenuBar menuBar = new JMenuBar();
  private JMenu fileMenu = new JMenu("File");
  private JMenuItem loadMenuItem = new JMenuItem("Load");
  private JMenuItem saveMenuItem = new JMenuItem("Save");
  private JTextArea messageArea = new JTextArea(5, 10);
  private JButton updateHex = new JButton("Update hex");
  private JLabel aLabel = new JLabel(" ");
  private JTextArea messageAreaR = new JTextArea(5,20);  
  private static final int EOF = -1;    
  private static String filename = null;
  
  public HexEditor(){
    setJMenuBar(menuBar);
    menuBar.add(fileMenu);
    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);
    loadMenuItem.addActionListener(this);
    saveMenuItem.addActionListener(this);
    updateHex.addActionListener(this);
    add(messageAreaR, BorderLayout.EAST);
    add(updateHex, BorderLayout.SOUTH);
    add(messageArea, BorderLayout.WEST);    
    add(aLabel, BorderLayout.CENTER);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();    
  }

  public void actionPerformed(ActionEvent ae){
    Object source = ae.getSource();
    if (source == loadMenuItem){
      do {
        filename = JOptionPane.showInputDialog(null, 
                        "Please enter the full path of a file:");
      } while(filename == null);
      
      try{
        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        int byteRead;
        while ((byteRead = is.read()) != EOF){
          if(byteRead != 13){            
            messageArea.append(String.valueOf((char)byteRead));
          }
        }
        is.close();
      }
      catch(IOException e){
        System.err.println("Caught IOException: " + e.getMessage());
      }
    } else if(source == updateHex){
      byte[] buffer = messageArea.getText().getBytes();
      messageAreaR.setText("");
      for(byte b: buffer){
        String hex = Integer.toHexString(b);
        if(hex.length() == 1){
          hex = "0" + hex;
        }
        messageAreaR.append(hex.toUpperCase() + " ");
      }
    } else if(source == saveMenuItem){
        while(filename == null){
          filename = JOptionPane.showInputDialog(null, 
                        "Please enter the full path of a file:");            
        }
        try{
          File file = new File(filename);
          OutputStream out = new FileOutputStream(file);
          byte[] buffer = messageArea.getText().getBytes();
          for(byte b : buffer) {
            out.write(b);
          }
        }
        catch(IOException e){
          System.err.println("Caught IOException: " + e.getMessage());
        }
    }
  }
}