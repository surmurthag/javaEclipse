package controle.utilitaires;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GestionDates {

	private static final String MATRICE_FR = "dd/MM/yyyy";

	private static final String TEMPS_FR = "dd/MM/yyyy HH:mm";

	private static final ZoneId UTC = ZoneId.of("UTC");

	public static String dateEnChaineFR(Instant dtDate) {
		return dateEnChaine(dtDate, Locale.FRANCE, MATRICE_FR);
	}

	private static String dateEnChaine(Instant laDate, Locale locale, String matrice) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(matrice, locale);
		ZoneId fuseau = ZoneId.systemDefault();
		ZonedDateTime dateSurFuseau = laDate.atZone(fuseau);
		String date = format.format(dateSurFuseau);
		return date;
	}

	public static String tempsEnChaineFR(Instant dtDate) {
		return dateEnChaine(dtDate, Locale.FRANCE, TEMPS_FR);
	}

	public static Instant chaineFRenDate(String laDateChaine)
			throws ParseException {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(MATRICE_FR);
		LocalDate date = LocalDate.parse(laDateChaine, format);
		LocalDateTime minuit = date.atStartOfDay();
		ZoneId fuseau = ZoneId.systemDefault();
		ZonedDateTime ici = minuit.atZone(fuseau);
		return ici.toInstant();
	}
	
	public static Instant instant(LocalDate date) {
		return instant(date.atStartOfDay());
	}

	public static Instant instant(LocalDateTime date) {
		return ZonedDateTime.of(date, UTC).toInstant();
	}

	public static LocalDate date(Instant instant) {
		return LocalDateTime.ofInstant(instant, UTC).toLocalDate();
	}

	public static LocalDateTime maintenant() {
		return LocalDateTime.now(UTC);
	}
}