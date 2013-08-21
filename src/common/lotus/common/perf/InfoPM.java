package lotus.common.perf;

public class InfoPM {
	
	private String name;
	
	private String state;
	
	private double value;
	
	private long cpuSec;
	
	private double cpuPer;
	
	private long memKb;
	
	private double memPer;
	
	private double nettxKb;
	
	private double netrxKb;
	
	private double vbdRd;
	
	private double vbdWr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getCpuSec() {
		return cpuSec;
	}

	public void setCpuSec(long cpuSec) {
		this.cpuSec = cpuSec;
	}

	public double getCpuPer() {
		return cpuPer;
	}

	public void setCpuPer(double cpuPer) {
		this.cpuPer = cpuPer;
	}

	public long getMemKb() {
		return memKb;
	}

	public void setMemKb(long memKb) {
		this.memKb = memKb;
	}

	public double getMemPer() {
		return memPer;
	}

	public void setMemPer(double memPer) {
		this.memPer = memPer;
	}

	public double getNettxKb() {
		return nettxKb;
	}

	public void setNettxKb(double nettxKb) {
		this.nettxKb = nettxKb;
	}

	public double getNetrxKb() {
		return netrxKb;
	}

	public void setNetrxKb(double netrxKb) {
		this.netrxKb = netrxKb;
	}

	public double getVbdRd() {
		return vbdRd;
	}

	public void setVbdRd(double vbdRd) {
		this.vbdRd = vbdRd;
	}

	public double getVbdWr() {
		return vbdWr;
	}

	public void setVbdWr(double vbdWr) {
		this.vbdWr = vbdWr;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}


	
}
