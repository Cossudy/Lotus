package lotus.worker.daemon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import lotus.common.perf.InfoVM;

public class StatVM {
	
	
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

		// List<InfoVM> all = new ArrayList<InfoVM>();

		for (int i = 0; i < 10; i++) {

			String info = FileReader("F:\\123.log");

			System.out.println(info);

			String[] lines = info.split("\n");

			System.out.println(lines[1]);

			String[] words = lines[1].split(" ");

			// int index = 0;
			// for(String k : words){
			// System.out.print(index + "  ");
			// System.out.println(k);
			// index++;
			// }

			InfoVM infoVM = new InfoVM();

			infoVM.setName(words[2]);
			infoVM.setState(words[3]);
			infoVM.setCpuSec(Long.parseLong(words[12]));
			infoVM.setCpuPer(Double.parseDouble(words[16]));
			infoVM.setMemKb(Long.parseLong(words[20]));
			infoVM.setMemPer(Double.parseDouble(words[23]));
			infoVM.setNettxKb(Double.parseDouble(words[51]));
			infoVM.setNetrxKb(Double.parseDouble(words[59]));
			infoVM.setVbdRd(Double.parseDouble(words[79]));
			infoVM.setVbdWr(Double.parseDouble(words[87]));

			System.out.println(infoVM.getName());
			System.out.println(infoVM.getState());
			System.out.println(infoVM.getCpuSec());
			System.out.println(infoVM.getCpuPer());
			System.out.println(infoVM.getMemKb());
			System.out.println(infoVM.getMemPer());
			System.out.println(infoVM.getNettxKb());
			System.out.println(infoVM.getNetrxKb());
			System.out.println(infoVM.getVbdRd());
			System.out.println(infoVM.getVbdWr());

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// all.add(infoVM);

	}
			
		

}
