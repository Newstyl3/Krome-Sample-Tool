import java.io.RandomAccessFile;


public class KSC {
	private String idKorg = null;
	private int Intro = 0;
	private String KMPName;
	
	public String getidKorg()
	{
		return this.idKorg;
	}
	public void setidKorg (String idKorg)
	{
		this.idKorg = idKorg;
	}
		
	public int getIntro()
	  {
	    return this.Intro;
	  }
	  
	  public void setIntro(int Intro)
	  {
	    this.Intro = Intro;
	  }
	  
	  public String getKMPName()
	  {
		  return this.KMPName;
	  }
	  public void setKMPName (String KMPName)
	  {
		  this.KMPName = KMPName;
	  }
	 
	  public int readKSC(String myKscFile)
	  {
	    try
	    {
	      RandomAccessFile in = new RandomAccessFile(myKscFile,"r");
	      
	      byte[] b = new byte[5];
	      in.read(b);
	      String id = new String(b);
	      if (id.equals("KORG"))
	      {
	        setidKorg(id);
	      }
	      else
	      {
	        setidKorg(null);in.close();return 1;
	      }
	      in.read(b);   
	      
	      int intro = Integer.reverseBytes(in.readInt());
	      setIntro(intro);
	      
	      String KMPName = new String(b);
	      setKMPName(KMPName);
}
	    catch (Exception ef)
	      {
	        ef.printStackTrace();
	      }
	      return 0;
	  }
}
