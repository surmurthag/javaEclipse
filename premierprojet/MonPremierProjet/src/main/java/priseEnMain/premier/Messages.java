package priseEnMain.premier;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.stream.Stream;

public class Messages {
	private static final String BUNDLE_NAME = "priseEnMain.premier.messages"; //$NON-NLS-1$

	private static final Map<Locale, ResourceBundle> BUNDLES = new HashMap<>();

	private static Control control = new Control() {
		
		public Locale getFallbackLocale(String baseName, Locale locale) {
			return Locale.ROOT;
		}
	};

	static {
		charge(Locale.FRENCH);
		charge(Locale.ENGLISH);
	}

	private static void charge(Locale locale) {
		BUNDLES.put(locale, ResourceBundle.getBundle(BUNDLE_NAME, locale));
	}

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return getString(key, Locale.getDefault());
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Locale locale) {
		
		ResourceBundle bundle = BUNDLES.get(locale);
		Optional<ResourceBundle> opt = Optional.ofNullable(bundle);
		bundle = opt.orElse(BUNDLES.get(Locale.FRENCH));
		Stream<ResourceBundle> flux = Stream.of(bundle);
		Stream<String> trad = flux.map(rsc -> rsc.getString(key));
		return trad.findFirst().get();
	}
	
	public static String getString4(String key, Locale locale) {
		return Stream.of(BUNDLES)
				.map(bundles -> bundles.get(locale))
				.map(Optional::ofNullable)
				.map(o -> o.orElse(BUNDLES.get(Locale.FRENCH)))
				.map(rsc -> rsc.getString(key))
				.findFirst()
				.orElse("?" + key + "?");
	}

	public static String getString3(String key, Locale locale) {
		return Stream.of(BUNDLES)
				.map(bundles -> Optional.ofNullable(bundles.get(locale)))
				.map(o -> o.orElse(BUNDLES.get(Locale.FRENCH)))
				.map(rsc -> rsc.getString(key))
				.findFirst()
				.orElse("?" + key + "?");
	}

	public static String getString2(String key, Locale locale) {
		return Stream.of(BUNDLES)
				.map(bundles -> bundles.get(locale))
				.map(rsc -> rsc.getString(key))
				.findFirst()
				.orElse("?" + key + "?");
	}
}
