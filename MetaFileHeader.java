import java.io.RandomAccessFile;

public class MetaFileHeader
{
  private String idKorg = null;
  private String version = null;
  private int unknown1 = 0;
  private int nbMultisample = 0;
  private int unknown2 = 0;
  private int nbMultisampleDescription = 0;
  private int nbWaveformDescription = 0;
  
  public String getIdKorg()
  {
    return this.idKorg;
  }
  
  public void setIdKorg(String idKorg)
  {
    this.idKorg = idKorg;
  }
  
  public String getVersion()
  {
    return this.version;
  }
  
  public void setVersion(String version)
  {
    this.version = version;
  }
  
  public int getNbMultisample()
  {
    return this.nbMultisample;
  }
  
  public void setNbMultisample(int nbMultisample)
  {
    this.nbMultisample = nbMultisample;
  }
  
  public int getNbMultisampleDescription()
  {
    return this.nbMultisampleDescription;
  }
  
  public void setNbMultisampleDescription(int nbMultisampleDescription)
  {
    this.nbMultisampleDescription = nbMultisampleDescription;
  }
  
  public int getNbWaveformDescription()
  {
    return this.nbWaveformDescription;
  }
  
  public void setNbWaveformDescription(int nbWaveformDescription)
  {
    this.nbWaveformDescription = nbWaveformDescription;
  }
  
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
  
  public int readMetaFileHeader(String myImgFile)
  {
    try
    {
      RandomAccessFile in = new RandomAccessFile(myImgFile, "r");
      
      byte[] b = new byte[4];
      in.read(b);
      String id = new String(b);
      if (id.equals("KORG"))
      {
        setIdKorg(id);
      }
      else
      {
        setIdKorg(null);in.close();return 1;
      }
      in.read(b);
      String ver = new String(b);
      setVersion(ver);
      

      int ukn1 = Integer.reverseBytes(in.readInt());
      setUnknown1(ukn1);
      

      int nbs = Integer.reverseBytes(in.readInt());
      setNbMultisample(nbs);
      

      int ukn2 = Integer.reverseBytes(in.readInt());
      setUnknown2(ukn2);
      

      int nbsd = Integer.reverseBytes(in.readInt());
      setNbMultisampleDescription(nbsd);
      

      int nbwf = Integer.reverseBytes(in.readInt());
      setNbWaveformDescription(nbwf);
      

      in.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
  
  public int writeMetaFileHeader(String myImgFile)
  {
    try
    {
      RandomAccessFile out = new RandomAccessFile(myImgFile, "rw");
      
      byte[] b = new byte[4];
      b = getIdKorg().getBytes();
      out.write(b);
      
      b = getVersion().getBytes();
      out.write(b);
      
      out.writeInt(Integer.reverseBytes(getUnknown1()));
      out.writeInt(Integer.reverseBytes(getNbMultisample()));
      out.writeInt(Integer.reverseBytes(getUnknown2()));
      out.writeInt(Integer.reverseBytes(getNbMultisampleDescription()));
      out.writeInt(Integer.reverseBytes(getNbWaveformDescription()));
      
      out.close();
    }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }
}
