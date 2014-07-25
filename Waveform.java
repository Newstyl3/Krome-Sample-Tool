import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class Waveform
{
  private int unknown1 = 0;
  private int unknown2 = 0;
  private int startAddress1 = 0;
  private int startAddress2 = 0;
  private int startAddress3 = 0;
  private int endAddress1 = 0;
  private int unknown3 = 0;
  private int sampleAddress = 0;
  private int unknown4 = 0;
  private int unknown5 = 0;
  private int unknown6 = 0;
  private int unknown7 = 0;
  private int unknown8 = 0;
  private int unknown9 = 0;
  private int unknown10 = 0;
  private int unknown11 = 0;
  private int sampleSizeX2 = 0;
  private String sampleName;
  private String imgFilePath;
  private byte[] samples;
  private BufferedImage samplesImage;
  
  public int getUnknown1()
  {
    return this.unknown1;
  }
  
  public void setUnknown1(int unknown1)
  {
    this.unknown1 = unknown1;
  }
  
  public int getUnknown2()
  {
    return this.unknown2;
  }
  
  public void setUnknown2(int unknown2)
  {
    this.unknown2 = unknown2;
  }
  
  public int getStartAddress1()
  {
    return this.startAddress1;
  }
  
  public void setStartAddress1(int startAddress1)
  {
    this.startAddress1 = startAddress1;
  }
  
  public int getStartAddress2()
  {
    return this.startAddress2;
  }
  
  public void setStartAddress2(int startAddress2)
  {
    this.startAddress2 = startAddress2;
  }
  
  public int getStartAddress3()
  {
    return this.startAddress3;
  }
  
  public void setStartAddress3(int startAddress3)
  {
    this.startAddress3 = startAddress3;
  }
  
  public int getEndAddress1()
  {
    return this.endAddress1;
  }
  
  public void setEndAddress1(int endAddress1)
  {
    this.endAddress1 = endAddress1;
  }
  
  public int getUnknown3()
  {
    return this.unknown3;
  }
  
  public void setUnknown3(int unknown3)
  {
    this.unknown3 = unknown3;
  }
  
  public int getSampleAddress()
  {
    return this.sampleAddress;
  }
  
  public void setSampleAddress(int sampleAddress)
  {
    this.sampleAddress = sampleAddress;
  }
  
  public int getUnknown4()
  {
    return this.unknown4;
  }
  
  public void setUnknown4(int unknown4)
  {
    this.unknown4 = unknown4;
  }
  
  public int getUnknown5()
  {
    return this.unknown5;
  }
  
  public void setUnknown5(int unknown5)
  {
    this.unknown5 = unknown5;
  }
  
  public int getUnknown6()
  {
    return this.unknown6;
  }
  
  public void setUnknown6(int unknown6)
  {
    this.unknown6 = unknown6;
  }
  
  public int getUnknown7()
  {
    return this.unknown7;
  }
  
  public void setUnknown7(int unknown7)
  {
    this.unknown7 = unknown7;
  }
  
  public int getUnknown8()
  {
    return this.unknown8;
  }
  
  public void setUnknown8(int unknown8)
  {
    this.unknown8 = unknown8;
  }
  
  public int getUnknown9()
  {
    return this.unknown9;
  }
  
  public void setUnknown9(int unknown9)
  {
    this.unknown9 = unknown9;
  }
  
  public int getUnknown10()
  {
    return this.unknown10;
  }
  
  public void setUnknown10(int unknown10)
  {
    this.unknown10 = unknown10;
  }
  
  public int getUnknown11()
  {
    return this.unknown11;
  }
  
  public void setUnknown11(int unknown11)
  {
    this.unknown11 = unknown11;
  }
  
  public int getSampleSizeX2()
  {
    return this.sampleSizeX2;
  }
  
  public void setSampleSizeX2(int sampleSizeX2)
  {
    this.sampleSizeX2 = sampleSizeX2;
  }
  
  public String getSampleName()
  {
    return this.sampleName;
  }
  
  public void setSampleName(String sampleName)
  {
    this.sampleName = padString(sampleName, 24);
  }
  
  public BufferedImage getSamplesImage()
  {
    return this.samplesImage;
  }
  
  public void setSamplesImage(BufferedImage samplesImage)
  {
    this.samplesImage = samplesImage;
  }
  
  public int readWaveformDescription(String myImgFile, int nbMultisample, int nbMultisampleDescription, int number)
  {
    try
    {
      this.imgFilePath = myImgFile;
      
      RandomAccessFile in = new RandomAccessFile(myImgFile, "r");
      in.seek(28 + nbMultisample * 28 + nbMultisampleDescription * 20 + number * 92);
      
      this.unknown1 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown1));
      
      this.unknown2 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown2));
      
      this.startAddress1 = (Integer.reverseBytes(in.readInt()) & 0xFFFFFF);
      System.out.println("Start Address 1 : " + this.startAddress1);
      
      this.startAddress2 = Integer.reverseBytes(in.readInt());
      System.out.println("Start Address 2 : " + this.startAddress2);
      
      this.startAddress3 = (Integer.reverseBytes(in.readInt()) & 0xFFFFFF);
      System.out.println("Start Address 3 : " + this.startAddress3);
      
      this.endAddress1 = (Integer.reverseBytes(in.readInt()) & 0xFFFFFF);
      System.out.println("End Address 1 : " + this.endAddress1);
      
      this.unknown3 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown3));
      
      this.sampleAddress = (Integer.reverseBytes(in.readInt()) & 0xFFFFFF);
      System.out.println("Sample Address : " + this.sampleAddress);
      
      this.unknown4 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown4));
      this.unknown5 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown5));
      this.unknown6 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown6));
      this.unknown7 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown7));
      this.unknown8 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown8));
      this.unknown9 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown9));
      this.unknown10 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown10));
      this.unknown11 = Integer.reverseBytes(in.readInt());
      System.out.println(Integer.toHexString(this.unknown11));
      this.sampleSizeX2 = Integer.reverseBytes(in.readInt());
      System.out.println("Sample Size : " + this.sampleSizeX2);
      
      byte[] b = new byte[24];
      in.read(b);
      setSampleName(new String(b));
      System.out.println(getSampleName());
      in.close();
      
      readWaveformSamples();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int readWaveformSamples()
  {
    try
    {
      RandomAccessFile in = new RandomAccessFile(this.imgFilePath.replace("META", "SAMPLE"), "r");
      

      in.seek(this.startAddress1);
      

      this.samples = new byte[(this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1];
      
      in.read(this.samples);
      in.close();
      

      setSamplesImage(processWaveformImage());
    }
    catch (Exception ef)
    {
      System.out.println("Error Reading SAMPLE.IMG file");
      return 1;
    }
    RandomAccessFile in;
    return 0;
  }
  
  public int writeWaveformSamples(int number, int mode)
  {
    try
    {
      if (this.samples != null)
      {
        String filename = new String("");
        if (mode == 0)
        {
          filename = "SAMPLE" + String.format("%03d", new Object[] { Integer.valueOf(number) }) + ".RAW";
        }
        else
        {
          filename = getSampleName();
          filename = filename.replace(" ", "_");
          filename = filename.replace(".", "-");
          filename = filename.replace("+", "-");
          filename = filename.concat(".RAW");
        }
        System.out.println("Writing samples file to : " + this.imgFilePath.replace("META.IMG", filename));
        
        RandomAccessFile out = new RandomAccessFile(this.imgFilePath.replace("META.IMG", filename), "rw");
        out.write(this.samples);
        out.close();
      }
      else
      {
        System.out.println("Cannot write samples since no data were read from SAMPLE.IMG");
      }
    }
    catch (Exception ef)
    {
      System.out.println("Cannot write sample Data!");
      return 1;
    }
    return 0;
  }
  
  private BufferedImage processWaveformImage()
  {
    int w = 256;
    int h = 256;
    BufferedImage img = new BufferedImage(w, h, 2);
    
    Graphics2D g2d = img.createGraphics();
    g2d.setPaint(Color.black);
    
    g2d.drawLine(0, h / 2, w, h / 2);
    g2d.drawLine(0, h / 2 + 1, w, h / 2 + 1);
    
    g2d.drawLine(0, h / 4, 2, h / 4);
    g2d.drawLine(0, h / 4 + 1, 2, h / 4 + 1);
    
    g2d.drawLine(0, 3 * h / 4, 2, 3 * h / 4);
    g2d.drawLine(0, 3 * h / 4 + 1, 2, 3 * h / 4 + 1);
    
    g2d.drawLine(0, 0, 0, h);
    g2d.drawLine(1, 0, 1, h);
    
    g2d.setPaint(Color.blue);
    if ((this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1 > w) {
      for (int i = 1; i < w; i++) {
        g2d.drawLine(i - 1, 
          this.samples[((i - 1) * ((this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1) / w)] + h / 2, 
          i, 
          this.samples[(i * ((this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1) / w)] + h / 2);
      }
    } else {
      for (int i = 1; i < w; i++) {
        if (i < (this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1) {
          g2d.drawLine(i - 1, this.samples[(i - 1)] + h / 2, i, this.samples[i] + h / 2);
        }
      }
    }
    g2d.setPaint(Color.red);
    String s = "Sample Preview";
    g2d.drawString(s, h / 2 - h / 8, h / 4);
    
    g2d.dispose();
    return img;
  }
  
  public int writeWaveformDescription(String myImgFile, int nbMultisample, int nbMultisampleDescription, int number)
  {
    try
    {
      System.out.println("Writing Waveform description " + number);
      this.imgFilePath = myImgFile;
      
      RandomAccessFile out = new RandomAccessFile(myImgFile, "rw");
      out.seek(28 + nbMultisample * 28 + nbMultisampleDescription * 20 + number * 92);
      
      out.writeInt(Integer.reverseBytes(this.unknown1));
      out.writeInt(Integer.reverseBytes(this.unknown2));
      out.writeInt(Integer.reverseBytes(this.startAddress1));
      out.writeInt(Integer.reverseBytes(this.startAddress2));
      out.writeInt(Integer.reverseBytes(this.startAddress3));
      out.writeInt(Integer.reverseBytes(this.endAddress1));
      out.writeInt(Integer.reverseBytes(this.unknown3));
      out.writeInt(Integer.reverseBytes(this.sampleAddress));
      out.writeInt(Integer.reverseBytes(this.unknown4));
      out.writeInt(Integer.reverseBytes(this.unknown5));
      out.writeInt(Integer.reverseBytes(this.unknown6));
      out.writeInt(Integer.reverseBytes(this.unknown7));
      out.writeInt(Integer.reverseBytes(this.unknown8));
      out.writeInt(Integer.reverseBytes(this.unknown9));
      out.writeInt(Integer.reverseBytes(this.unknown10));
      out.writeInt(Integer.reverseBytes(this.unknown11));
      out.writeInt(Integer.reverseBytes(this.sampleSizeX2));
      

      out.write(getSampleName().getBytes());
      
      out.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int readWaveformSamplesFrom(String myFile)
  {
    try
    {
      RandomAccessFile in = new RandomAccessFile(myFile, "r");
      

      this.samples = new byte[(this.endAddress1 & 0xFFFFFF) - (this.startAddress1 & 0xFFFFFF) + 1];
      
      in.read(this.samples);
      in.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int writeWaveformSamplesToSampleIMG(String myFile)
  {
    try
    {
      RandomAccessFile out = new RandomAccessFile(myFile, "rw");
      out.seek(this.startAddress1 & 0xFFFFFF);
      out.write(this.samples);
      out.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int writePaddingToSampleIMG(String myFile, int lastByte)
  {
    try
    {
      int nbBytes = 16777216 - lastByte - 1;
      byte[] buffer = new byte[nbBytes];
      
      buffer[(nbBytes - 1)] = 1;
      buffer[(nbBytes - 4)] = 8;
      
      RandomAccessFile out = new RandomAccessFile(myFile, "rw");
      out.seek(lastByte + 1);
      out.write(buffer);
      
      out.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  private static String padString(String str, int leng)
  {
    for (int i = str.length(); i < leng; i++) {
      str = str + " ";
    }
    return str;
  }
}
