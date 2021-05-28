package test;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import controle.connection.Connexion;
import controle.connection.TestConnexion;

@Entity
public class TestJPA {
	@Id
	private long clePrimaire;

	@Basic
	private String message;

	private static void proprietes() {
		EntityManagerFactory fabrique = Persistence.createEntityManagerFactory("eni-acces");

		EntityManager m = fabrique.createEntityManager();
		System.out.println(m.getProperties());
	}

	public static void main(String[] args) {

		Connexion connexion = TestConnexion.getConnexion();
		connexion.modifier((em) -> {
			TypedQuery<TestJPA> query = em.createQuery("SELECT t FROM TestJPA t", TestJPA.class);
			query.getResultList();
			System.out.println(em.getProperties());

			TestJPA test1 = new TestJPA();
			test1.clePrimaire = 1;
			test1.message = "message 1";

			TestJPA test2 = new TestJPA();
			test2.clePrimaire = 2;
			test2.message = "message 2";

			TestJPA test3 = new TestJPA();
			test3.clePrimaire = 3;
			test3.message = "message 3";

			em.persist(test1);
			em.persist(test2);
			em.persist(test3);

			em.flush();

			em.remove(test3);
			return null;
		});
		TestJPA trouve = connexion.chercher((em) -> {
			return em.find(TestJPA.class, Long.valueOf(1));
		});
		System.out.println("trouvé: " + trouve);
		List<?> resultats = connexion.chercher((em) -> {
			Query requete = em.createQuery("SELECT test FROM TestJPA test");
			return requete.getResultList();
		});
		System.out.println("trouvés: " + resultats);

		connexion.fermeture();
	}

	@Override
	public String toString() {
		return "TestJPA [clePrimaire=" + clePrimaire + ", message=" + message + "]";
	}
}
