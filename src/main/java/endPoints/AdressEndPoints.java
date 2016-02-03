package endPoints;

/**
 * Created by mohannad on 02/01/16.
 */
public class AdressEndPoints {
	String startAdrr;
	String endAdrr;

	public AdressEndPoints(String startAdrr, String endAdrr) {
		this.startAdrr = startAdrr;
		this.endAdrr = endAdrr;
	}

	public String getStartAdrr() {
		return startAdrr;
	}

	public void setStartAdrr(String startAdrr) {
		this.startAdrr = startAdrr;
	}

	public String getEndAdrr() {
		return endAdrr;
	}

	public void setEndAdrr(String endAdrr) {
		this.endAdrr = endAdrr;
	}
}
