import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class KromeSampleTool
  extends JFrame
{
  private JPanel contentPane;
  private MetaFileHeader myHeader = new MetaFileHeader();
  private Multisample myMultisampleInfo = new Multisample();
  private MultisampleDescription myMultisampleDescription = new MultisampleDescription();
  private Waveform myWaveform = new Waveform();
  private KSC myKSC = new KSC();
  private KMP myKMP = new KMP();
  private KSF myKSF = new KSF();
  private int currentMultisample = 0;
  private int currentMultisampleDescription = 0;
  private int currentWaveform = 0;
  private String metaImgFilepath = new String();
  private String KSCFilepath = new String();
  private String KSFFilepath = new String();
  private String KMPFilepath = new String();
  private JMenuItem mntmExtractSamples = new JMenuItem("Extract Samples from SAMPLE.IMG");
  private JMenuItem mntmExtractSamplesFrom = new JMenuItem("Extract Samples from SAMPLE.IMG (with original names)");
  private String sampleDirPath = new String();
  private String outputMetaFilePath = new String();
  private String outputSampleFilePath = new String();
  private int nbSampleInDirPath = 0;
  File[] listOfFiles;
  private Waveform[] myWaveformList;
  private KSF [] myKSFList;
  private KMP [] myKMPList;
  private KSC [] myKSClist;
  private MultisampleDescription[] myMSDList;
  private Multisample[] myMSList;
  private MetaFileHeader myMetaFileHeader = new MetaFileHeader();
  private GridLayout myGridLayout82 = new GridLayout(8, 2);
  private GridLayout myGridLayout03 = new GridLayout(0, 3);
  private JMenuItem mntmHeader = new JMenuItem("Header");
  private JLabel lblTitle = new JLabel("Korg Meta Header");
  private JLabel lblTitle2 = new JLabel("Values");
  private JLabel lblId = new JLabel("Id");
  private JLabel lblVer = new JLabel("Version");
  private JLabel lblNbMultiSample = new JLabel("NbMultisamples");
  private JLabel lblNbMultiSampleDescription = new JLabel("NbMultisamplesDescription");
  private JLabel lblNbWaveformDescription = new JLabel("NbWaveformDescription");
  private JTextField textId = new JTextField();
  private JTextField textVer = new JTextField();
  private JTextField textNbs = new JTextField();
  private JTextField textNbsd = new JTextField();
  private JTextField textNbwfd = new JTextField();
  private JMenuItem mntmMultisamples = new JMenuItem("Multisamples General");
  private JLabel lblTitle3 = new JLabel("Multisample Number");
  private JLabel lblTitle4 = new JLabel(String.valueOf(this.currentMultisample));
  private JLabel lblMultisampleName = new JLabel("MultisampleName");
  private JLabel lblMdsn = new JLabel("multisampleDescriptionStartNum");
  private JLabel lblUnk = new JLabel("Unknown (1)");
  private JLabel lblNbMsd = new JLabel("nbMultisampleDescription");
  private JTextField textMultisampleName = new JTextField();
  private JTextField textMdsn = new JTextField();
  private JTextField textNbMsd = new JTextField();
  private JTextField textUnk = new JTextField();
  private Button buttonPreviousMS = new Button("Previous");
  private Button buttonNextMS = new Button("Next");
  private Button buttonGotoMD = new Button("Goto Multisample Info");
  private JMenuItem mntmMultisamplesdescription = new JMenuItem("Multisamples Description");
  private JLabel lblTitle5 = new JLabel("MultisampleDescription Number");
  private JLabel lblTitle6 = new JLabel(String.valueOf(this.currentMultisampleDescription));
  private JLabel lblTitle7 = new JLabel("");
  private JLabel lblsampleNumber = new JLabel("sampleNumber");
  private JLabel lblUnknownAlways2 = new JLabel("Unknown (2)");
  private JLabel lblkeyUp = new JLabel("Top Key");
  private JLabel lblkeyUpPic;
  private JLabel lblkeyDown = new JLabel("Original Key");
  private JLabel lblkeyDownPic;
  private JLabel lblNosample = new JLabel("No associated sample!");
  private JLabel lblTune = new JLabel("Tune");
  private JLabel lblLevel = new JLabel("Level");
  private JLabel lblPan = new JLabel("Pan");
  private JLabel lblFilter = new JLabel("FilterCutOff");
  private JTextField textsampleNumber = new JTextField();
  private JTextField textUnknownAlways2 = new JTextField();
  private JTextField textkeyUp = new JTextField();
  private JTextField textkeyDown = new JTextField();
  private JTextField textTune = new JTextField();
  private JTextField textLevel = new JTextField();
  private JTextField textPan = new JTextField();
  private JTextField textFilter = new JTextField();
  private Button buttonPreviousMSD = new Button("Previous");
  private Button buttonNextMSD = new Button("Next");
  private Button buttonGotoWF = new Button("Goto waveform Info");
  private JMenuItem mntmWaveformdescription = new JMenuItem("Waveforms Description");
  private JLabel lblTitle8 = new JLabel("Waveform Number");
  private JLabel lblTitle9 = new JLabel("");
  private JLabel lblsampleName = new JLabel("Sample Name");
  private JTextField textsampleName = new JTextField();
  private JLabel lblTitle11 = new JLabel("");
  private Button buttonPreviousWF = new Button("Previous");
  private Button buttonNextWF = new Button("Next");
  private Button buttonExtractWF = new Button("Extract From SAMPLE.IMG");
  private JLabel lblWaveformPic;
  private BufferedImage myPicture = null;
  private JLabel picLabel;
  private BufferedImage myPictureKeyDown = null;
  private BufferedImage myPictureKeyUp = null;
  private JLabel lblSelectDirectory = new JLabel("Select your samples directory :");
  private JTextField tfSelectedDirectory = new JTextField("");
  private Button buttonSelectDirectory = new Button("Select");
  private Button buttonGenerate = new Button("Generate");
  private BufferedImage myPictureEasy = null;
  private JLabel lblGenerated = new JLabel("");
  private JLabel lblEasyPic;
  JRadioButton firstChoiceButton = new JRadioButton("One sample per key", true);
  JRadioButton secondChoiceButton = new JRadioButton("One sample per multisample", false);
  
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          KromeSampleTool frame = new KromeSampleTool();
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
  
  private void displayMultisampleDescription()
  {
    this.myMultisampleDescription.readMultisampleDescription(this.metaImgFilepath, this.myHeader.getNbMultisample(), this.currentMultisampleDescription);
    
    this.contentPane.removeAll();
    this.contentPane.setLayout(this.myGridLayout03);
    
    this.lblTitle6.setText(String.valueOf(this.currentMultisampleDescription));
    

    Multisample myMs = new Multisample();
    String myPreviousName = new String("");
    for (int i = 0; i < this.myHeader.getNbMultisample(); i++)
    {
      myMs.readMultisampleInfo(this.metaImgFilepath, i);
      if (myMs.getMultisampleDescriptionStartNum() > this.currentMultisampleDescription) {
        break;
      }
      myPreviousName = myMs.getMultisampleName();
    }
    this.lblTitle7.setText("From : " + myPreviousName);
    
    this.textsampleNumber.setText(String.valueOf(this.myMultisampleDescription.getSampleNumber()));
    

    this.textkeyUp.setText(String.valueOf(this.myMultisampleDescription.getkeyUp()));
    

    this.textkeyDown.setText(String.valueOf(this.myMultisampleDescription.getkeyDown()));
    

    this.textUnknownAlways2.setText(String.valueOf(this.myMultisampleDescription.getunknown()));
    this.textTune.setText(String.valueOf(this.myMultisampleDescription.getTune()));
    this.textLevel.setText(String.valueOf(this.myMultisampleDescription.getLevel()));
    this.textPan.setText(String.valueOf(this.myMultisampleDescription.getPan()));
    this.textFilter.setText(String.valueOf(this.myMultisampleDescription.getFilter()));
    if (this.myMultisampleDescription.getSampleNumber() == -1) {
      this.lblNosample.setText("No associated sample!");
    } else {
      this.lblNosample.setText("");
    }
    this.contentPane.add(this.lblTitle5);
    this.contentPane.add(this.lblTitle6);
    this.contentPane.add(this.lblTitle7);
    this.contentPane.add(this.lblsampleNumber);
    this.contentPane.add(this.textsampleNumber);
    if (this.myMultisampleDescription.getSampleNumber() == -1)
    {
      this.lblNosample.setText("No associated sample!");
      this.contentPane.add(this.lblNosample, "Center");
    }
    else
    {
      this.contentPane.add(this.buttonGotoWF);
    }
    this.contentPane.add(this.lblUnknownAlways2);
    this.contentPane.add(this.textUnknownAlways2);
    this.contentPane.add(new JLabel());
    
    this.contentPane.add(this.lblkeyUp);
    this.contentPane.add(this.textkeyUp);
    this.contentPane.add(this.lblkeyUpPic);
    
    this.contentPane.add(this.lblkeyDown);
    this.contentPane.add(this.textkeyDown);
    this.contentPane.add(this.lblkeyDownPic);
    
    this.contentPane.add(this.lblTune);
    this.contentPane.add(this.textTune);
    this.contentPane.add(new JLabel());
    
    this.contentPane.add(this.lblLevel);
    this.contentPane.add(this.textLevel);
    this.contentPane.add(new JLabel());
    
    this.contentPane.add(this.lblPan);
    this.contentPane.add(this.textPan);
    this.contentPane.add(new JLabel());
    
    this.contentPane.add(this.lblFilter);
    this.contentPane.add(this.textFilter);
    this.contentPane.add(new JLabel());
    
    this.contentPane.add(this.buttonPreviousMSD);
    this.contentPane.add(this.buttonNextMSD);
    
    this.contentPane.revalidate();
    
    repaint();
  }
  
  private void displayWaveformDescription()
  {
    this.myWaveform.readWaveformDescription(this.metaImgFilepath, 
      this.myHeader.getNbMultisample(), 
      this.myHeader.getNbMultisampleDescription(), 
      this.currentWaveform);
    if (this.myWaveform.getSamplesImage() == null) {
      this.lblWaveformPic = new JLabel("No preview available from SAMPLE.IMG");
    } else {
      this.lblWaveformPic = new JLabel(new ImageIcon(this.myWaveform.getSamplesImage()));
    }
    this.contentPane.removeAll();
    
    this.contentPane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    
    this.lblTitle9.setText(String.valueOf(this.currentWaveform));
    this.textsampleName.setText(this.myWaveform.getSampleName());
    


    gbc.fill = 0;
    gbc.anchor = 512;
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    this.contentPane.add(this.lblTitle8, gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    this.contentPane.add(this.lblTitle9, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    this.contentPane.add(this.lblWaveformPic, gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    
    this.contentPane.add(this.lblsampleName, gbc);
    gbc.gridx = 2;
    gbc.gridy = 1;
    
    gbc.weightx = 1.0D;
    
    this.textsampleName.setPreferredSize(new Dimension(200, 25));
    this.textsampleName.setMinimumSize(new Dimension(200, 25));
    this.textsampleName.setMaximumSize(new Dimension(200, 25));
    
    this.contentPane.add(this.textsampleName, gbc);
    gbc.gridx = 0;
    gbc.gridy = 2;
    this.contentPane.add(this.buttonExtractWF, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    this.contentPane.add(this.buttonPreviousWF, gbc);
    gbc.gridx = 2;
    gbc.gridy = 2;
    this.contentPane.add(this.buttonNextWF, gbc);
    


    this.contentPane.revalidate();
    
    repaint();
  }
  
  private void displayCreateEasyModeGUI()
  {
    this.contentPane.removeAll();
    this.contentPane.setLayout(new GridBagLayout());
    
    JLabel title = new JLabel("Easy Creation Mode");
    JLabel icon = new JLabel("logo");
    this.lblGenerated.setText("");
    
    icon.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    icon.setHorizontalAlignment(0);
    icon.setPreferredSize(new Dimension(128, 128));
    
    JSeparator separator = new JSeparator();
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = (gbc.gridy = 0);
    gbc.gridwidth = 0;
    gbc.insets = new Insets(10, 5, 0, 0);
    gbc.anchor = 512;
    this.contentPane.add(title, gbc);
    
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 4;
    gbc.anchor = 21;
    gbc.insets = new Insets(5, 5, 0, 0);
    this.contentPane.add(this.lblEasyPic, gbc);
    
    gbc.gridx = (gbc.gridy = gbc.gridwidth = gbc.gridheight = 1);
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 5, 0, 0);
    this.contentPane.add(this.lblSelectDirectory, gbc);
    
    gbc.gridx = 2;
    gbc.gridwidth = -1;
    gbc.weightx = 1.0D;
    gbc.fill = 2;
    gbc.anchor = 256;
    gbc.insets = new Insets(3, 5, 0, 0);
    this.contentPane.add(this.tfSelectedDirectory, gbc);
    
    gbc.gridx = 4;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(this.buttonSelectDirectory, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(new JLabel("Pack Type "), gbc);
    

    ButtonGroup bgroup = new ButtonGroup();
    bgroup.add(this.firstChoiceButton);
    bgroup.add(this.secondChoiceButton);
    
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(this.firstChoiceButton, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(this.secondChoiceButton, gbc);
    
    gbc.gridy = 5;
    gbc.gridx = 0;
    gbc.anchor = 10;
    gbc.fill = 2;
    gbc.insets = new Insets(3, 5, 0, 5);
    this.contentPane.add(separator, gbc);
    
    gbc.gridy = 6;
    gbc.gridheight = 0;
    gbc.weighty = 1.0D;
    gbc.fill = 0;
    gbc.anchor = 768;
    gbc.insets = new Insets(3, 0, 5, 5);
    this.contentPane.add(this.buttonGenerate, gbc);
    
    gbc.gridy = 7;
    
    gbc.weighty = 1.0D;
    gbc.fill = 0;
    gbc.anchor = 768;
    gbc.insets = new Insets(3, 0, 5, 5);
    this.contentPane.add(this.lblGenerated, gbc);
    
    this.contentPane.revalidate();
    repaint();
  }
  
  private void displayConvertKSCModeGUI()
  {
    this.contentPane.removeAll();
    this.contentPane.setLayout(new GridBagLayout());
    
    JLabel title = new JLabel("KSC Convert Mode");
    JLabel icon = new JLabel("logo");
    this.lblGenerated.setText("");
    
    icon.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    icon.setHorizontalAlignment(0);
    icon.setPreferredSize(new Dimension(128, 128));
    
    JSeparator separator = new JSeparator();
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = (gbc.gridy = 0);
    gbc.gridwidth = 0;
    gbc.insets = new Insets(10, 5, 0, 0);
    gbc.anchor = 512;
    this.contentPane.add(title, gbc);
    
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 4;
    gbc.anchor = 21;
    gbc.insets = new Insets(5, 5, 0, 0);
    this.contentPane.add(this.lblEasyPic, gbc);
    
    gbc.gridx = (gbc.gridy = gbc.gridwidth = gbc.gridheight = 1);
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 5, 0, 0);
    this.contentPane.add(this.lblSelectDirectory, gbc);
    
    gbc.gridx = 2;
    gbc.gridwidth = -1;
    gbc.weightx = 1.0D;
    gbc.fill = 2;
    gbc.anchor = 256;
    gbc.insets = new Insets(3, 5, 0, 0);
    this.contentPane.add(this.tfSelectedDirectory, gbc);
    
    gbc.gridx = 4;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(this.buttonSelectDirectory, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 0;
    gbc.weightx = 0.0D;
    gbc.fill = 0;
    gbc.anchor = 512;
    gbc.insets = new Insets(0, 3, 0, 5);
    this.contentPane.add(new JLabel("Pack Type "), gbc);
    
       
    gbc.gridy = 5;
    gbc.gridx = 0;
    gbc.anchor = 10;
    gbc.fill = 2;
    gbc.insets = new Insets(3, 5, 0, 5);
    this.contentPane.add(separator, gbc);
    
    gbc.gridy = 6;
    gbc.gridheight = 0;
    gbc.weighty = 1.0D;
    gbc.fill = 0;
    gbc.anchor = 768;
    gbc.insets = new Insets(3, 0, 5, 5);
    this.contentPane.add(this.buttonGenerate, gbc);
    
    gbc.gridy = 7;
    
    gbc.weighty = 1.0D;
    gbc.fill = 0;
    gbc.anchor = 768;
    gbc.insets = new Insets(3, 0, 5, 5);
    this.contentPane.add(this.lblGenerated, gbc);
    
    this.contentPane.revalidate();
    repaint();
  }
  
  private void listRawSampleFilesInDir(String path)
  {
    File folder = new File(path);
    this.listOfFiles = folder.listFiles();
    
    this.nbSampleInDirPath = 0;
    for (int i = 0; i < this.listOfFiles.length; i++) {
      if (this.listOfFiles[i].isFile())
      {
        String files = this.listOfFiles[i].getName();
        if ((files.endsWith(".raw")) || (files.endsWith(".RAW")))
        {
          System.out.println("Found : " + this.listOfFiles[i].getPath());
          this.nbSampleInDirPath += 1;
        }
      }
    }
  }
      
  
  private void generateMetaAndSampleFiles()
  {
    this.myMetaFileHeader.setIdKorg("KORG");
    this.myMetaFileHeader.setVersion("0001");
    this.myMetaFileHeader.setUnknown1(0);
    this.myMetaFileHeader.setNbMultisample(1);
    this.myMetaFileHeader.setUnknown2(0);
    this.myMetaFileHeader.setNbMultisampleDescription(this.nbSampleInDirPath);
    this.myMetaFileHeader.setNbWaveformDescription(this.nbSampleInDirPath);
    

    this.outputMetaFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "META.IMG");
    this.outputSampleFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "SAMPLE.IMG");
    

    this.myMetaFileHeader.writeMetaFileHeader(this.outputMetaFilePath);
    

    this.myMSList = new Multisample[1];
    
    this.myMSList[0] = new Multisample();
    this.myMSList[0].setMultisampleDescriptionStartNum((short)0);
    this.myMSList[0].setMultisampleName("KromeSampleTool SAMPLE");
    this.myMSList[0].setNbMultisampleDescription((byte)this.nbSampleInDirPath);
    this.myMSList[0].setUnknownAlways1((byte)1);
    

    this.myMSList[0].writeMultisampleInfo(this.outputMetaFilePath, 0);
    

    this.myMSDList = new MultisampleDescription[this.nbSampleInDirPath];
    for (int i = 0; i < this.nbSampleInDirPath; i++)
    {
      this.myMSDList[i] = new MultisampleDescription();
      this.myMSDList[i].setSampleNumber((short)i);
      this.myMSDList[i].setkeyDown((byte)(36 + i));
      this.myMSDList[i].setkeyUp((byte)(36 + i));
      this.myMSDList[i].setunknown((byte)2);
      this.myMSDList[i].setUnknown2to0();
      this.myMSDList[i].writeMultisampleDescription(this.outputMetaFilePath, 1, i);
    }
    this.myWaveformList = new Waveform[this.nbSampleInDirPath];
    
    int offsetAddress = 0;
    

    int i = 0;
    for (int j = 0; j < this.listOfFiles.length; j++) {
      if (this.listOfFiles[j].isFile())
      {
        String files = this.listOfFiles[j].getName();
        if ((files.endsWith(".raw")) || (files.endsWith(".RAW")))
        {
          System.out.println("Processing : " + this.listOfFiles[j].getPath());
          
          this.myWaveformList[i] = new Waveform();
          this.myWaveformList[i].setSampleName("SAMPLE NUMBER " + i);
          this.myWaveformList[i].setUnknown1(855703364);
          this.myWaveformList[i].setUnknown2(-871861776);
          this.myWaveformList[i].setStartAddress1(offsetAddress | 0x83000000);
          this.myWaveformList[i].setStartAddress2(offsetAddress);
          this.myWaveformList[i].setStartAddress3(offsetAddress | 0x3000000);
          
          this.myWaveformList[i].setEndAddress1(offsetAddress + (int)this.listOfFiles[j].length() - 1 | 0x3000000);
          this.myWaveformList[i].setUnknown3(50331648);
          this.myWaveformList[i].setSampleAddress(50331648);
          this.myWaveformList[i].setUnknown4(50331648);
          this.myWaveformList[i].setUnknown5(50331648);
          this.myWaveformList[i].setUnknown6(50331648);
          this.myWaveformList[i].setUnknown7(50331648);
          this.myWaveformList[i].setUnknown8(50331648);
          this.myWaveformList[i].setUnknown9(50331648);
          this.myWaveformList[i].setUnknown10(0);
          this.myWaveformList[i].setUnknown11(50331648);
          this.myWaveformList[i].setSampleSizeX2((int)this.listOfFiles[j].length() * 2);
          
          this.myWaveformList[i].writeWaveformDescription(this.outputMetaFilePath, 1, this.nbSampleInDirPath, i);
          
          this.myWaveformList[i].readWaveformSamplesFrom(this.listOfFiles[j].getPath());
          
          this.myWaveformList[i].writeWaveformSamplesToSampleIMG(this.outputSampleFilePath);
          
          offsetAddress = (int)(offsetAddress + this.listOfFiles[j].length());
          
          i++;
        }
      }
    }
    if (16777216 - offsetAddress - 1 - 1 >= 4)
    {
      this.myWaveformList[0].writePaddingToSampleIMG(this.outputSampleFilePath, offsetAddress - 1);
      this.lblGenerated.setText("Generation done with " + String.valueOf(this.nbSampleInDirPath) + " Sample(s)!");
    }
    else
    {
      File myFile = new File(this.outputSampleFilePath);
      File myFile2 = new File(this.outputMetaFilePath);
      myFile.delete();
      myFile2.delete();
      this.lblGenerated.setText("Generation cannot be done (SAMPLE.IMG cannot exceed 16 MB)");
    }
  }
  
  private void generateMetaAndSampleFilesInstrument()
  {
    this.myMetaFileHeader.setIdKorg("KORG");
    this.myMetaFileHeader.setVersion("0001");
    this.myMetaFileHeader.setUnknown1(0);
    this.myMetaFileHeader.setNbMultisample(this.nbSampleInDirPath);
    this.myMetaFileHeader.setUnknown2(0);
    this.myMetaFileHeader.setNbMultisampleDescription(this.nbSampleInDirPath);
    this.myMetaFileHeader.setNbWaveformDescription(this.nbSampleInDirPath);
    

    this.outputMetaFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "META.IMG");
    this.outputSampleFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "SAMPLE.IMG");
    

    this.myMetaFileHeader.writeMetaFileHeader(this.outputMetaFilePath);
    

    this.myMSList = new Multisample[this.nbSampleInDirPath];
    for (int i = 0; i < this.nbSampleInDirPath; i++)
    {
      this.myMSList[i] = new Multisample();
      this.myMSList[i].setMultisampleDescriptionStartNum((short)i);
      this.myMSList[i].setMultisampleName("KromeSampleTool SAMPLE " + i);
      this.myMSList[i].setNbMultisampleDescription((byte)1);
      this.myMSList[i].setUnknownAlways1((byte)1);
      
      this.myMSList[i].writeMultisampleInfo(this.outputMetaFilePath, i);
    }
    this.myMSDList = new MultisampleDescription[this.nbSampleInDirPath];
    for (int i = 0; i < this.nbSampleInDirPath; i++)
    {
      this.myMSDList[i] = new MultisampleDescription();
      this.myMSDList[i].setSampleNumber((short)i);
      this.myMSDList[i].setkeyDown((byte)60);
      this.myMSDList[i].setkeyUp((byte)127);
      this.myMSDList[i].setunknown((byte)2);
      this.myMSDList[i].setFilterto0();
      this.myMSDList[i].writeMultisampleDescription(this.outputMetaFilePath, this.nbSampleInDirPath, i);
    }
    this.myWaveformList = new Waveform[this.nbSampleInDirPath];
    
    int offsetAddress = 0;
    

    int i = 0;
    for (int j = 0; j < this.listOfFiles.length; j++) {
      if (this.listOfFiles[j].isFile())
      {
        String files = this.listOfFiles[j].getName();
        if ((files.endsWith(".raw")) || (files.endsWith(".RAW")))
        {
          System.out.println("Processing : " + this.listOfFiles[j].getPath());
          
          this.myWaveformList[i] = new Waveform();
          this.myWaveformList[i].setSampleName("SAMPLE NUMBER " + i);
          this.myWaveformList[i].setUnknown1(855703364);
          this.myWaveformList[i].setUnknown2(-871861776);
          this.myWaveformList[i].setStartAddress1(offsetAddress | 0x83000000);
          this.myWaveformList[i].setStartAddress2(offsetAddress);
          this.myWaveformList[i].setStartAddress3(offsetAddress | 0x3000000);
          
          this.myWaveformList[i].setEndAddress1(offsetAddress + (int)this.listOfFiles[j].length() - 1 | 0x3000000);
          this.myWaveformList[i].setUnknown3(50331648);
          this.myWaveformList[i].setSampleAddress(50331648);
          this.myWaveformList[i].setUnknown4(50331648);
          this.myWaveformList[i].setUnknown5(50331648);
          this.myWaveformList[i].setUnknown6(50331648);
          this.myWaveformList[i].setUnknown7(50331648);
          this.myWaveformList[i].setUnknown8(50331648);
          this.myWaveformList[i].setUnknown9(50331648);
          this.myWaveformList[i].setUnknown10(0);
          this.myWaveformList[i].setUnknown11(50331648);
          this.myWaveformList[i].setSampleSizeX2((int)this.listOfFiles[j].length() * 2);
          
          this.myWaveformList[i].writeWaveformDescription(this.outputMetaFilePath, this.nbSampleInDirPath, this.nbSampleInDirPath, i);
          
          this.myWaveformList[i].readWaveformSamplesFrom(this.listOfFiles[j].getPath());
          
          this.myWaveformList[i].writeWaveformSamplesToSampleIMG(this.outputSampleFilePath);
          
          offsetAddress = (int)(offsetAddress + this.listOfFiles[j].length());
          
          i++;
        }
      }
    }
    if (16777216 - offsetAddress - 1 - 1 >= 4)
    {
      this.myWaveformList[0].writePaddingToSampleIMG(this.outputSampleFilePath, offsetAddress - 1);
      this.lblGenerated.setText("Generation done with " + String.valueOf(this.nbSampleInDirPath) + " Sample(s)!");
    }
    else
    {
      File myFile = new File(this.outputSampleFilePath);
      File myFile2 = new File(this.outputMetaFilePath);
      myFile.delete();
      myFile2.delete();
      this.lblGenerated.setText("Generation cannot be done (SAMPLE.IMG cannot exceed 16 MB)");
    }
  }
  
  
  private void generateMetaAndSampleFilesInstrument_2()
  {
    this.myMetaFileHeader.setIdKorg("KORG");
    this.myMetaFileHeader.setVersion("0001");
    this.myMetaFileHeader.setUnknown1(0);
    this.myMetaFileHeader.setNbMultisample(this.nbSampleInDirPath);
    this.myMetaFileHeader.setUnknown2(0);
    this.myMetaFileHeader.setNbMultisampleDescription(this.nbSampleInDirPath);
    this.myMetaFileHeader.setNbWaveformDescription(this.nbSampleInDirPath);
    

    this.outputMetaFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "META.IMG");
    this.outputSampleFilePath = this.listOfFiles[0].getPath().replace(this.listOfFiles[0].getName(), "SAMPLE.IMG");
    

    this.myMetaFileHeader.writeMetaFileHeader(this.outputMetaFilePath);
    

    this.myKMPList = new Multisample[this.nbSampleInDirPath];
    for (int i = 0; i < this.nbSampleInDirPath; i++)
    {
      this.myMSList[i] = new Multisample();
      this.myKMPList[i].setMultisampleDescriptionStartNum((short)i);
      this.myKMPList[i].setInstrumentName((padString(InstrumentName));
      this.myMSList[i].setNbMultisampleDescription((byte)1);
      this.myMSList[i].setUnknownAlways1((byte)1);
      
      this.myMSList[i].writeMultisampleInfo(this.outputMetaFilePath, i);
    }
    this.myMSDList = new MultisampleDescription[this.nbSampleInDirPath];
    for (int i = 0; i < this.nbSampleInDirPath; i++)
    {
      this.myKMPList[i] = new MultisampleKMPDescription();
      this.myKMPList[i].setSampleNumber((short)i);
      this.myKMPList[i].setOriginalKey((byte)60);
      this.myKMPList[i].setTopKey((byte)127);
      this.myMSDList[i].setunknown((byte)2);
      this.myMSDList[i].setUnknown2to0();
      this.myMSDList[i].writeMultisampleDescription(this.outputMetaFilePath, this.nbSampleInDirPath, i);
    }
    this.myKSFList = new Waveform[this.nbSampleInDirPath];
    
    int offsetAddress = 0;
    

    int i = 0;
    for (int j = 0; j < this.listOfFiles.length; j++) {
      if (this.listOfFiles[j].isFile())
      {
        String files = this.listOfFiles[j].getName();
        if ((files.endsWith(".raw")) || (files.endsWith(".RAW")))
        {
          System.out.println("Processing : " + this.listOfFiles[j].getPath());
          
          this.myKSFList[i] = new Waveform();
          this.myKSFList[i].setSampleName("SAMPLE NUMBER " + i);
          this.myWaveformList[i].setUnknown1(855703364); //44FF0033
          this.myWaveformList[i].setUnknown2(-871861776);//2
          this.myKSFList[i].setStartAdress;//3
          this.myKSFList[i].setStartAdress2;//4
          this.myKSFList[i].setLoopStartAdress(byte[] copy = LoopStartAdress.clone);//5
          this.myKSFList[i].setSampleEnd(offsetAddress + (int)this.listOfFiles[j].length() - 1 | 0x3000000);
          this.myWaveformList[i].setUnknown3(50331648);//00000003
          this.myWaveformList[i].setSampleAddress(50331648);
          this.myWaveformList[i].setUnknown4(50331648);
          this.myWaveformList[i].setUnknown5(50331648);
          this.myWaveformList[i].setUnknown6(50331648);
          this.myWaveformList[i].setUnknown7(50331648);
          this.myWaveformList[i].setUnknown8(50331648);
          this.myWaveformList[i].setUnknown9(50331648);
          this.myWaveformList[i].setUnknown10(0);
          this.myWaveformList[i].setUnknown11(50331648);
          this.myKSFList[i].setSampleSize((int)this.listOfFiles[j].length() * 2);
          
          this.myKSFList[i].writeKSFWaveformDescription(this.outputMetaFilePath, this.nbSampleInDirPath, this.nbSampleInDirPath, i);
          
          this.myKSFList[i].readWaveformSamplesFrom(this.listOfFiles[j].getPath());
          
          this.myKSFList[i].writeWaveformSamplesToSampleIMG(this.outputSampleFilePath);
          
          offsetAddress = (int)(offsetAddress + this.listOfFiles[j].length());
          
          i++;
        }
      }
    }
    if (16777216 - offsetAddress - 1 - 1 >= 4)
    {
      this.myKSFList[0].writePaddingToSampleIMG(this.outputSampleFilePath, offsetAddress - 1);
      this.lblGenerated.setText("Generation done with " + String.valueOf(this.nbSampleInDirPath) + " Sample(s)!");
    }
    else
    {
      File myFile = new File(this.outputSampleFilePath);
      File myFile2 = new File(this.outputMetaFilePath);
      myFile.delete();
      myFile2.delete();
      this.lblGenerated.setText("Generation cannot be done (SAMPLE.IMG cannot exceed 16 MB)");
    }
  }
  
  public KromeSampleTool()
  {
    setTitle("KromeSampleTool");
    setDefaultCloseOperation(3);
    setBounds(100, 100, 800, 400);
    try
    {
      this.myPicture = ImageIO.read(getClass().getResource("KromeSampleTool.png"));
      this.myPictureKeyDown = ImageIO.read(getClass().getResource("keyDown.png"));
      this.myPictureKeyUp = ImageIO.read(getClass().getResource("keyUp.png"));
      this.myPictureEasy = ImageIO.read(getClass().getResource("easy.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    this.picLabel = new JLabel(new ImageIcon(this.myPicture));
    this.lblkeyUpPic = new JLabel(new ImageIcon(this.myPictureKeyUp));
    this.lblkeyDownPic = new JLabel(new ImageIcon(this.myPictureKeyDown));
    this.lblEasyPic = new JLabel(new ImageIcon(this.myPictureEasy));
    
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    JMenu mnFile = new JMenu("File");
    menuBar.add(mnFile);
    
    JMenuItem mntmOpen = new JMenuItem("Open META.IMG");
    mntmOpen.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        JFileChooser fileChooser = new JFileChooser();
        
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == 0)
        {
          KromeSampleTool.this.metaImgFilepath = fileChooser.getSelectedFile().getPath();
          

          KromeSampleTool.this.myHeader.readMetaFileHeader(KromeSampleTool.this.metaImgFilepath);
          if (KromeSampleTool.this.myHeader.getIdKorg() != null)
          {
            KromeSampleTool.this.currentMultisample = 0;
            KromeSampleTool.this.currentMultisampleDescription = 0;
            KromeSampleTool.this.currentWaveform = 0;
            
            KromeSampleTool.this.mntmHeader.setEnabled(true);
            KromeSampleTool.this.mntmMultisamples.setEnabled(true);
            KromeSampleTool.this.mntmMultisamplesdescription.setEnabled(true);
            KromeSampleTool.this.mntmWaveformdescription.setEnabled(true);
            KromeSampleTool.this.mntmExtractSamples.setEnabled(true);
            KromeSampleTool.this.mntmExtractSamplesFrom.setEnabled(true);
            KromeSampleTool.this.contentPane.removeAll();
            


            KromeSampleTool.this.repaint();
          }
          else
          {
            KromeSampleTool.this.contentPane.removeAll();
            KromeSampleTool.this.contentPane.setLayout(KromeSampleTool.this.myGridLayout82);
            
            JLabel lblError = new JLabel("Not a META.IMG korg format!");
            KromeSampleTool.this.contentPane.add(lblError);
            
            KromeSampleTool.this.contentPane.revalidate();
            KromeSampleTool.this.repaint();
          }
        }
        else
        {
          System.out.println("File access cancelled by user.");
        }
      }
    });
    mnFile.add(mntmOpen);
    
    JMenuItem mntmQuit = new JMenuItem("Quit");
    mntmQuit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        System.exit(0);
      }
    });
    JSeparator separator = new JSeparator();
    mnFile.add(separator);
    mnFile.add(mntmQuit);
    
    JMenu mnEdit = new JMenu("Edit");
    menuBar.add(mnEdit);
    
    this.mntmMultisamples.setEnabled(false);
    
    this.buttonPreviousMS.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentMultisample -= 1;
        if (KromeSampleTool.this.currentMultisample < 0) {
          KromeSampleTool.this.currentMultisample = 0;
        }
        KromeSampleTool.this.myMultisampleInfo.readMultisampleInfo(KromeSampleTool.this.metaImgFilepath, KromeSampleTool.this.currentMultisample);
        KromeSampleTool.this.lblTitle4.setText(String.valueOf(KromeSampleTool.this.currentMultisample));
        KromeSampleTool.this.textMultisampleName.setText(KromeSampleTool.this.myMultisampleInfo.getMultisampleName());
        KromeSampleTool.this.textMdsn.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getMultisampleDescriptionStartNum()));
        KromeSampleTool.this.textNbMsd.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getNbMultisampleDescription()));
        KromeSampleTool.this.repaint();
      }
    });
    this.buttonNextMS.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentMultisample += 1;
        if (KromeSampleTool.this.currentMultisample > KromeSampleTool.this.myHeader.getNbMultisample() - 1) {
          KromeSampleTool.this.currentMultisample = (KromeSampleTool.this.myHeader.getNbMultisample() - 1);
        }
        KromeSampleTool.this.myMultisampleInfo.readMultisampleInfo(KromeSampleTool.this.metaImgFilepath, KromeSampleTool.this.currentMultisample);
        KromeSampleTool.this.lblTitle4.setText(String.valueOf(KromeSampleTool.this.currentMultisample));
        KromeSampleTool.this.textMultisampleName.setText(KromeSampleTool.this.myMultisampleInfo.getMultisampleName());
        KromeSampleTool.this.textMdsn.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getMultisampleDescriptionStartNum()));
        KromeSampleTool.this.textNbMsd.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getNbMultisampleDescription()));
        KromeSampleTool.this.repaint();
      }
    });
    this.buttonPreviousMSD.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentMultisampleDescription -= 1;
        if (KromeSampleTool.this.currentMultisampleDescription < 0) {
          KromeSampleTool.this.currentMultisampleDescription = 0;
        }
        KromeSampleTool.this.displayMultisampleDescription();
      }
    });
    this.buttonNextMSD.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentMultisampleDescription += 1;
        if (KromeSampleTool.this.currentMultisampleDescription > KromeSampleTool.this.myHeader.getNbMultisampleDescription() - 1) {
          KromeSampleTool.this.currentMultisampleDescription = (KromeSampleTool.this.myHeader.getNbMultisampleDescription() - 1);
        }
        KromeSampleTool.this.displayMultisampleDescription();
      }
    });
    this.buttonGotoMD.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentMultisampleDescription = KromeSampleTool.this.myMultisampleInfo.getMultisampleDescriptionStartNum();
        KromeSampleTool.this.displayMultisampleDescription();
      }
    });
    this.buttonGotoWF.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentWaveform = KromeSampleTool.this.myMultisampleDescription.getSampleNumber();
        KromeSampleTool.this.displayWaveformDescription();
      }
    });
    this.buttonPreviousWF.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentWaveform -= 1;
        if (KromeSampleTool.this.currentWaveform < 0) {
          KromeSampleTool.this.currentWaveform = 0;
        }
        KromeSampleTool.this.displayWaveformDescription();
      }
    });
    this.buttonNextWF.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.currentWaveform += 1;
        if (KromeSampleTool.this.currentWaveform > KromeSampleTool.this.myHeader.getNbWaveformDescription() - 1) {
          KromeSampleTool.this.currentWaveform = (KromeSampleTool.this.myHeader.getNbWaveformDescription() - 1);
        }
        KromeSampleTool.this.displayWaveformDescription();
      }
    });
    this.buttonExtractWF.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.myWaveform.writeWaveformSamples(KromeSampleTool.this.currentWaveform, 1);
      }
    });
    this.buttonSelectDirectory.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(1);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == 0)
        {
          KromeSampleTool.this.sampleDirPath = fileChooser.getSelectedFile().getPath();
          KromeSampleTool.this.tfSelectedDirectory.setText(KromeSampleTool.this.sampleDirPath);
          KromeSampleTool.this.displayCreateEasyModeGUI();
        }
        else
        {
          System.out.println("File access cancelled by user.");
        }
      }
    });
    this.buttonGenerate.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.sampleDirPath.isEmpty())
        {
          KromeSampleTool.this.tfSelectedDirectory.setText("Push the Select button to select your samples directory >");
          KromeSampleTool.this.displayCreateEasyModeGUI();
        }
        else
        {
          KromeSampleTool.this.listRawSampleFilesInDir(KromeSampleTool.this.sampleDirPath);
        }
        if (KromeSampleTool.this.nbSampleInDirPath != 0) {
          if (KromeSampleTool.this.firstChoiceButton.isSelected()) {
            KromeSampleTool.this.generateMetaAndSampleFiles();
          } else {
            KromeSampleTool.this.generateMetaAndSampleFilesInstrument();
          }
        }
      }
    });
    this.mntmMultisamples.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          KromeSampleTool.this.myMultisampleInfo.readMultisampleInfo(KromeSampleTool.this.metaImgFilepath, KromeSampleTool.this.currentMultisample);
          
          KromeSampleTool.this.contentPane.removeAll();
          KromeSampleTool.this.contentPane.setLayout(KromeSampleTool.this.myGridLayout03);
          
          KromeSampleTool.this.textMultisampleName.setText(KromeSampleTool.this.myMultisampleInfo.getMultisampleName());
          

          KromeSampleTool.this.textMdsn.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getMultisampleDescriptionStartNum()));
          

          KromeSampleTool.this.textNbMsd.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getNbMultisampleDescription()));
          

          KromeSampleTool.this.textUnk.setText(String.valueOf(KromeSampleTool.this.myMultisampleInfo.getUnknownAlways1()));
          

          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblTitle3);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblTitle4);
          KromeSampleTool.this.contentPane.add(new JLabel(""));
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblMultisampleName);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textMultisampleName);
          KromeSampleTool.this.contentPane.add(new JLabel(""));
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblMdsn);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textMdsn);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.buttonGotoMD);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblNbMsd);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textNbMsd);
          KromeSampleTool.this.contentPane.add(new JLabel(""));
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblUnk);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textUnk);
          KromeSampleTool.this.contentPane.add(new JLabel(""));
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.buttonPreviousMS);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.buttonNextMS);
          

          KromeSampleTool.this.contentPane.revalidate();
          
          KromeSampleTool.this.repaint();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    this.mntmHeader.setEnabled(false);
    
    this.mntmHeader.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          KromeSampleTool.this.contentPane.removeAll();
          KromeSampleTool.this.contentPane.setLayout(KromeSampleTool.this.myGridLayout82);
          
          KromeSampleTool.this.textId.setText(KromeSampleTool.this.myHeader.getIdKorg());
          KromeSampleTool.this.textId.setPreferredSize(new Dimension(20, 20));
          
          KromeSampleTool.this.textVer.setText(KromeSampleTool.this.myHeader.getVersion());
          KromeSampleTool.this.textVer.setPreferredSize(new Dimension(20, 20));
          
          KromeSampleTool.this.textNbs.setText(String.valueOf(KromeSampleTool.this.myHeader.getNbMultisample()));
          KromeSampleTool.this.textNbs.setPreferredSize(new Dimension(20, 20));
          
          KromeSampleTool.this.textNbsd.setText(String.valueOf(KromeSampleTool.this.myHeader.getNbMultisampleDescription()));
          KromeSampleTool.this.textNbsd.setPreferredSize(new Dimension(20, 20));
          
          KromeSampleTool.this.textNbwfd.setText(String.valueOf(KromeSampleTool.this.myHeader.getNbWaveformDescription()));
          KromeSampleTool.this.textNbwfd.setPreferredSize(new Dimension(20, 20));
          
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblTitle);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblTitle2);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblId);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textId);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblVer);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textVer);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblNbMultiSample);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textNbs);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblNbMultiSampleDescription);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textNbsd);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.lblNbWaveformDescription);
          KromeSampleTool.this.contentPane.add(KromeSampleTool.this.textNbwfd);
          
          KromeSampleTool.this.contentPane.revalidate();
          
          KromeSampleTool.this.repaint();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    mnEdit.add(this.mntmHeader);
    
    JSeparator separator_1 = new JSeparator();
    mnEdit.add(separator_1);
    mnEdit.add(this.mntmMultisamples);
    




    this.mntmMultisamplesdescription.setEnabled(false);
    
    this.mntmMultisamplesdescription.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          KromeSampleTool.this.displayMultisampleDescription();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    mnEdit.add(this.mntmMultisamplesdescription);
    




    this.mntmWaveformdescription.setEnabled(false);
    
    this.mntmWaveformdescription.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          KromeSampleTool.this.displayWaveformDescription();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    JSeparator separator_2 = new JSeparator();
    mnEdit.add(separator_2);
    mnEdit.add(this.mntmWaveformdescription);
    
    JMenu mnTools = new JMenu("Tools");
    menuBar.add(mnTools);
    
    this.mntmExtractSamples.setEnabled(false);
    
    mnTools.add(this.mntmExtractSamples);
    




    this.mntmExtractSamplesFrom.setEnabled(false);
    
    this.mntmExtractSamplesFrom.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          JLabel lblInfo = new JLabel("");
          File f = new File(KromeSampleTool.this.metaImgFilepath.replace("META.IMG", "SAMPLE.IMG"));
          if (f.exists())
          {
            for (int i = 0; i < KromeSampleTool.this.myHeader.getNbWaveformDescription(); i++)
            {
              KromeSampleTool.this.myWaveform.readWaveformDescription(KromeSampleTool.this.metaImgFilepath, KromeSampleTool.this.myHeader.getNbMultisample(), KromeSampleTool.this.myHeader.getNbMultisampleDescription(), i);
              KromeSampleTool.this.myWaveform.writeWaveformSamples(i, 1);
            }
            lblInfo.setText(i + " Samples extracted !");
          }
          else
          {
            lblInfo.setText("Put SAMPLE.IMG in the same directory than META.IMG !");
          }
          KromeSampleTool.this.contentPane.removeAll();
          KromeSampleTool.this.contentPane.setLayout(KromeSampleTool.this.myGridLayout82);
          KromeSampleTool.this.contentPane.add(lblInfo);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    mnTools.add(this.mntmExtractSamplesFrom);
    
    JSeparator separator_3 = new JSeparator();
    mnTools.add(separator_3);
    




    JMenuItem mntmCreateSampleimgAnd = new JMenuItem("Create SAMPLE.IMG and META.IMG : Easy Mode");
    mntmCreateSampleimgAnd.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        KromeSampleTool.this.displayCreateEasyModeGUI();
      }
    });
    mnTools.add(mntmCreateSampleimgAnd);
    
    JMenuItem mntmCreateSampleimgAnd_1 = new JMenuItem("Create SAMPLE.IMG and META.IMG : Advanced Mode");
    mnTools.add(mntmCreateSampleimgAnd_1);
    mntmCreateSampleimgAnd_1.setEnabled(false);
    
    //JMenuItem mntmCreateSampleimgAnd_2 = new JMenuItem("Convert KSC to SAMPLE.IMG and META.IMG");
    //mntmCreateSampleimgAnd_2.addActionListener(new ActionListener()
    //{
      //public void actionPerformed(ActionEvent arg0)
      //{
        //KromeSampleTool.this.displayConvertKSCModeGUI ();
      //}
    //});
    //mnTools.add(mntmCreateSampleimgAnd_2);
    
    JMenuItem mntmCreateSampleimgAnd_2 = new JMenuItem("Convert KSC to SAMPLE.IMG and META.IMG");
    mntmCreateSampleimgAnd_2.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent arg0)
        {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setFileSelectionMode(1);
          int returnVal = fileChooser.showOpenDialog(null);
          if (returnVal == 0)
          {
            KromeSampleTool.this.sampleDirPath = fileChooser.getSelectedFile().getPath();
            KromeSampleTool.this.tfSelectedDirectory.setText(KromeSampleTool.this.sampleDirPath);
            KromeSampleTool.this.displayCreateEasyModeGUI();
          }
          else
          {
            System.out.println("File access cancelled by user.");
          }
        }
      });
   mnTools.add(mntmCreateSampleimgAnd_2);
   
    
         
    


    this.mntmExtractSamples.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        if (KromeSampleTool.this.myHeader.getIdKorg() != null)
        {
          JLabel lblInfo = new JLabel("");
          File f = new File(KromeSampleTool.this.metaImgFilepath.replace("META.IMG", "SAMPLE.IMG"));
          if (f.exists())
          {
            for (int i = 0; i < KromeSampleTool.this.myHeader.getNbWaveformDescription(); i++)
            {
              KromeSampleTool.this.myWaveform.readWaveformDescription(KromeSampleTool.this.metaImgFilepath, KromeSampleTool.this.myHeader.getNbMultisample(), KromeSampleTool.this.myHeader.getNbMultisampleDescription(), i);
              KromeSampleTool.this.myWaveform.writeWaveformSamples(i, 0);
            }
            lblInfo.setText(i + " Sample(s) extracted !");
          }
          else
          {
            lblInfo.setText("Put SAMPLE.IMG in the same directory than META.IMG !");
          }
          KromeSampleTool.this.contentPane.removeAll();
          KromeSampleTool.this.contentPane.setLayout(KromeSampleTool.this.myGridLayout82);
          KromeSampleTool.this.contentPane.add(lblInfo);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
        else
        {
          KromeSampleTool.this.contentPane.removeAll();
          
          JLabel lblError = new JLabel("Open a META.IMG file !");
          KromeSampleTool.this.contentPane.add(lblError);
          KromeSampleTool.this.contentPane.revalidate();
          KromeSampleTool.this.repaint();
        }
      }
    });
    JMenu mnAbout = new JMenu("Help");
    menuBar.add(mnAbout);
    
    JMenuItem mntmAbout = new JMenuItem("About");
    mntmAbout.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        JFrame myFrame = new JFrame("About");
        myFrame.setResizable(false);
        myFrame.getContentPane().setLayout(new GridLayout(0, 2));
        myFrame.getContentPane().add(KromeSampleTool.this.picLabel);
        
        JLabel textLabel = new JLabel("KromeSampleTool V0.3.1 (c) http://www.kromeheaven.com 2013", 0);
        myFrame.getContentPane().add(textLabel, "Center");
        
        myFrame.pack();
        myFrame.setVisible(true);
      }
    });
    mnAbout.add(mntmAbout);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.contentPane.setLayout(this.myGridLayout82);
    setContentPane(this.contentPane);
    
    this.contentPane.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder("KromeSampleTool"), 
      BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    
    JLabel textLabel = new JLabel("DISCLAIMER", 0);
    this.contentPane.add(textLabel, "Center");
    JLabel textLabel2 = new JLabel("This SOFTWARE PRODUCT is provided by Kromeheaven 'as is' and 'with all faults'.", 0);
    this.contentPane.add(textLabel2, "Center");
    JLabel textLabel3 = new JLabel("Kromeheaven makes no representations or warranties of any kind concerning the safety, suitability, lack of viruses, ", 0);
    this.contentPane.add(textLabel3, "Center");
    JLabel textLabel4 = new JLabel("inaccuracies, typographical errors, or other harmful components of this SOFTWARE PRODUCT. There are inherent dangers", 0);
    this.contentPane.add(textLabel4, "Center");
    JLabel textLabel5 = new JLabel("in the use of any software, and you are solely responsible for determining whether this SOFTWARE PRODUCT is", 0);
    this.contentPane.add(textLabel5, "Center");
    JLabel textLabel6 = new JLabel("compatible with your equipment. You are also solely responsible for the protection of your equipment and backup of ", 0);
    this.contentPane.add(textLabel6, "Center");
    JLabel textLabel7 = new JLabel("your data, and Kromeheaven will not be liable for any damages you may suffer in connection with using, modifying,", 0);
    this.contentPane.add(textLabel7, "Center");
    JLabel textLabel8 = new JLabel("this SOFTWARE PRODUCT.", 0);
    this.contentPane.add(textLabel8, "Center");
  }
  
  private static void addPopup(Component component, JPopupMenu popup)
  {
    component.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        if (e.isPopupTrigger()) {
          showMenu(e);
        }
      }
      
      public void mouseReleased(MouseEvent e)
      {
        if (e.isPopupTrigger()) {
          showMenu(e);
        }
      }
      
      private void showMenu(MouseEvent e)
      {
        KromeSampleTool.this.show(e.getComponent(), e.getX(), e.getY());
      }
    });
  }
}
