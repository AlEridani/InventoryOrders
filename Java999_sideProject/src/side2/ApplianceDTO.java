package side2;

public class ApplianceDTO {
	private int apID;
	private String apName;
	private String apMfr;
	private String apInfo;



	public ApplianceDTO() {}

	public ApplianceDTO(int apID, String apName, String apMfr, String apInfo) {
		this.apID = apID;
		this.apName = apName;
		this.apMfr = apMfr;
		this.apInfo = apInfo;
	}
	
	public ApplianceDTO( String apName, String apMfr, String apInfo) {
		this.apName = apName;
		this.apMfr = apMfr;
		this.apInfo = apInfo;
	}

	public int getApID() {
		return apID;
	}

	public void setApID(int apID) {
		this.apID = apID;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}


	public String getApMfr() {
		return apMfr;
	}

	public void setApMfr(String apMfr) {
		this.apMfr = apMfr;
	}

	public String getApInfo() {
		return apInfo;
	}

	public void setApInfo(String apInfo) {
		this.apInfo = apInfo;
	}
	@Override
	public String toString() {
		return "ApplianceDTO [apName=" + apName + ", apMfr=" + apMfr + ", apInfo=" + apInfo + "]";
	}





}