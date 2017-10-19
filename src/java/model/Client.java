package model;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import model.Database;

public class Client {

    private int noClient;
    private String nom, email, commentaire;

    public Client(int noClient, String nom, String email, String commentaire) {
        this.noClient = noClient;
        this.nom = nom;
        this.email = email;
        this.commentaire = commentaire;
    }

    /**
     * Client de n° noClient, ou null s'il n'existe pas en base.
     * @param noClient
     * @return
     * @throws SQLException
     */
    public static Client getClient(int noClient) throws SQLException {
        Client result = null;

        Connection connexion = Database.getConnection();
        String sql = "SELECT * FROM client WHERE no_client =? ";
        PreparedStatement ordre = connexion.prepareStatement(sql);
        ordre.setInt(1, noClient);

        ResultSet rs = ordre.executeQuery();
        if (rs.next()) {
            result = new Client(rs.getInt("no_client"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("commentaire"));
        }

        rs.close();
        ordre.close();
        connexion.close();
        return result;
    }

    public int getNoClient() {
        return noClient;
    }

    public void setNoClient(int noClient) {
        this.noClient = noClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Client{" + "noClient=" + noClient + ", nom=" + nom + ", email=" + email + ", commentaire=" + commentaire + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.noClient;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.commentaire);
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
        final Client other = (Client) obj;
        if (this.noClient != other.noClient) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.commentaire, other.commentaire)) {
            return false;
        }
        return true;
    }
    
}
