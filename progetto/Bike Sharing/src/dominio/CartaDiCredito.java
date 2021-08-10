package dominio;
import java.time.LocalDate;

public class CartaDiCredito {
	private String numero;
	private String scadenza;
	private String cvv;
	
	public CartaDiCredito(String numero, String scadenza, String cvv) throws NullPointerException, IllegalArgumentException {
		if (numero == null) throw new NullPointerException("Il numero della carta di credito non pu� essere null.");
		if (scadenza == null) throw new NullPointerException("La scadenza della carta di credito non pu� essere null.");
		if (cvv == null) throw new NullPointerException("Il cvv della carta di credito non pu� essere null.");
		if (scadenza.split("/", 2).length != 2) throw new IllegalArgumentException("Il formato della data di scadenza inserita non � corretto.");
		if (!CartaDiCredito.controllaValidit�Numero(numero)) throw new IllegalArgumentException("Il numero inserito non � valido.");
		if (!CartaDiCredito.controllaValidit�Scadenza(scadenza)) throw new IllegalArgumentException("La data di scadenza inserita � precedente alla data attuale.");
		if (!CartaDiCredito.controllaValidit�Cvv(cvv)) throw new IllegalArgumentException("Il cvv deve essere un numero di 3 cifre.");
		this.numero = numero;
		this.scadenza = scadenza;
		this.cvv = cvv;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public String getScadenza() {
		return this.scadenza;
	}
	
	public String getCvv() {
		return this.cvv;
	}
	
	static public boolean controllaValidit�Numero(String numero) {
		int[] ints = new int[numero.length()];
		for (int i = 0; i < numero.length(); i++) {
			ints[i] = Integer.parseInt(numero.substring(i, i + 1));
		}
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}
		int sum = 0;
		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}
		if (sum % 10 == 0) return true;
		else return false;
	}
	
	static public boolean controllaValidit�Scadenza(String scadenza) {
		String s[] = scadenza.split("/", 2);
		int month = Integer.parseInt(s[0]);
		int year = Integer.parseInt(s[1]);
		LocalDate date = LocalDate.of(year, month, 01);
		
		LocalDate currentDate = LocalDate.now();
		if (date.isBefore(currentDate)) return false;
		return true;
	}
	
	static public boolean controllaValidit�Cvv(String cvv) {
		if (cvv.length() == 3) return true;
		return false;
	}
	
	/* La carta di credito deve essere valida fino allo scadere dell'abbonamento. */
	/*
	static public boolean controllaScadenzaAbbonamento(TipoAbbonamento, String scadenzaCarta) {
		String s[] = scadenzaCarta.split("/", 2);
		int month = Integer.parseInt(s[0]);
		int year = Integer.parseInt(s[1]);
		LocalDate dataScadenzaCarta = LocalDate.of(year, month, 01);
		
		if (dataScadenzaCarta.isBefore(scadenzaAbbonamento)) return false;
		return true;
	}
	*/
	
	@Override
	public String toString() {
		return "Carta di credito - Numero: " + this.numero + ", Scadenza: " + this.scadenza + ", Cvv: " + this.cvv;
	}
}