package entite.crud;

public class EniException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EniException() {
		super();
	}

	public EniException(String message, Throwable cause) {
		super(message, cause);
	}

	public EniException(String message) {
		super(message);
	}

	public EniException(Throwable cause) {
		super(cause);
	}
}
