package lotus.worker.daemon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import lotus.common.perf.InfoPM;
import lotus.common.service.SimpleDaemon;
import lotus.master.api.MasterProxy;
import lotus.worker.LotusWorker;

public class StatPMDaemon extends SimpleDaemon{
	
	 public static final long HEARTBEAT_INTERVAL = 1000;

	 public StatPMDaemon() {
	     super(HEARTBEAT_INTERVAL);
	 }
	 
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

	   
	@Override
	protected void workOneRound() {
		// TODO Auto-generated method stub
		
		String hostname = null;
		try {
			hostname = FileReader("F:\\hostname.log");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String info = null;
		try {
			info = FileReader("F:\\pm_top.log");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.println(info);

		String[] lines = info.split("\n");

		System.out.println(lines[0]);

		String[] words = lines[0].split(" ");

//		 int index = 0;
//		 for(String k : words){
//		 System.out.print(index + "  ");
//		 System.out.println(k);
//		 index++;
//		 }
		 
		 String disk_info = null;
		try {
			disk_info = FileReader("F:\\pm_disk_rw.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 System.out.println(disk_info);
		 
		 String[] disk_lines = disk_info.split("\n");
		 
		 System.out.println(disk_lines[0]);
		 
		 String [] disk_words = disk_lines[0].split(" ");
		 
//		 int index = 0;
//		 for(String k : disk_words){
//		 System.out.print(index + "  ");
//		 System.out.println(k);
//		 index++;
//		 }
		 

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
		
		
		 if (this.isStopping() == false) {
	            MasterProxy master = LotusWorker.getInstance().getMaster();
	            if (master != null) {
	                master.sendPnodePerInfo(infoPM);
	            }
	        }
		

//		System.out.println(infoPM.getName());
//		System.out.println(infoPM.getState());
//		System.out.println(infoPM.getValue());
//		System.out.println(infoPM.getCpuSec());
//		System.out.println(infoPM.getCpuPer());
//		System.out.println(infoPM.getMemKb());
//		System.out.println(infoPM.getMemPer());
//		System.out.println(infoPM.getNettxKb());
//		System.out.println(infoPM.getNetrxKb());
//		System.out.println(infoPM.getVbdRd());
//		System.out.println(infoPM.getVbdWr());

	
	}

}
