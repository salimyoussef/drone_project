package endPoints;

import java.util.ArrayList;

/**
 * Created by mohannad on 02/01/16.
 */
public class EndPointProvider implements EndPointProviderIF {
	@Override
	public ArrayList<AdressEndPoints> getEndPoints() {
		// read from file
		AdressEndPoints adressEndPoints1 = new AdressEndPoints("Paris", "Lille");
		AdressEndPoints adressEndPoints2 = new AdressEndPoints("Paris", "Rennes");
		AdressEndPoints adressEndPoints3 = new AdressEndPoints("Rennes", "Lille");
		ArrayList<AdressEndPoints> adressEndPointses = new ArrayList<>();
		adressEndPointses.add(adressEndPoints1);
		adressEndPointses.add(adressEndPoints2);
		adressEndPointses.add(adressEndPoints3);

		return adressEndPointses;
	}
}
