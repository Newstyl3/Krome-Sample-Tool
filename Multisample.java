import java.io.RandomAccessFile;

public class Multisample
{
  private short multisampleDescriptionStartNum = 0;
  private byte nbMultisampleDescription = 0;
  private byte unknownAlways1 = 1;
  private String multisampleName;
  
  public short getMultisampleDescriptionStartNum()
  {
    return this.multisampleDescriptionStartNum;
  }
  
  public void setMultisampleDescriptionStartNum(short multisampleDescriptionStartNum)
  {
    this.multisampleDescriptionStartNum = multisampleDescriptionStartNum;
  }
  
  public byte getNbMultisampleDescription()
  {
    return this.nbMultisampleDescription;
  }
  
  public void setNbMultisampleDescription(byte nbMultisampleDescription)
  {
    this.nbMultisampleDescription = nbMultisampleDescription;
  }
  
  public String getMultisampleName()
  {
    return this.multisampleName;
  }
  
  public void setMultisampleName(String multisampleName)
  {
    this.multisampleName = padString(multisampleName, 24);
  }
  
  public byte getUnknownAlways1()
  {
    return this.unknownAlways1;
  }
  
  public void setUnknownAlways1(byte unknownAlways1)
  {
    this.unknownAlways1 = unknownAlways1;
  }
  
  public int readMultisampleInfo(String myImgFile, int number)
  {
    try
    {
      RandomAccessFile in = new RandomAccessFile(myImgFile, "r");
      in.seek(28 + number * 28);
      
      this.multisampleDescriptionStartNum = Short.reverseBytes(in.readShort());
      

      this.nbMultisampleDescription = in.readByte();
      

      this.unknownAlways1 = in.readByte();
      

      byte[] b = new byte[24];
      in.read(b);
      setMultisampleName(new String(b));
      

      in.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int writeMultisampleInfo(String myImgFile, int number)
  {
    try
    {
      RandomAccessFile out = new RandomAccessFile(myImgFile, "rw");
      out.seek(28 + number * 28);
      
      out.writeShort(Short.reverseBytes(this.multisampleDescriptionStartNum));
      out.writeByte(this.nbMultisampleDescription);
      out.writeByte(this.unknownAlways1);
      
      out.write(getMultisampleName().getBytes());
      
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
