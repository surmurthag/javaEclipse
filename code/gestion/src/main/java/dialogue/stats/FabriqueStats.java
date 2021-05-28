package dialogue.stats;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class FabriqueStats {

	public static List<Stat> createBeanCollection() {
		List<Stat> stats = new ArrayList<>();
		stats.add( new Stat(Month.JANUARY, 3));
		stats.add( new Stat(Month.FEBRUARY, 21));
		stats.add( new Stat(Month.MARCH, 142));
		return stats;
	}
}
