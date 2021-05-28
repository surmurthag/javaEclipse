package controle.utilitaires;

import org.junit.Assert;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class GestionDatesTest {

	@Test
	public void testDateEnChaine() {
		LocalDate date = LocalDate.of(2015, Month.JULY, 01);
		ZonedDateTime zone = date.atStartOfDay().atZone(ZoneId.systemDefault());
		String chaine = GestionDates.dateEnChaineFR(zone.toInstant());
		Assert.assertEquals("01/07/2015", chaine);
	}

	@Test
	public void testChaineEnDate() {
		ZoneId fuseau = ZoneId.systemDefault();
		try {
			Instant instant = GestionDates.chaineFRenDate("01/07/2015");
			LocalDateTime date = LocalDateTime.ofInstant(instant, fuseau);
			Assert.assertEquals(2015, date.getYear());
			Assert.assertEquals(Month.JULY, date.getMonth());
			Assert.assertEquals(1, date.getDayOfMonth());
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail("");
		}
	}

}
