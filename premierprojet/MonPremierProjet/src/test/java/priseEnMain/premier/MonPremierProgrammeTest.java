package priseEnMain.premier;

import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.Assert;
import org.junit.Test;

public class MonPremierProgrammeTest {

	@Test
	public void testMessages() {
		String string = null;
		try {
			string = Messages.getString("inexistant", Locale.FRENCH);
			Assert.fail("Message inconnu récupéré");
		} catch(MissingResourceException e) {
			Assert.assertNotNull(e);
		}
		string = Messages.getString("MonPremierProgramme.0", Locale.FRENCH);
		Assert.assertEquals("Message connu au mauvais format en Français", "C'est %s!", string);		

		string = Messages.getString("MonPremierProgramme.0", Locale.ENGLISH);
		Assert.assertEquals("Message connu au mauvais format en Anglais", "It's %s!", string);		

		string = Messages.getString("MonPremierProgramme.0", Locale.ITALIAN);
		Assert.assertEquals("Message connu au mauvais format en Italien", "C'est %s!", string);		
	}
	
	@Test
	public void testMonPremierProgramme() {
		Locale.setDefault(Locale.FRENCH);
		MonPremierProgramme test = new MonPremierProgramme("Fred");
		
		String affichage = test.moi();
		
		Assert.assertEquals("Ce n'est pas Fred!", "Fred", test.getPrenom());
		Assert.assertEquals("L'affichage est mauvais", "C'est Fred!", affichage);
		
		test.afficher();
	}

	@Test
	public void testMonPremierProgrammeAnglais() {
		Locale.setDefault(Locale.ENGLISH);
		MonPremierProgramme test = new MonPremierProgramme("Fred");
		
		String affichage = test.moi();
		
		Assert.assertEquals("Ce n'est pas Fred!", "Fred", test.getPrenom());
		Assert.assertEquals("L'affichage est mauvais", "It's Fred!", affichage);
		
		test.afficher();
	}
}
