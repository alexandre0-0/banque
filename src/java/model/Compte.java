package model;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Database;

public class Compte {

	private int noCompte, noClient;
	private double solde;

	/**
	 * Crée en mémoir un compte Si noCompte vaut 0, le compte n'est pas en base de données.
	 *
	 * @param noCompte
	 * @param solde
	 * @param noClient
	 */
	public Compte(int noCompte, double solde, int noClient) {
		this.noCompte = noCompte;
		this.noClient = noClient;
		this.solde = solde;
	}

	public static List<Compte> getComptes(int noClient) throws SQLException {
		ArrayList<Compte> listCompte = new ArrayList();
		Compte result = null;

		Connection connexion = Database.getConnection();
		String sql = "SELECT * FROM compte WHERE no_client =? ";
		PreparedStatement ordre = connexion.prepareStatement(sql);
		ordre.setInt(1, noClient);

		ResultSet rs = ordre.executeQuery();
		while(rs.next()) {
			result = new Compte(rs.getInt("no_compte"),
					  rs.getDouble("solde"),
					  rs.getInt("no_client"));
			listCompte.add(result);
		}

		rs.close();
		ordre.close();
		connexion.close();
		return listCompte;
	}

    Compte(int aInt, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public int getNoCompte() {
		return noCompte;
	}

	public int getNoClient() {
		return noClient;
	}

	public double getSolde() {
		return solde;
	}

	/**
	 * requiert un numeros de compte > 0
	 *
	 * @param noCompte
	 */
	public void setNoCompte(int noCompte) {
		assert noCompte > 0;
		this.noCompte = noCompte;
	}

	/**
	 * requiert un numeros de client > 0
	 *
	 * @param noClient
	 */
	public void setNoClient(int noClient) {
		assert noClient > 0;
		this.noClient = noClient;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public void update(int nouveauNCompte, double nouveauSolde, int nouveauNClient) throws SQLException, InstantiationException {

		Connection connexion = Database.getConnection();
		String sql = "UPDATE compte SET solde =?, no_client=? WHERE no_compte =? ";
		PreparedStatement ordre = connexion.prepareStatement(sql);
		ordre.setDouble(1, nouveauSolde);
		ordre.setInt(2, nouveauNClient);
		ordre.setInt(3, nouveauNCompte);
		ordre.executeUpdate();
		ordre.close();
		connexion.close();
	}

	/**
	 * Normalement requiert de ne pas mettre en dessous du decouvert autorisé, et de ne pas faire dépasser le plafond de l'autre. Requiert aussi Compte.getById(noAutreCompte) != null.
	 *
	 * @param noAutreCompte
	 * @param montant
	 */
	public void virerSur(int noAutreCompte, double montant) throws SQLException {

		Connection connection = Database.getConnection();
		// debuter une transaction
		connection.setAutoCommit(false);

		// 1er Ordre
		String sql = "UPDATE compte SET solde= solde-? WHERE no_compte=?";
		PreparedStatement retrait = connection.prepareStatement(sql);
		retrait.setDouble(1, montant);
		retrait.setInt(2, noCompte);
		int nbRetrait = retrait.executeUpdate();
		if (nbRetrait == 0) {
			connection.rollback();
			throw new SQLException("COMPTE" + noCompte + "inexistant");
		} else {
			solde -= montant;
		}

		//2eme Ordre
		sql = "UPDATE compte SET solde= solde+? WHERE no_compte=?";
		PreparedStatement depot = connection.prepareStatement(sql);
		depot.setDouble(1, montant);
		depot.setInt(2, noAutreCompte);
		int nbDepot = depot.executeUpdate();
		if (nbDepot == 0) {
			connection.rollback();
			throw new SQLException("COMPTE" + noAutreCompte + "inexistant");
		} else //Valider
		{
			connection.commit();
		}

		//Fermeture de la connexion
		retrait.close();
		depot.close();
		connection.close();
	}

	public void inserer() throws SQLException {
		//Connexion
		Connection connection = Database.getConnection();
		try {
			//Debut de la transaction
			connection.setAutoCommit(false);

			//Preparation de la requete 1
			String sql = "INSERT INTO compte (solde , no_client) VALUES (?,?);";
			PreparedStatement creation = connection.prepareStatement(sql);
			creation.setDouble(1, solde);
			creation.setInt(2, noClient);
			creation.executeUpdate();

			//Preparation de la requete 2
			sql = "SELECT MAX(no_compte) AS no_compte FROM compte;";
			PreparedStatement dernier = connection.prepareStatement(sql);
			ResultSet rs = dernier.executeQuery();
			assert rs.next();
			noCompte = rs.getInt("no_compte");

			//Valider transaction
			connection.commit();

			//Fermeture connexion
			creation.close();
			dernier.close();
		} catch (SQLException exc) {
			connection.rollback();
			throw exc;
		} finally {
			connection.close();
		}
	}

	public void inserer2() throws SQLException {
		Connection connection = Database.getConnection();
		String sql = "INSERT INTO compte(solde, no_client) VALUES(?, ?)";
		PreparedStatement reqInserer = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		reqInserer.setDouble(1, solde);
		reqInserer.setInt(2, noClient);
		reqInserer.executeUpdate();

		//Récupération de la dernière clé
		ResultSet keys = reqInserer.getGeneratedKeys();
		if (keys.next()) {
			noCompte = keys.getInt(1);
		}

		reqInserer.close();
		connection.close();
	}

	public static Compte getById(int unNoCompte) throws SQLException, InstantiationException {
		Compte result = null;

		Connection connexion = Database.getConnection();
		Statement ordre = connexion.createStatement();
		String sql = "SELECT * FROM compte WHERE no_compte =" + unNoCompte;
		ResultSet rs = ordre.executeQuery(sql);
		if (rs.next()) {
			result = new Compte(rs.getInt("no_compte"),
					  rs.getDouble("solde"),
					  rs.getInt("no_client"));
			rs.close();
			ordre.close();
			connexion.close();
		}
		return result;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + this.noCompte;
		hash = 97 * hash + this.noClient;
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.solde) ^ (Double.doubleToLongBits(this.solde) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Compte other = (Compte) obj;
		if (this.noCompte != other.noCompte) {
			return false;
		}
		if (this.noClient != other.noClient) {
			return false;
		}
		if (Double.doubleToLongBits(this.solde) != Double.doubleToLongBits(other.solde)) {
			return false;
		}
		return true;
	}

    @Override
    public String toString() {
        return "Compte{" + "noCompte=" + noCompte + ", noClient=" + noClient + ", solde=" + solde + '}';
    }

}
