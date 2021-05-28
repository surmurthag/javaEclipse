package entite;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controle.connection.Connexion;
import controle.connection.TestConnexion;
import entite.crud.ArticleCrud;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleTest {

	private static Connexion connexion;

	@BeforeClass
	public static void setUp() {
		connexion = TestConnexion.getConnexion();
	}
	
	@AfterClass
	public static void tearDown() {
		connexion.fermeture();
	}
	
	@Test
	public void test_1_creer() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		List<Article> articles = crud.lire();
		Assert.assertEquals("Des articles existents déjà", 0, articles.size());
		
		Article article = new Article("code1", "cat", "désignation", 10, 1.08, Instant.now());
		
		try {
			crud.creer(article);
		} catch(Exception e) {
			Assert.fail("Un article n'a pas pu être créé");			
		}

		List<Article> old = articles;
		articles = crud.lire();
		Assert.assertEquals("Un article n'a pas pu être lu", 1, articles.size());

		Assert.assertEquals("Un article a pu être lu", 0, old.size());
	}

	@Test
	public void test_2_lire() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		List<Article> articles = crud.lire();
		Assert.assertTrue("Des articles n'existents pas", articles.size() > 0);

		Optional<Article> article = crud.lire("code1");
		Assert.assertTrue("Pas d'article avec un code", article.isPresent());
	}

	@Test
	public void test_3_modifier() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		Optional<Article> peutEtre = crud.lire("code1");
		Assert.assertTrue("Pas d'article avec un code", peutEtre.isPresent());

		Article article = peutEtre.get();
		Assert.assertEquals("Pas de bonne référence catégorie", "cat", article.getCategorie().getCode());
		Assert.assertEquals("Pas de bonne désignation", "désignation", article.getDesignation());
		Assert.assertEquals("Pas de bonne quantité", 10, article.getQuantite());
		Assert.assertEquals("Pas de bonne quantité", 1.08, article.getPrixUnitaire(), 0.0001);

		boolean modifie = crud.modifier("code1", "vReference", "vDesignation", 3, 0.42);
		Assert.assertTrue(modifie);
		
		peutEtre = crud.lire("code1");
		article = peutEtre.get();
		Assert.assertEquals("Pas de bonne référence catégorie", "vReference", article.getCategorie().getCode());
		Assert.assertEquals("Pas de bonne désignation", "vDesignation", article.getDesignation());
		Assert.assertEquals("Pas de bonne quantité", 3, article.getQuantite());
		Assert.assertEquals("Pas de bonne quantité", 0.42, article.getPrixUnitaire(), 0.0001);
	}

	@Test
	public void test_4_chercher() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		List<Article> articles = crud.chercherTous("code1");
		Assert.assertEquals("Pas d'article avec un code", 1, articles.size());
		
		articles = crud.chercherTous("vReference");
		Assert.assertEquals("Pas d'article avec une référence", 1, articles.size());

		articles = crud.chercherTous("Designation");
		Assert.assertEquals("Pas d'article avec une désignation", 1, articles.size());
	}

	@Test
	public void test_5_chercher() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		List<Article> articles = crud.chercherParCode("code1");
		Assert.assertEquals("Pas d'article avec un code", 1, articles.size());
		
		articles = crud.chercherParCode("code2");
		Assert.assertEquals("Article avec un code", 0, articles.size());

		articles = crud.chercherParCode("code");
		Assert.assertEquals("Article avec un code", 0, articles.size());
	}

	@Test
	public void test_6_supprimer() {
		ArticleCrud crud = new ArticleCrud(connexion);
		
		Optional<Article> article = crud.lire("code1");
		Assert.assertTrue("Pas d'article avec un code", article.isPresent());

		boolean supprime = crud.supprimer("code1");
		Assert.assertTrue(supprime);
		
		article = crud.lire("code1");
		Assert.assertFalse(article.isPresent());
	}
}
