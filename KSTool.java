package korg.sample.tool;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.tree.TreeSelectionModel;



class KSTool extends JFrame {
    
    private JPanel contentPane;
    private JLabel picLabel;
    private BufferedImage myPicture = null;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu File = new JMenu("File");
    private JMenu Edit = new JMenu("Edit");
    private JMenu View = new JMenu("View");
    private JMenu Options = new JMenu("Options");
    private JMenu Help = new JMenu("Help");
    private JMenuItem openFile = new JMenuItem("Open File");
    private JMenuItem saveFile = new JMenuItem("Save Collection");
    private JMenuItem saveCollection = new JMenuItem("Save Collection as...");
    private JMenuItem clearAll = new JMenuItem("Clear All");
    private JMenuItem recentFiles = new JMenuItem("Recent Files");
    private JMenuItem exitMenu = new JMenuItem("Exit");
    private JMenuItem nuevoIns = new JMenuItem("Add New Instrument");
    private JMenuItem editItem = new JMenuItem("Edit item name");
    private JMenuItem deleteItem = new JMenuItem("Delete item");
    private JMenuItem runWaveform = new JMenuItem("Run Waveform Editor");
    private JMenuItem appProperties = new JMenuItem("Properties");
    private JMenuItem playAudio = new JMenuItem("Play Audio");
    private JMenuItem itemCount = new JMenuItem("Item Count");
    private JMenuItem appPreferences = new JMenuItem("Preferences");
    private JMenuItem AboutUs = new JMenuItem("About KSTool");
    private JSeparator jSeparator1 = new JSeparator();
    private JSeparator jSeparator2 = new JSeparator();  
    private JSeparator jSeparator3 = new JSeparator();   
    private JSeparator jSeparator4 = new JSeparator();
    private JSeparator jSeparator5 = new JSeparator(); 
    private JSeparator jSeparator6 = new JSeparator();    
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTree instrumentTree;
    private DefaultMutableTreeNode collection = new DefaultMutableTreeNode("Collection");
    private String metaImgFilepath = new String();
    private MetaFileHeader myHeader = new MetaFileHeader();
    private Multisample myMultisampleInfo = new Multisample(); 
    private int currentMultisample = 0;
    private int currentMultisampleDescription = 0;
    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";
    private static String CLEAR_COMMAND = "clear";
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem addIn = new JMenuItem("Add instrument");
    
    /*Nombre de la Aplicacion, Size, etc */
    public KSTool() {
        super("Korg Sample Tools");
        setDefaultCloseOperation(3);
        setBounds(100, 100, 800, 400);
    try
    {
      this.myPicture = ImageIO.read(getClass().getResource("icon.png"));
    }   
    catch (IOException e)
    {
      e.printStackTrace();
    }
    this.picLabel = new JLabel(new ImageIcon(this.myPicture));
    

        
        //Crea Collection
        //DefaultMutableTreeNode collection = new DefaultMutableTreeNode("Collection");  
        
        //Crea nodos de Instrumento 
        //DefaultMutableTreeNode instrumentNode = new DefaultMutableTreeNode("Instrumentos");
        //instrumentNode.add(new DefaultMutableTreeNode("Instrumentos"));
        
        //collection.add(instrumentNode);
        
        instrumentTree = new JTree(collection);
        add(instrumentTree);
        add(new JScrollPane(instrumentTree));
        instrumentTree.setEditable(true);
        instrumentTree.setPreferredSize(new Dimension(300, 150));
        //addIn.addActionListener(this);
        //addIn.addActionCommand("Add");
        popup.add(addIn);
        
        
        
        setJMenuBar(menuBar);
        
        menuBar.add(File);
        File.add(openFile);
        File.add(jSeparator1);
        File.add(saveFile);
        this.saveFile.setEnabled(false);
        File.add(saveCollection);
        this.saveCollection.setEnabled(false);
        File.add(jSeparator2);        
        File.add(clearAll);
        File.add(recentFiles);
        File.add(jSeparator3);        
        File.add(exitMenu);
        
        openFile.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0)
      { 
          JFileChooser fileChooser = new JFileChooser();
          int returnVal = fileChooser.showOpenDialog(null);
          if (returnVal == 0)
          {
           KSTool.this.metaImgFilepath = fileChooser.getSelectedFile().getPath();
           KSTool.this.myHeader.readMetaFileHeader(KSTool.this.metaImgFilepath);
           if (KSTool.this.myHeader.getIdKorg() != null)
           {
               System.out.println("Open File access granted");
               KSTool.this.currentMultisample = 0;
               KSTool.this.currentMultisampleDescription = 0;
               System.out.println("Instruments Samples correctly loaded");
               
               KSTool.this.saveFile.setEnabled(true);
               KSTool.this.saveCollection.setEnabled(true);
               System.out.println("Save Menus Activated");
               KSTool.this.runWaveform.setEnabled(true);
               System.out.println("Run Waveform Menu Activated");
               KSTool.this.appProperties.setEnabled(true);
               System.out.println("View - Properties Menu Activated");
               KSTool.this.playAudio.setEnabled(true);
               System.out.println("View - Play Audio Menu Activated");
               
               //Crea el nombre del archivo .ksc 
               DefaultMutableTreeNode collection = new DefaultMutableTreeNode("Collection");
               //Creacion y lectura de los instrumentos
               DefaultMutableTreeNode instrumentNode = new DefaultMutableTreeNode(KSTool.this.myMultisampleInfo.getMultisampleName());
               instrumentNode.add(new DefaultMutableTreeNode(KSTool.this.myMultisampleInfo.getMultisampleName()));
               collection.add(instrumentNode);
               
               KSTool.this.repaint();
           }
           else
           {
               System.out.println("File access canceled by user");
           }
          }
        }});
        
    exitMenu.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        System.exit(0);
      }
    });        
     
        
        menuBar.add(Edit);
        
        Edit.add(nuevoIns);
        nuevoIns.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
               int bank = 0;
               int nbProgram = 0;
               DefaultMutableTreeNode instrumentNode = new DefaultMutableTreeNode(bank + ":" + nbProgram++ + ":" + " " + "Instrument" );
               System.out.println("New Instrument added");
               KSTool.this.collection.add(instrumentNode);
            }
        });
        
        Edit.add(jSeparator4);
        Edit.add(editItem);
        editItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                
            }
        });
        
        Edit.add(deleteItem);
        deleteItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                instrumentTree.remove(nuevoIns);
                }
            }
        );
                   
        
        Edit.add(jSeparator5);
        Edit.add(runWaveform);
        this.runWaveform.setEnabled(false);
        
        menuBar.add(View);
        View.add(appProperties);
        this.appProperties.setEnabled(false);
        View.add(playAudio);
        this.playAudio.setEnabled(false);
        View.add(jSeparator6);
        View.add(itemCount);
        
        menuBar.add(Options);
        Options.add(appPreferences);
        menuBar.add(Help);
        Help.add(AboutUs);
        AboutUs.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent arg0)
                    {
                       JFrame aboutFrame = new JFrame("About Us");
                       aboutFrame.setResizable(false);
                       aboutFrame.getContentPane().setLayout(new GridLayout(0, 2));
                       aboutFrame.getContentPane().add(KSTool.this.picLabel);
                       
                       JLabel textLabel = new JLabel("Korg Sample Tools V0.5.5 (c) http://www.ks-tool.com 2014", 0);
                       aboutFrame.getContentPane().add(textLabel, "Center");
                       
                       aboutFrame.pack();
                       aboutFrame.setVisible(true);
                    }
                });
 
}    
    
    //Main : Inicio de Aplicación java
    public static void main(String [] args){
                try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KSTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KSTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KSTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KSTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
                
        /* Crea y Muestra en pantalla la Aplicación */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KSTool().setVisible(true);
            }
        });
    }
    
}
