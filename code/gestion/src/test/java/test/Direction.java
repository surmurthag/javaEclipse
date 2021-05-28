package test;

public enum Direction {
	NORD("septentrion"), SUD("midi"), EST("levant"), OUEST("ponant");
	
	private String ancienNom;

	Direction(String nom) {
		ancienNom = nom;
	}
	
	public String getAncienNom() {
		return ancienNom;
	}
}