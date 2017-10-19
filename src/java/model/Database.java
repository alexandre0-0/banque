package model;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 * ReprÃ©sente la base de donnÃ©es videos. Fournit une connexion Ã cette base (via <code>getConnection()</code>.
 *
 * @author plasse
 */
public class Database {

	/**
	 * Code erreur MySQL quand le serveur est inaccessible
	 */
	public static final int SERVER_OFF = 0;

	/**
	 * Code erreur MySQL pour "duplicate entry" (doublons)
	 */
	public static final int DOUBLON = 1062;

	/**
	 * Code erreur MySQL pour "Cannot delete or update a parent row: a foreign key constraint fails "
	 */
	public static final int ROW_IS_REFERENCED = 1451;

	/**
	 * Code erreur MySQL pour "Cannot add or update a child row: a foreign key constraint fails"
	 */
	public static final int FOREIGN_KEY_NOT_FOUND = 1452;

	/**
	 * Pilote MySQL (bibliothÃ¨que contenant les implÃ©mentations de jdbc)
	 */
	protected static final String DRIVER_NAME
			  = "com.mysql.jdbc.Driver";
//   protected static final String ORACLE_DRIVER_NAME
//           = "oracle.jdbc.driver.OracleDriver";
//   protected static final String DERBY_DRIVER_NAME
//           = "org.apache.derby.jdbc.ClientDriver";
	protected static final String DB_NAME = "banque";
	protected static final String USER = "root";
	protected static final String PASSWORD = "";

	/**
	 * Chaine de connexion (adresse TCP/IP de la base de donnÃ©es
	 */
	protected static String URL = "jdbc:mysql://localhost/" + DB_NAME;
	// La chaine de connexion diffÃ¨re d'un SGBD Ã  l'autre.
	// Pour Oracle : "jdbc:oracle:oci8:@localhost:1521:XE/" + DB_NAME
	// Pour Derby (BD en mÃ©moire en Java) : "jdbc:derby://localhost:1527/" + DB_NAME
	// Pour MySQL : "jdbc:mysql://localhost/" + DB_NAME;

	// Bloc d'initialisation pour les variables static, ne s'exÃ©cute qu'une fois
	static {
		try {
			Class.forName(DRIVER_NAME).newInstance();
			System.out.printf("*** Driver %s loaded.\n", DRIVER_NAME);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException exc) {
			// Depuis java 1.7, on peut rassembler ainsi les exceptions
			exc.printStackTrace();
			throw new RuntimeException("Pilote pas chargÃ©");
		}
	}

	public enum SortOrder {
		ASC, DESC;
	}

	/**
	 * Fournit une connexion Ã la base de donnÃ©es. Ne fait pas appel Ã un pool de connexion, mÃªme si cela est envisageable plus tard (ne changerait rien Ã l'appel de la mÃ©thode)
	 * <br><strong>Requiert</strong> que le pilote soir chargÃ©
	 *
	 * @throws java.sql.SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * ExÃ©cuter un fichier SQL sur la base de donnÃ©es. Les instructions SQL y sont toutes terminÃ©es par un point virgule. Les lignes vides et les espaces aprÃ¨s les points virgules sont ignorÃ©s.
	 *
	 * @param path chemin du fichier SQL
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void loadSQL(String path) throws SQLException, IOException {
		// Analyseur de fichier
		Scanner scanner = null;
		Connection connexion = null;
		Statement ordre = null;
		try {
			connexion = getConnection();
			ordre = connexion.createStatement();
			scanner = new Scanner(new File(path));
			// Le delimiteur de blocs sera le point virgule suivi de 0 a n espaces
			scanner.useDelimiter(";\\s*");
			// On boucle sur chaque bloc detectÃ©
			while (scanner.hasNext()) {
				// Recuperer l'instruction SQL
				String sql = scanner.next();
				System.out.println(sql);
				ordre.executeUpdate(sql);
			}
		} catch (SQLException exc) {
			throw exc;
		} catch (IOException exc) {
			throw exc;
		} finally {
			if (ordre != null) {
				ordre.close();
			}
			if (connexion != null) {
				connexion.close();
			}
			if (scanner != null) {
				scanner.close();
			}
		}
	}
}
