package entite;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controle.connection.Connexion;
import controle.connection.TestConnexion;
import entite.crud.ModeReglementsCrud;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReglementTest {

	private static ModeReglementsCrud crud;

	private static int code;
	
	private static Connexion connexion;

	@BeforeClass
	public static void setUp() {
		connexion = TestConnexion.getConnexion();
		crud = new ModeReglementsCrud(connexion);	
		crud.lire().stream().forEach(reg -> crud.supprimer(reg.getCode()));
	}
	
	@AfterClass
	public static void tearDown() {
		connexion.fermeture();
	}
	
	@Test
	public void test_1_Creer() {
		
		List<ModeReglements> modes = crud.lire();
		int existant = modes.size();
		
		ModeReglements cree = crud.creer("vType");
		Assert.assertNotNull("Un mode n'a pas pu être créé", cree);

		List<?> old = modes;
		modes = crud.lire();
		Assert.assertEquals("Un mode n'a pas pu être lu", existant + 1, modes.size());

		Assert.assertEquals("Un mode a pu être lu", existant, old.size());
	}

	@Test
	public void test_2_Modifier() {
		List<ModeReglements> modes = crud.lire();
		int existant = modes.size();
		Assert.assertTrue("Des modes n'existent pas", existant > 0);

		ModeReglements mode = modes.get(0);
		Assert.assertEquals("nom non lu", "vType", mode.getType());

		code = mode.getCode();
		boolean modifie = crud.modifier(code, "type");
		
		Assert.assertTrue("Un mode n'a pas pu être modifié", modifie);

		Assert.assertEquals("Un mode a pu être lu", existant, modes.size());

		modes = crud.lire();
		Assert.assertEquals("Un mode n'a pas pu être lu", existant, modes.size());
		
		mode = modes.get(0);
		
		Assert.assertEquals("type non modifié", "type", mode.getType());
	}

	@Test
	public void test_3_Chercher() {
		List<ModeReglements> modes = crud.chercher("type");
		Assert.assertEquals("Des modes n'existent pas", 1, modes.size());

		modes = crud.chercher("code");
		Assert.assertEquals("Des modes existent", 0, modes.size());		
	}

	@Test
	public void test_4_Supprimer() {
		boolean efface = crud.supprimer(code);
		Assert.assertTrue("Le mode n'a pas été effacé", efface);

		List<ModeReglements> modes = crud.chercher("type");
		Assert.assertEquals("Des modes existent", 0, modes.size());

		modes = crud.lire();
		Assert.assertEquals("Des modes existent", 0, modes.size());
	}
}
