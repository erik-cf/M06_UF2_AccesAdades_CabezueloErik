package forHonor;

/*
 * Clase que define a las facciones del juego For Honor
 */
public class Faccion {
	
	/*
	 * Atributos de la clase
	 */
	private int id;
	private String nombre;
	private String lore;
	
	/*
	 * Constructor vacio
	 */
	public Faccion() {
		
	}

	/*
	 * Constructor donde se rellena todos los atributos
	 */
	public Faccion(int id, String nombre, String lore) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.lore = lore;
	}

	/*
	 * Getters y Setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	/*
	 * Metodo toString
	 */
	public String toString() {
		return "Faccion con id " + id + " y nombre " + nombre + ".\n Lore: " + lore;
	}
	
	
}
