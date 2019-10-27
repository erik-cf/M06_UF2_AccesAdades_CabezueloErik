package forHonor;

/*
 * Clase que define a los personajes del juego For Honor
 */
public class Personaje {
	/*
	 * Atributos de la clase
	 */
	private int id;
	private String nombre;
	private int ataque;
	private int defensa;
	private int faccion_id;
	
	/*
	 * Constructor vacio
	 */
	public Personaje() {
		
	}
	
	/*
	 * Constructor donde se rellena todos los atributos
	 */
	public Personaje(int id, String nombre, int ataque, int defensa, int faccion_id) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ataque = ataque;
		this.defensa = defensa;
		this.faccion_id = faccion_id;
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

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getFaccion_id() {
		return faccion_id;
	}

	public void setFaccion_id(int faccion_id) {
		this.faccion_id = faccion_id;
	}

	/*
	 * Metodo toString()
	 */
	public String toString() {
		return "Personaje con id " + id + " y nombre " + nombre + ". Atributos: \n\tataque: " + ataque + "\n\tdefensa: " + defensa
				+ "\n\tPertenece a la faccion con id: " + faccion_id;
	}
	
	
	
}
