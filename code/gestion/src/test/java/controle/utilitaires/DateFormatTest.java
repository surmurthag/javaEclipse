package controle.utilitaires;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import controle.connection.Connexion;
import controle.connection.TestConnexion;

public class DateFormatTest {

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
	public void test() {
		LocalDate localdate = LocalDate.of(2015, Month.MAY, 25);
		LocalTime localtime = LocalTime.of(21, 26, 33);
		ZonedDateTime zone = localdate.atTime(localtime).atZone(ZoneId.systemDefault());
		
		Instant instant = zone.toInstant();

		TestDate saved = connexion.modifier((em) -> {			
			TestDate test1 = new TestDate();
			Date date = Date.from(instant);
			test1
				.setHorodatage(date)
				.setDate(date)
				.setHeure(date)
				;

			em.persist(test1);
			
			em.flush();
			return test1;
		});
		long clePrimaire = saved.getClePrimaire();
		
//		tearDown();
		TimeZone.setDefault(TimeZone.getTimeZone("America/Buenos_Aires"));
//		setUp();
		
		TestDate trouve = connexion.chercher((em) -> {
			return em.find(TestDate.class, Long.valueOf(clePrimaire));
		});
		System.out.println("trouvé: " +trouve);
		
		String fuseau = trouve.getFuseau();
		Instant horodatage = trouve.getHorodatage().toInstant();
		int offset = ZoneId.of(fuseau).getRules().getOffset(horodatage).getTotalSeconds();
		int offset2 = ZoneId.systemDefault().getRules().getOffset(horodatage).getTotalSeconds();
		
		Instant shifted = horodatage.minusSeconds(offset - offset2);
		
		Instant date = trouve.getDate().toInstant();
		Instant heure = trouve.getHeure().toInstant();
		
		ZoneId systemDefault = ZoneId.systemDefault();
		LocalDate localdate2 = date.atZone(systemDefault).toLocalDate();
		LocalTime localtime2 = heure.atZone(systemDefault).toLocalTime();
		
		Assert.assertEquals(localdate, localdate2);
		Assert.assertEquals(localtime, localtime2);
		Assert.assertEquals(instant, shifted);
	}

	@Test
	public void testConversionBsAs() {
		LocalDate localdate = LocalDate.of(2015, Month.MAY, 25);
		LocalTime localtime = LocalTime.of(21, 26, 33);
		String fuseau = "America/Buenos_Aires";
		testConversion(localdate, localtime, fuseau);
	}

	@Test
	public void testConversionKtm() {
		LocalDate localdate = LocalDate.of(2015, Month.MAY, 25);
		LocalTime localtime = LocalTime.of(21, 26, 33);
		String fuseau = "Asia/Kathmandu";
		testConversion(localdate, localtime, fuseau);
	}

	private void testConversion(LocalDate localdate, LocalTime localtime,
			String fuseau) {
		LocalDateTime localDateTime = localdate.atTime(localtime);
		
		TestDateConverted saved = connexion.modifier((em) -> {			
			TestDateConverted test1 = new TestDateConverted();
			test1
				.setHorodatage(localDateTime)
				;

			em.persist(test1);
			
			em.flush();
			return test1;
		});
		long clePrimaire = saved.getClePrimaire();
		
//		tearDown();
		TimeZone.setDefault(TimeZone.getTimeZone(fuseau));
//		setUp();
		
		TestDateConverted trouve = connexion.chercher((em) -> {
			return em.find(TestDateConverted.class, Long.valueOf(clePrimaire));
		});
		System.out.println("trouvé: " +trouve);
		
		LocalDateTime horodatage = trouve.getHorodatage();
				
		Assert.assertEquals(localDateTime, horodatage);
	}
	
	@Test
	public void testNow() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime now2 = LocalDateTime.now(ZoneId.of("UTC"));
		Instant instant = ZonedDateTime.of(now2, ZoneId.of("UTC")).toInstant();
		
		LocalDate date = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalDate();
		System.out.println();
	}
}
