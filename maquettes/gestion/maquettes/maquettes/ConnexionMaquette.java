package maquettes;

import java.util.function.Function;

import javax.persistence.EntityManager;

import controle.connection.Connexion;

public class ConnexionMaquette extends Connexion {

	public ConnexionMaquette() {
		super(null);
	}

	@Override
	public boolean controle(String nom, String motDePasse) {
		return true;
	}

	@Override
	public void fermeture() {
	}

	@Override
	public <R> R chercher(Function<EntityManager, R> fonction) {
		return null;
	}

	@Override
	public <R> R modifier(Function<EntityManager, R> fonction) {
		return null;
	}	
}
