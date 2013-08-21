package lotus.worker.daemon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import lotus.common.perf.InfoPM;

public class StatPM {
	
	
	public static String FileReader(String path) throws IOException {

		File file = new File(path);
		System.out.println(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();

		FileInputStream fis = new FileInputStream(file);

		byte[] buf = new byte[1024];

		StringBuffer sb = new StringBuffer();

		while (fis.read(buf) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];
		}

		fis.close();

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

		// List<infoPM> all = new ArrayList<infoPM>();
		
			String hostname = FileReader("F:\\hostname.log");

		for (int i = 0; i < 1; i++) {

			String info = FileReader("F:\\pm_top.log");

			System.out.println(info);

			String[] lines = info.split("\n");

			System.out.println(lines[0]);

			String[] words = lines[0].split(" ");

//			 int index = 0;
//			 for(String k : words){
//			 System.out.print(index + "  ");
//			 System.out.println(k);
//			 index++;
//			 }
			 
			 String disk_info = FileReader("F:\\pm_disk_rw.log");
			 
			 System.out.println(disk_info);
			 
			 String[] disk_lines = disk_info.split("\n");
			 
			 System.out.println(disk_lines[0]);
			 
			 String [] disk_words = disk_lines[0].split(" ");
			 
//			 int index = 0;
//			 for(String k : disk_words){
//			 System.out.print(index + "  ");
//			 System.out.println(k);
//			 index++;
//			 }
			 

			InfoPM infoPM = new InfoPM();

			infoPM.setName(hostname);
			infoPM.setState("running");
			infoPM.setCpuSec(0);
			infoPM.setCpuPer(Double.parseDouble(words[3]));
			infoPM.setMemKb(Long.parseLong(words[7]));
			infoPM.setMemPer(Double.parseDouble(words[7])/Double.parseDouble(words[5]));
			infoPM.setNettxKb(Double.parseDouble(words[13]));
			infoPM.setNetrxKb(Double.parseDouble(words[15]));
			infoPM.setVbdRd(Double.parseDouble(disk_words[9]));
			infoPM.setVbdWr(Double.parseDouble(disk_words[21]));
			
			double whole_value = (1/(1-infoPM.getCpuPer())) * (1/(1-infoPM.getMemPer())) * (1/(1-infoPM.getNettxKb())) * (1/(1-infoPM.getNetrxKb())) * (1/(1-infoPM.getVbdRd())) * (1/(1-infoPM.getVbdWr()));
			
			infoPM.setValue(whole_value);
			

			System.out.println(infoPM.getName());
			System.out.println(infoPM.getState());
			System.out.println(infoPM.getValue());
			System.out.println(infoPM.getCpuSec());
			System.out.println(infoPM.getCpuPer());
			System.out.println(infoPM.getMemKb());
			System.out.println(infoPM.getMemPer());
			System.out.println(infoPM.getNettxKb());
			System.out.println(infoPM.getNetrxKb());
			System.out.println(infoPM.getVbdRd());
			System.out.println(infoPM.getVbdWr());

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// all.add(infoPM);

	}
			
		

}
