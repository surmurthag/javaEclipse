package controle.connection;

import java.util.function.Function;

import javax.persistence.EntityManager;

public class Connexion {

	public Connexion(Object object) {
		// TODO Auto-generated constructor stub
	}

	public static Connexion getConnexion() {
		// TODO Auto-generated method stub
		return null;
	}

	public void fermeture() {
		// TODO Auto-generated method stub
		
	}

	public boolean controle(String nom, String motDePasse) {
		// TODO Auto-generated method stub
		return false;
	}

	public <R> R chercher(Function<EntityManager, R> fonction) {
		// TODO Auto-generated method stub
		return null;
	}

	public <R> R modifier(Function<EntityManager, R> fonction) {
		// TODO Auto-generated method stub
		return null;
	}

}
