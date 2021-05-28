package entite;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controle.connection.Connexion;
import controle.connection.TestConnexion;
import controle.utilitaires.GestionDates;
import entite.crud.ClientCrud;
import entite.crud.CommandeCrud;
import entite.crud.EniException;
import entite.crud.ModeReglementsCrud;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandeTest {

	private static CommandeCrud crud;

	private static Connexion connexion;

	@BeforeClass
	public static void setUp() {
		connexion = TestConnexion.getConnexion();
		crud = new CommandeCrud(connexion);		
	}
	
	@AfterClass
	public static void tearDown() {
		connexion.fermeture();
	}
		
	@Test
	public void test_0_vide() {
		List<Commande> commandes = crud.lire();
		Assert.assertTrue(commandes.isEmpty());
	}
	
	@Test
	public void test_1_Creer() {
		
		List<Commande> commandes = crud.lire();
		int existant = commandes.size();
		
		ClientTest clientTest = new ClientTest();
		ClientTest.setUp();
		clientTest.test_1_Creer();
		
		ClientCrud clientCrud = new ClientCrud(connexion);
		List<Client> clients = clientCrud.lire();
		Assert.assertFalse(clients.isEmpty());
		
		Client client = clients.get(0);

		ModeReglementsCrud reglementCrud = new ModeReglementsCrud(connexion);

		ModeReglements reglements = reglementCrud.creer("reglType");
		reglementCrud.lire();
		Commande commande = new Commande("cmd");
		commande.setClient(client);
		commande.setReglement(reglements);
		commande.maintenant();
		try {
			crud.creer(commande);
		} catch(Exception e) {
			Assert.fail("Une commande n'a pas pu être créé");			
		}

		List<?> old = commandes;
		commandes = crud.lire();
		Assert.assertEquals("Une commande n'a pas pu être lue", existant + 1, commandes.size());

		Assert.assertEquals("Une commande a pu être lue", existant, old.size());
	}
	
	@Test
	public void test_2_Modifier() {
		List<Commande> commandes = crud.lire();
		int existant = commandes.size();
		Assert.assertTrue("Des commandes n'existent pas", existant > 0);

		Commande commande = commandes.get(0);

		LocalDateTime maintenant = commande.maintenant();
		try {
			commande = crud.modifier(commande);
		} catch (EniException e) {
			Assert.fail(e.getMessage());
		}
		
		Assert.assertTrue("Une commande n'a pas pu être modifiée", commande != null);

		commandes = crud.lire();
		Assert.assertEquals("Une commande n'a pas pu être lue", existant, commandes.size());
		
		commande = commandes.get(0);
		
		Assert.assertEquals("Date non modifiée",  GestionDates.instant(maintenant), commande.getInstant());
	}

	@Test
	public void test_3_Chercher() {
		List<Commande> commandes = crud.chercher("reglType");
		Assert.assertEquals("Des commandes n'existent pas", 1, commandes.size());
		
		commandes = crud.chercher("nom");
		Assert.assertEquals("Des commandes n'existent pas", 1, commandes.size());
		
		commandes = crud.chercher("prénom");
		Assert.assertEquals("Des commandes n'existent pas", 1, commandes.size());

		commandes = crud.chercher("cmd");
		Assert.assertEquals("Des commandes n'existent pas", 1, commandes.size());
		
		commandes = crud.chercher(commandes.get(0).getCode());
		Assert.assertEquals("Des commandes n'existent pas", 1, commandes.size());
		
		commandes = crud.chercher("code");
		Assert.assertEquals("Des commandes existent", 0, commandes.size());		

		commandes = crud.chercher("foobar");
		Assert.assertEquals("Des commandes existent", 0, commandes.size());		
	}

	@Test
	public void test_4_Supprimer() {
		List<Commande> commandes = crud.lire();
		int existant = commandes.size();
		Assert.assertEquals("Une commande n'a pas pu être lue", 1, existant);
		
		Commande commande = commandes.get(0);

		try {
			crud.supprimer(commande.getCode());
		} catch (EniException e) {
			Assert.fail("La commande n'a pas été effacée");
		}

		List<?> recherche = crud.chercher("type");
		Assert.assertEquals("Des modes existent", existant -1, recherche.size());

		recherche = crud.chercher("45");
		Assert.assertEquals("Des modes existent", existant -1, recherche.size());

		commandes = crud.lire();
		Assert.assertEquals("Des modes existent", existant -1, commandes.size());
	}

}
