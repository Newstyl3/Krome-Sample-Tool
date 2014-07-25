import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;


public class KSF {
	private String idSMP1 = null;
	private String ChunkSize2 = null;
	private String SampleName;
	private int DefaultBlank = 0;
	private int LoopStartAdress;
	private int SampleEnd;
	private String idSMD1 = null;
	private String ChunkSize3 = null;
	private byte SampleRate;
	private byte Attributes;
	private byte AttributesCompId = 0;
	private byte AttributesCompressed = AttributesCompId;
	private int LoopTune;
	private int Channels = LoopTune;
	private int BitDepht = 0;
	private int SampleSize;
	private int StartAdress = LoopStartAdress + 1;
	private int StartAdress2 = StartAdress;
	private byte[] samples;
    private BufferedImage samplesImage;
    
    
	public String getidSMP1()
	{
		return this.idSMP1;
	}
	public void setidSMP1(String idSMP1)
	{
		this.idSMP1 = idSMP1;
	}
	
	public String getChunkSize2()
	{
		return this.ChunkSize2;
	}
	public void setChunkSize2(String ChunkSize2)
	{
		this.ChunkSize2 = ChunkSize2;
	}
	
	public String getSampleName()
	{
		return this.SampleName;
	}
	public void setSampleName(String SampleName)
	{
		this.SampleName = padString(SampleName, 16);
	}
	
	 public int getDefaultBlank()
	  {
	    return this.DefaultBlank;
	  }
	  
	  public void setDefaultBlank(int DefaultBlank)
	  {
	    this.DefaultBlank = DefaultBlank;
	  }
	
	  public int getLoopStartAdress()
	  {
	    return this.LoopStartAdress;
	  }
	  
	  public void setLoopStartAdress(int LoopStartAdress)
	  {
	    this.LoopStartAdress = LoopStartAdress;
	  }

	  public int getSampleEnd()
	  {
	    return this.SampleEnd;
	  }
	  
	  public void setSampleEnd(int SampleEnd)
	  {
	    this.SampleEnd = SampleEnd;
	  }
	  
	  public String getidSMD1()
		{
			return this.idSMD1;
		}
		public void setidSMD1(String idSMD1)
		{
			this.idSMD1 = idSMD1;
		}
		
		public String getChunkSize3()
		{
			return this.ChunkSize3;
		}
		public void setChunkSize3(String ChunkSize3)
		{
			this.ChunkSize3 = ChunkSize3;
		}
		
		public byte getSampleRate()
		  {
		    return this.SampleRate;
		  }
		  
		  public void setSampleRate(byte SampleRate)
		  {
		    this.SampleRate = SampleRate;
		  }
		  
		  public byte getAttributes()
		  {
		    return this.Attributes;
		  }
		  
		  public void setAttributes(byte Attributes)
		  {
		    this.Attributes = Attributes;
		  }
		  
		  public byte getAttributesCompId()
		  {
		    return this.AttributesCompId;
		  }
		  
		  public void setAttributesCompId(byte AttributesCompId)
		  {
		    this.AttributesCompId = AttributesCompId;
		  }
		  
		  public byte getAttributesCompressed()
		  {
		    return this.AttributesCompressed;
		  }
		  
		  public void setAttributesCompressed(byte AttributesCompressed)
		  {
		    this.AttributesCompressed = AttributesCompressed;
		  }
		  
		  public int getLoopTune()
		  {
		    return this.LoopTune;
		  }
		  
		  public void setLoopTune(int LoopTune)
		  {
		    this.LoopTune = LoopTune;
		  }

		  public int getBitDepht()
		  {
		    return this.BitDepht;
		  }
		  
		  public void setBitDepht(int BitDepht)
		  {
		    this.BitDepht = BitDepht;
		  }
		  
		  public int getChannels()
		  {
		    return this.Channels;
		  }
		  
		  public void setChannels(int Channels)
		  {
		    this.Channels = Channels;
		  }

		  public int getSampleSize()
		  {
		    return this.SampleSize;
		  }
		  
		  public void setSampleSize(int SampleSize)
		  {
		    this.SampleSize = SampleSize;
		  }
		  
		  public int getStartAdress ()
		  {
			  return this.StartAdress;
		  }
		  
		  public void setStartAdress (int StartAdress)
		  {
			  this.StartAdress = StartAdress;
		  }
		  
		  public int getStartAdress2()
		  {
			  return this.StartAdress2;
		  }
		  
		  public void setStartAdress2(int StartAdress2)
		  {
			  this.StartAdress2 = StartAdress2;
		  }
		  
		  public BufferedImage getSamplesImage()
		  {
		    return this.samplesImage;
		  }
		  
		  public void setSamplesImage(BufferedImage samplesImage)
		  {
		    this.samplesImage = samplesImage;
		  }
		  
		  
		  public int readKSF(String myKsfFile)
		  {
		    try
		    {
		      this.KSFFilePath = myKsfFile;	
		      
		      RandomAccessFile in = new RandomAccessFile(myKsfFile, "r");
		      		      
		      byte[] b = new byte[4];
		      in.read(b);
		      String id = new String(b);
		      if (id.equals("SMP1"))
		      {
		        setidSMP1(id);
		      }
		      else
		      {
		        setidSMP1(null);in.close();return 1;
		      }
		      in.read(b);
		    
		      String ChSz2 = new String(b);
		      setChunkSize2(ChSz2);
		      
		      String Sname = new String(b);
		      setSampleName(Sname);
		    
		      int dfb = Integer.reverseBytes(in.readInt());
		      setDefaultBlank(dfb);
		      
		      this.LoopStartAdress = Integer.reverseBytes (in.readInt()); 
		      System.out.println(Integer.toHexString(this.LoopStartAdress));
		      
		      this.SampleEnd = Integer.reverseBytes (in.readInt()); 
		      System.out.println(Integer.toHexString(this.SampleEnd));

		      
		      byte[] b1 = new byte[4];
		      in.read(b1);
		      String SMD1 = new String(b1);
		      if (SMD1.equals("SMD1"))
		      {
		        setidSMD1(SMD1);
		      }
		      else
		      {
		        setidSMD1(null);in.close();return 1;
		      }
		      in.read(b1);
		      
		      String ChSz3 = new String(b);
		      setChunkSize3(ChSz3);
 		   		      
		      this.SampleRate = in.readByte ();
		      
		      this.Attributes = in.readByte ();
		      
		      this.AttributesCompId = in.readByte ();
		      
		      this.AttributesCompressed = in.readByte ();
		      
		      this.LoopTune = in.readByte ();
		      
		      int ch = Integer.reverseBytes(in.readInt());
		      setChannels(ch);
		      
		      int bd = Integer.reverseBytes(in.readInt());
		      setBitDepht(bd);
		      
		      int ss = Integer.reverseBytes(in.readInt());
		      setSampleSize(ss);
		      		      
		      readKSFSamples();
		    }
		      catch (Exception ef)
		      {
		        ef.printStackTrace();
		      }
		      return 0;
		    }
		  
		  public int readKSFSamples()
		  {
		    try
		    {
		      RandomAccessFile in = new RandomAccessFile(this.KSFFilePath.replace("META", "SAMPLE"), "r");
		      

		      in.seek(this.LoopStartAdress);
		      

		      this.samples = new byte[(this.SampleEnd & 0xFFFFFF) - (this.StartAdress & 0xFFFFFF) + 1];
		      
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
		          filename = KMP.this.sampleName + ".RAW";
		        }
		        else
		        {
		          filename = KMP.String.getsampleName();
		          filename = filename.replace(" ", "_");
		          filename = filename.replace(".", "-");
		          filename = filename.replace("+", "-");
		          filename = filename.concat(".RAW");
		        }
		        System.out.println("Writing samples file to : " + this.KSFFilePath.replace("META.IMG", filename));
		        
		        RandomAccessFile out = new RandomAccessFile(this.KSFFilePath.replace("META.IMG", filename), "rw");
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
		  
		  
		  public int writeKSFWaveformDescription(String myKsfFile, int number)
		     {
			  try
			  {
		      System.out.println("Writing KSF description " + number);
		      this.KSFFilePath = myKsfFile;
		      
		      RandomAccessFile out = new RandomAccessFile(myKSFFile, "rw");
		      
		      out.writeInt(Integer.reverseBytes(this.StartAdress));
		      out.writeInt(Integer.reverseBytes(this.StartAdress2));
		      out.writeInt(Integer.reverseBytes(this.LoopStartAdress));
		      out.writeInt(Integer.reverseBytes(this.SampleEnd));
		      out.writeInt(Integer.reverseBytes(this.SampleSize));
		      

		      out.write(getSampleName().getBytes());
		      
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
