package common;

public enum LocatorType {
	ID("id"),
	NAME("name"),
	LINKTEXT("linktext"),
	PARTIALLINKTEXT("partiallinktext"),
	CSS("css"),
	CLASS("class"),
	XPATH("xpath");
	
	private String locator;
	
	LocatorType(String locator) {
		// TODO Auto-generated constructor stub
		this.locator = locator;
	}
	
	public String locator(){
		return locator;
	}

}
