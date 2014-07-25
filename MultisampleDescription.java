import java.io.PrintStream;
import java.io.RandomAccessFile;

public class MultisampleDescription
{
  private short sampleNumber;
  private byte unknown;
  private byte keyUp;
  private byte keyDown;
  private byte tune;
  private byte level;
  private byte pan;
  private byte[] filter = new byte[12];
  
  public short getSampleNumber()
  {
    return this.sampleNumber;
  }
  
  public void setSampleNumber(short sampleNumber)
  {
    this.sampleNumber = sampleNumber;
  }
  
  public byte getunknown()
  {
    return this.unknown;
  }
  
  public void setunknown(byte unknown)
  {
    this.unknown = unknown;
  }
  
  public byte getkeyUp()
  {
    return this.keyUp;
  }
  
  public void setkeyUp(byte keyup)
  {
    this.keyUp = keyup;
  }
  
  public byte getkeyDown()
  {
    return this.keyDown;
  }
  
  public void setkeyDown(byte keydown)
  {
    this.keyDown = keydown;
  }
  
  public byte getTune()
  {
    return this.tune;
  }
  
  public void setTune(byte tune)
  {
    this.tune = tune;
  }
  
  public byte getLevel()
  {
    return this.level;
  }
  
  public void setLevel(byte level)
  {
    this.level = level;
  }
  
  public byte getPan()
  {
    return this.pan;
  }
  
  public void setPan(byte pan)
  {
    this.pan = pan;
  }
  
  public byte[] getFilter()
  {
    return this.filter;
  }
  
  public void setFilter(byte[] filter)
  {
    this.filter = filter;
  }
  
  public void setFilterto0()
  {
    for (int i = 0; i < 12; i++) {
      this.filter[i] = 0;
    }
  }
  
  public int readMultisampleDescription(String myImgFile, int nbMultisample, int number)
  {
    try
    {
      RandomAccessFile in = new RandomAccessFile(myImgFile, "r");
      in.seek(28 + nbMultisample * 28 + number * 20);
      
      this.sampleNumber = Short.reverseBytes(in.readShort());
      

      this.unknown = in.readByte();
      

      this.keyUp = in.readByte();
      

      this.keyDown = in.readByte();
      

      this.tune = in.readByte();
      

      this.level = in.readByte();
      

      this.pan = in.readByte();
          

      in.read(this.filter);
      setFilter(this.filter);
      
      in.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int writeMultisampleDescription(String myImgFile, int nbMultisample, int number)
  {
    try
    {
      System.out.println("MultisampleDescription write : " + number);
      RandomAccessFile out = new RandomAccessFile(myImgFile, "rw");
      out.seek(28 + nbMultisample * 28 + number * 20);
      
      out.writeShort(Short.reverseBytes(this.sampleNumber));
      System.out.println(this.sampleNumber);
      out.writeByte(this.unknown);
      System.out.println(this.unknown);
      out.writeByte(this.keyUp);
      System.out.println(this.keyUp);
      out.writeByte(this.keyDown);
      System.out.println(this.keyDown);
      out.writeByte(this.tune);
      System.out.println(this.tune);
      out.writeByte(this.level);
      System.out.println(this.level);
      out.writeByte(this.pan);
      System.out.println(this.pan);
      out.write(this.filter);
      System.out.println(this.filter);
      
      out.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
}
