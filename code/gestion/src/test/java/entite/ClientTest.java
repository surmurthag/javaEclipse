package entite;

import java.time.Instant;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controle.connection.Connexion;
import controle.connection.TestConnexion;
import entite.crud.ClientCrud;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientTest {

	private static ClientCrud crud;

	private static Connexion connexion;

	@BeforeClass
	public static void setUp() {
		connexion = TestConnexion.getConnexion();
		crud = new ClientCrud(connexion);		
	}
	
	@AfterClass
	public static void tearDown() {
		connexion.fermeture();
	}
		
	@Test
	public void test_1_Creer() {
		
		List<Client> clients = crud.lire();
		int existant = clients.size();
		
		Client client = new Client("code", "nom", "prenom", true, Instant.now());
		try {
			crud.creer(client);
		} catch(Exception e) {
			Assert.fail("Un client n'a pas pu être créé");			
		}

		List<?> old = clients;
		clients = crud.lire();
		Assert.assertEquals("Un client n'a pas pu être lu", existant + 1, clients.size());

		Assert.assertEquals("Un client a pu être lu", existant, old.size());
	}

	@Test
	public void test_2_Modifier() {
		List<Client> clients = crud.lire();
		int existant = clients.size();
		Assert.assertTrue("Des clients n'existent pas", existant > 0);

		Client client = clients.get(0);
		Assert.assertEquals("nom non lu", "nom", client.getNom());
		Assert.assertEquals("prénom non lu", "prenom", client.getPrenom());
		Assert.assertEquals("fidélité non lue", true, client.isCarteFidele());

		client.setNom("famille");
		client.setPrenom("surnom");
		client.setCarteFidele(false);
		client.setDateCreation(Instant.now());
		client = crud.modifier(client);
		Assert.assertNotNull("Un client n'a pas pu être modifié", client);

		Assert.assertEquals("Un client a pu être lu", existant, clients.size());

		clients = crud.lire();
		Assert.assertEquals("Un client n'a pas pu être lu", existant, clients.size());
		
		client = clients.get(0);
		
		Assert.assertEquals("nom non modifié", "famille", client.getNom());
		Assert.assertEquals("prénom non modifié", "surnom", client.getPrenom());
		Assert.assertEquals("fidélité non modifiée", false, client.isCarteFidele());
	}

	@Test
	public void test_3_Chercher() {
		List<Client> clients = crud.chercher("code", "famille", "surnom");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());

		clients = crud.chercher("code", "nom", "prenom");
		Assert.assertEquals("Des clients existent", 0, clients.size());		

		clients = crud.chercher("faux", "famille", "surnom");
		Assert.assertEquals("Des clients existent", 0, clients.size());		

		clients = crud.chercher("code", "famille", "prenom");
		Assert.assertEquals("Des clients existent", 0, clients.size());		

		clients = crud.chercher("code", "nom", "surnom");
		Assert.assertEquals("Des clients existent", 0, clients.size());		
	}

	@Test
	public void test_4_Chercher() {
		List<Client> clients = crud.chercher("code");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());

		clients = crud.chercher("surnom");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());

		clients = crud.chercher("famille");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());

		clients = crud.chercher("vcode");
		Assert.assertEquals("Des clients existent", 0, clients.size());		

		clients = crud.chercher("nom");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());

		clients = crud.chercher("code");
		Assert.assertEquals("Des clients n'existent pas", 1, clients.size());
	}

	@Test
	public void test_5_Supprimer() {
		boolean efface = crud.supprimer("code");
		Assert.assertTrue("Le client n'a pas été effacé", efface);

		List<Client> clients = crud.chercher("code");
		Assert.assertEquals("Des clients existent", 0, clients.size());

		clients = crud.lire();
		Assert.assertEquals("Des clients existent", 0, clients.size());
	}

	@Test
	public void test_6_Lire_non() {
		Client client = crud.lire("codeNonExistant");
		Assert.assertNull("Le client a été trouvé", client);
	}
}
