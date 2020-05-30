package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Stores login data.
 * 
 * @author Martina
 *
 */
public class LoginData {

	/**
	 * User nick.
	 */
	private String nick;
	
	/**
	 * User password.
	 */
	private String password;
	
	/**
	 * Errors.
	 */
	Map<String, String> greske = new HashMap<>();
	
	public LoginData() {
	}
	
	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku. 
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}
	
	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.nick = pripremi(req.getParameter("nick"));
		this.password = pripremi(req.getParameter("password"));
	}
	
	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
	 * puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
	 */
	private String pripremi(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
	 * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
	 * registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();

		if(this.nick.isEmpty()) {
			greske.put("nick", "Ime je obavezno!");
		}
		
		if(this.password.isEmpty()) {
			greske.put("password", "Password je obvezan!");
		}
	}

	public void setError(String value, String message) {
		greske.put(value, message);
		
	}

}
