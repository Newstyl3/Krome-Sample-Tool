import java.io.RandomAccessFile;

// *************** KMPRegion ***************
//#if WORDS_BIGENDIAN
//# ChunkSize //0x040506012
//# define CHUNK_ID_MSP1  0x4d535031
//# define CHUNK_ID_RLP1  0x524c5031

public class KMP 
{
  private String idMSP1 = null;
  private String ChunkSize = null; 
  private String InstrumentName;
  private Short nbMultisample = 0;
  private int nbSampleAttribute = 0;
  private String MN01;
  private int Unknown1 = 0;
  private int SampleID = 0;
  private String RLP01;
  private byte Transpose;
  private byte OriginalKey;
  private byte TopKey;
  private byte Tune;
  private byte Level;
  private byte Pan;
  private byte FilterCutOff;
  private String sampleName;
  
  public String getidMSP1()
{
  return this.idMSP1;
}

public void setidMSP1(String idMSP1)
{
  this.idMSP1 = idMSP1;
}
    
  public String getChunkSize()
  {
    return this.ChunkSize;
  }
  
  public void setChunkSize(String ChunkSize)
  {
    this.ChunkSize = ChunkSize;
  }
    public String getInstrumentName()
  {
    return this.InstrumentName;
  }
  
  public void setInstrumentName(String InstrumentName)
  {
    this.InstrumentName = padString(InstrumentName, 16);
  }
  
  public int getNbMultisample()
  {
    return this.nbMultisample;
  }
  
  public void setNbMultisample(short nbMultisample)
  {
    this.nbMultisample = nbMultisample;
  }
  
  public int getNBSampleAttribute()
  {
    return this.nbSampleAttribute;
  }
  
  public void setNBSampleAttribute(int nbSampleAttribute)
  {
    this.nbSampleAttribute = nbSampleAttribute;
  }
  
  public String getMN01()
  {
	  return this.MN01;
  }
  
  public void setMN01 (String MN01)
  {
	  this.MN01 = MN01;
  }
  
  public int getUnknown1()
  {
	  return this.Unknown1;
  }
  
  public void setUnknown1 (int Unknown1)
  {
	  this.Unknown1 = Unknown1;
  }
  
  public int getSampleID()
  {
	  return this.SampleID;
  }
  
  public void setSampleID (int SampleID)
  {
	  this.SampleID = SampleID;
  }
  
  public String getRLP01()
  {
	  return this.RLP01;
  }
  
  public void setRLP01 (String RLP01)
  {
	  this.RLP01 = RLP01;
  }
  
  public byte getTranspose()
  {
    return this.Transpose;
  }
  
  public void setTranspose(byte Transpose)
  {
    this.Transpose = Transpose;
  }
  
  public byte getOriginalKey()
  {
    return this.OriginalKey;
  }
  
  public void setOriginalKey(byte OriginalKey)
  {
    this.OriginalKey = OriginalKey;
  }
  
  public byte getTopKey()
  {
    return this.TopKey;
  }
  
  public void setTopKey(byte TopKey)
  {
    this.TopKey = TopKey;
  }
  
  public byte getTune()
  {
    return this.Tune;
  }
  
  public void setTune(byte Tune)
  {
    this.Tune = Tune;
  }
  
  public byte getLevel()
  {
    return this.Level;
  }
  
  public void setLevel(byte Level)
  {
    this.Level = Level;
  }
  
  public byte getPan()
  {
    return this.Pan;
  }
  
  public void setPan(byte Pan)
  {
    this.Pan = Pan;
  }
	  
  public byte getFilterCutOff()
  {
    return this.FilterCutOff;
  }
  
  public void setFilterCutOff(byte FilterCutOff)
  {
    this.FilterCutOff = FilterCutOff;
  }
  
  public String getsampleName()
{
  return this.sampleName;
}

public void setsampleName(String sampleName)
{
  this.sampleName = padString(sampleName, 12);
}

public int readKMP(String myKmpFile, int nbMultisample, int number)
{
  try
  {
    RandomAccessFile in = new RandomAccessFile(myKmpFile, "r");
    in.seek(28 + nbMultisample * 28 + number * 20);
    
    byte[] b = new byte[4];
    in.read(b);
    String id = new String(b);
    if (id.equals("MSP1"))
    {
      setidMSP1(id);
    }
    else
    {
      setidMSP1(null);in.close();return 1;
    }
    in.read(b);
  
    String ChSz = new String(b);
    setChunkSize(ChSz);
    
    String Iname = new String(b);
    setInstrumentName(Iname);
  
    int nbs = Integer.reverseBytes(in.readInt());
    setNbMultisample(nbs);
    
    int nbsa = Integer.reverseBytes(in.readInt());
    setNBSampleAttribute(nbsa);
    
    String MN01 = new String(b);
    setMN01(MN01);
    

    int ukn1 = Integer.reverseBytes(in.readInt());
    setUnknown1(ukn1);
    

    int sid = Integer.reverseBytes(in.readInt());
    setSampleID(sid);
    
    byte[] b1 = new byte[5];
    in.read(b1);
    String RLP = new String(b1);
    if (RLP.equals("RLP01"))
    {
      setRLP01(RLP);
    }
    else
    {
      setRLP01(null);in.close();return 1;
    }
    in.read(b1);
    
    this.Transpose = in.readByte (); 
    
    this.OriginalKey = in.readByte ();
    
    this.TopKey = in.readByte ();
    
    this.Tune = in.readByte ();
    
    this.Level = in.readByte ();
    
    this.Pan = in.readByte ();
    
    this.FilterCutOff = in.readByte ();
    
    byte[] b2 = new byte[12];
    in.read(b2);
    setsampleName(new String(b2));
    System.out.println(getsampleName());
    in.close();
  }
    catch (Exception ef)
    {
      ef.printStackTrace();
    }
    return 0;
  }

public int writeMultisampleKMPDescription(String myKmpFile, int nbMultisample, int number)
{
  try
  {
    System.out.println("MultisampleKMPDescription write : " + number);
    RandomAccessFile out = new RandomAccessFile(myKmpFile, "rw");
    out.seek(28 + nbMultisample * 28 + number * 20);
    
    out.writeShort(Short.reverseBytes(this.nbMultisample));
    System.out.println(this.nbMultisample);
    out.writeByte(this.Transpose);
    System.out.println(this.Transpose);
    out.writeByte(this.TopKey);
    System.out.println(this.TopKey);
    out.writeByte(this.OriginalKey);
    System.out.println(this.OriginalKey);
    out.writeByte(this.Tune);
    System.out.println(this.Tune);
    out.writeByte(this.Level);
    System.out.println(this.Level);
    out.writeByte(this.Pan);
    System.out.println(this.Pan);
    out.write(this.FilterCutOff);
    System.out.println(this.FilterCutOff);
    
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
