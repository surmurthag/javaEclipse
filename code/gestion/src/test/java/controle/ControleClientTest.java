package controle;

import java.time.Instant;
import java.util.UUID;

import org.junit.Test;

import controle.connection.Connexion;
import controle.connection.TestConnexion;
import controle.modele.ModeleClients;
import entite.Client;
import entite.crud.ClientCrud;

public class ControleClientTest {

	@Test
	public void test() {
		Connexion connexion = TestConnexion.getConnexion();
		
		ClientCrud crud = new ClientCrud(connexion);
		Client unClient = new Client(UUID.randomUUID().toString(), "Nom", "Prenom", true, Instant.now());
		crud.creer(unClient);
		
		ControleClient controle = new ControleClient(connexion);
		ModeleClients modele = controle.getModele();
		Client client = modele.getClient(0);
		System.out.println("Nom du premier client " +client.getNom());
		connexion.fermeture();
	}
}
