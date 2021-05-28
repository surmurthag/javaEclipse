package dialogue.stats;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Stat {
	private final Long compteur;
	
	private final String mois;
	
	public Stat(Object obj) {
		this( (Integer) ((Object[]) obj)[0], (Long) ((Object[]) obj)[1]);
	}

	private Stat(Integer mois, Long nbr) {
		this(Month.of(mois), nbr);
	}

	public Stat(Month mois, Number nbr) {
		this.mois = mois.getDisplayName(TextStyle.FULL, Locale.getDefault());
		compteur = nbr.longValue();
	}

	public Long getCompteur() {
		return compteur;
	}
	
	public String getMois() {
		return mois;
	}
}