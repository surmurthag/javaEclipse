package dates;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DatesTest {

	@Test
	public void testZoneId() {
		System.out.println("Zone:" +ZoneId.systemDefault());
		System.out.println("Heure d'été/hiver:" +ZoneId.systemDefault().getRules().getDaylightSavings(Instant.now()));
		System.out.println("Prochain ajustement:" +ZoneId.systemDefault().getRules().nextTransition(Instant.now()));
		
		ZoneId.getAvailableZoneIds().stream().forEach(id ->
			System.out.println(id)
		);
//		System.out.println(ZoneId.getAvailableZoneIds());
	}

	@Test
	public void testInstant() {
//		Instant maintenant = Instant.now();
		Instant maintenant = LocalDateTime.of(2015, Month.JUNE, 01, 19, 42, 14).toInstant(ZoneOffset.UTC);
		System.out.println("Instant:" +maintenant);
		
		ZoneId ici = ZoneId.systemDefault();
		LocalDateTime dateHeure = LocalDateTime.ofInstant(maintenant, ici);
		ZonedDateTime aParis = maintenant.atZone(ici);
		System.out.println("Date locale Ici: " +dateHeure);
		System.out.println("Date zonée Ici : " +dateHeure.atZone(ici));
		System.out.println("Maintenant Ici : " +aParis);
		System.out.println("Un instant Ici : " +aParis.toInstant());
		System.out.println();

		ZoneId ktm = ZoneId.of("Asia/Katmandu");
		
		dateHeure = LocalDateTime.ofInstant(maintenant, ktm);
		System.out.println("Date locale à Katmandou: " +dateHeure);
		System.out.println("Date zonée à Katmandou : " +dateHeure.atZone(ktm));
		System.out.println("Date zonée Ici  : " +dateHeure.atZone(ici));

		ZonedDateTime aKtm = maintenant.atZone(ktm);
		System.out.println("Maintenant à Katmandou : " +aKtm);
		System.out.println("Un instant à Katmandou : " +aKtm.toInstant());		
		System.out.println();

		ZoneId bsas = ZoneId.of("America/Buenos_Aires");
		
		dateHeure = LocalDateTime.ofInstant(maintenant, bsas);
		System.out.println("Date locale à BsAs: " +dateHeure);
		System.out.println("Date zonée à BsAs : " +dateHeure.atZone(bsas));
		System.out.println("Date zonée Ici  : " +dateHeure.atZone(ici));

		ZonedDateTime aBsAs = maintenant.atZone(bsas);
		System.out.println("Maintenant à BsAs : " +aBsAs);
		System.out.println("Un instant à BsAs : " +aBsAs.toInstant());		
	}

	@Test
	public void testLocalDate() {
		LocalDate aujourdhui = LocalDate.now();
		System.out.println("Aujourdhui:" +aujourdhui);
		LocalDateTime minuit = aujourdhui.atStartOfDay();
		System.out.println("Minuit:" +minuit);
		ZonedDateTime aParis = minuit.atZone(ZoneId.systemDefault());
		System.out.println("A Paris:" +aParis);
		System.out.println("Un instant à Paris:" +aParis.toInstant());
		ZonedDateTime aKtm = minuit.atZone(ZoneId.of("Asia/Katmandu"));
		System.out.println("A Kathmandou:" +aKtm);
		System.out.println("Un instant à Kathmandou:" +aKtm.toInstant());		
	}

	@Test
	public void testDate() {
		Date now = new Date();
		System.out.println("Date:" +now);
		System.out.println("Date:" +now.toInstant());		
	}
	
	@Test
	public void testChrono() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Japon: " +JapaneseDate.from(now));
		System.out.println("Hijra: " +HijrahDate.from(now));
		System.out.println("Minguo: " +MinguoDate.from(now));
		System.out.println("ThaiBud: " +ThaiBuddhistDate.from(now));
	}	
	
	@Test
	public void testVoyage() {
		DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.FRENCH);

		LocalDateTime leaving = LocalDateTime.of(2015, Month.JULY, 01, 19, 42);
		ZoneId fuseauDépart = ZoneId.systemDefault(); 
		ZonedDateTime départ = ZonedDateTime.of(leaving, fuseauDépart);

	    String strDépart = départ.format(format);
	    System.out.printf("Départ d'ici:  %s (%s)%n", strDépart, fuseauDépart);

		// Le vol dure 14 heures et 20 minutes, soit 860 minutes
	    ZonedDateTime arrivéeIci = départ.plusMinutes(860);

	    ZoneId fuseauArrivée = ZoneId.of("Asia/Katmandu"); 
		ZonedDateTime arrivéeHeureLocale = arrivéeIci.withZoneSameInstant(fuseauArrivée);

	    String strArrivée = arrivéeHeureLocale.format(format);
	    System.out.printf("Arrivée heure locale: %s (%s)%n", strArrivée, fuseauArrivée);
	    strArrivée = arrivéeIci.format(format);
	    System.out.printf("Arrivée heure d'ici: %s (%s)%n", strArrivée, fuseauDépart);
	    
	    Duration durée = Duration.between(départ, arrivéeHeureLocale);
	    Duration durée2 = Duration.between(départ, arrivéeIci);
	    System.out.printf("Durée du vol   : %s %s %n", durée, durée2);	    
	}
}
