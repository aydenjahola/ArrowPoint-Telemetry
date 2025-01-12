package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prohelion.canbus.model.CanPacket;
import com.prohelion.canbus.model.UdpPacket;

@Component
public class LogReplayer {

    @Autowired
   	private CarTestUtils carTestUtils;
	
    static CanPacket canPacket;
    static UdpPacket udpPacket = new UdpPacket();	
	
	public void start (String[] arg) {
		
		String line = "";
		String cvsSplitBy = ",";
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		
		System.out.println("Prohelion Log Replayer V1.0");
		System.out.println("Note: Filenames should entered using Java form");
		System.out.println("D:/temp/car-park-test.csv");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
	 
			Date previousDate = null;
			Date parsedDate = null;
			long diff = 0;
			
			System.out.print("Enter the log you wish to replay > ");
	        String csvFile = br.readLine();
			
	        System.out.print("Do you wish to loop the file (Y/N) > ");
	        String repeat = br.readLine();
	        boolean repeatLoop = true;
	 
	        while (repeatLoop) {
	        	repeatLoop = false;

				br = new BufferedReader(new FileReader(csvFile));
				
				// Remove first line in case it is the header
				line = br.readLine();
				
				while ((line = br.readLine()) != null) {							
		 				
				    // use comma as separator
					String[] can = line.split(cvsSplitBy);
		 				
					previousDate = parsedDate;
					
					try {
						parsedDate = formatter.parse(can[0]);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					//
					if (parsedDate != null && previousDate != null)
						diff = parsedDate.getTime() - previousDate.getTime();
					
					try {
						if ( diff > 1000 ) diff = 1000;
						Thread.sleep(diff);					
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
									
					System.out.println("Delay = " + diff + " : CanPackey [time = " + can[0] + " , id = " + can[2] + " , data=" + can[4] + "]");	 
					carTestUtils.sendCan("0" + can[2].substring(3, 6),can[4].substring(3, 19));				
					
				}

				br.close();
	        	if (repeat != null && repeat.toUpperCase().startsWith("Y")) {
	        		repeatLoop = true;
	        		previousDate = null;
	    			parsedDate = null;
	        		System.out.println("About to replay file");
	        	}
	        	
	        }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	  }		
		
}
		 
      
