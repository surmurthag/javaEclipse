package lang;

import org.junit.Test;

public class NombresTest {

	@Test
	public void test() {
		
		int binaire = 0b01000111001010101;
		int hexadecimal = 0xCAFEBABE;
				
		long treslongnombre = 123456789;
		long tres_long_nombre = 123_456_789;

		long avec_un_l_pour_long = 123_456_789_876543210l;

		double avec_un_d_pour_double = 123_456_789_876543210d;
	}
}
