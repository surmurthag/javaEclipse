package controle.utilitaires;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestDateConverted {
	
	@Id
	@GeneratedValue
	private long clePrimaire;
	
//	@Convert(converter=ConvertisseurLocalDateTime.class)
	private LocalDateTime horodatage;
	
	public long getClePrimaire() {
		return this.clePrimaire;
	}

	public LocalDateTime getHorodatage() {
		return this.horodatage;
	}

	public TestDateConverted setHorodatage(LocalDateTime horodatage) {
		this.horodatage = horodatage;
		return this;
	}

	@Override
	public String toString() {
		return "TestDateConverted [clePrimaire=" + this.clePrimaire
				+ ", horodatage=" + this.horodatage + "]";
	}
}
