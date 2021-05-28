package controle.utilitaires;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TestDate {
	
	@Id
	@GeneratedValue
	private long clePrimaire;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horodatage;
	
	private String fuseau;

	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Temporal(TemporalType.TIME)
	private Date heure;

	
	@Override
	public String toString() {
		return "TestDate [clePrimaire=" + this.clePrimaire + ", horodatage="
				+ this.horodatage + ", date=" + this.date + ", heure="
				+ this.heure + "]";
	}

	public long getClePrimaire() {
		return this.clePrimaire;
	}

	public Date getHorodatage() {
		return this.horodatage;
	}

	public TestDate setHorodatage(Date horodatage) {
		this.horodatage = horodatage;
		this.fuseau = TimeZone.getDefault().getID();
		return this;
	}

	public String getFuseau() {
		return this.fuseau;
	}
	
	public Date getDate() {
		return this.date;
	}

	public TestDate setDate(Date date) {
		this.date = date;
		return this;
	}

	public Date getHeure() {
		return this.heure;
	}

	public TestDate setHeure(Date heure) {
		this.heure = heure;
		return this;
	}
}
