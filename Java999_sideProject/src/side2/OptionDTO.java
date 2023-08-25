package side2;

public class OptionDTO {
	private String optionId;
	private String apName;
	private int price;
	private int stock;
	private int apId;
	
	public OptionDTO() {}
	
	
	public OptionDTO(String optionId, String apName, int price, int stock, int apId) {
		this.optionId = optionId;
		this.apName = apName;
		this.price = price;
		this.stock = stock;
		this.apId = apId;
	}


	public String getOptionId() {
		return optionId;
	}


	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}


	public String getApName() {
		return apName;
	}


	public void setApName(String apName) {
		this.apName = apName;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public int getApId() {
		return apId;
	}


	public void setApId(int apId) {
		this.apId = apId;
	}


	@Override
	public String toString() {
		return "OptionDTO [optionId=" + optionId + ", apName=" + apName + ", price=" + price + ", stock=" + stock
				+ ", apId=" + apId + "]";
	}
	
	

}
