package controle.etat;

import java.util.List;

import controle.connection.Connexion;
import entite.Client;
import entite.crud.ClientCrud;

public class FabriqueClients {

	public static List<Client> createBeanCollection() {
		Connexion connexion = Connexion.getConnexion();
		ClientCrud crud = new ClientCrud(connexion);
		List<Client> clients = crud.lire();
		return clients;
	}
}
