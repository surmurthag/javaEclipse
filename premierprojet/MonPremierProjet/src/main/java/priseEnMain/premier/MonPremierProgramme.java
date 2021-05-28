package priseEnMain.premier;

public class MonPremierProgramme {

	public String getPrenom() {
		return prenom;
	}

	public MonPremierProgramme(String prenom) {
		super();
		this.prenom = prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	private String prenom;
	
	public String moi() {
		return String.format(Messages.getString("MonPremierProgramme.0"), prenom); //$NON-NLS-1$
	}
	
	public void afficher() {
		System.out.println(moi());
	}

	public static void main(String[] args) {
		System.out.println("1ere ligne");
		System.out.println("2eme ligne");
		System.out.println("3eme ligne");
	}
}
