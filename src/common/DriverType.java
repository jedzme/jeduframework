package common;

public enum DriverType {
	
	CHROME("chrome"),
	FIREFOX("firefox");
	
	private String driverType;
	
	DriverType(String driverType) {
		// TODO Auto-generated constructor stub
		this.driverType = driverType;
	}
	
	public String driverType(){
		return driverType;
	}

}
