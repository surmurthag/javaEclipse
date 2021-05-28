package controle.connection;

public class TestConnexion {

	private TestConnexion() {
		super();
	}

	public static Connexion getConnexion() {
		return new Connexion("test");
	}
}
