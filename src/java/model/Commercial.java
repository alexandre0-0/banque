/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author alexa
 */
public class Commercial {

    private int noCommercial;
    private String nom;

    public Commercial(int noCommercial, String nom) {
        this.noCommercial = noCommercial;
        this.nom = nom;

    }

    public List<Client> getClients() throws SQLException {
        ArrayList<Client> result = new ArrayList();

        Connection connexion = Database.getConnection();
        String sql = "SELECT\n"
                + "   c.no_client, nom, email, commentaire\n"
                + "FROM\n"
                + "   client c\n"
                + "       INNER JOIN\n"
                + "   portefeuille p    ON c.no_client = p.no_client\n"
                + "WHERE (p.no_client, date_attribution) IN\n"
                + "(\n"
                + "   SELECT no_client, MAX(date_attribution)\n"
                + "   FROM portefeuille\n"
                + "   GROUP BY no_client\n"
                + ")\n"
                + "AND p.no_commercial = ?; ";
        PreparedStatement ordre = connexion.prepareStatement(sql);
        ordre.setInt(1, noCommercial);

        ResultSet rs = ordre.executeQuery();
        while (rs.next()) {
            result.add(new Client(rs.getInt("no_client"),
                    rs.getString("nom"),
            rs.getString("email"),
            rs.getString("commentaire")));
        }

        rs.close();
        ordre.close();
        connexion.close();
        return result;
    }

    public int getNoCommercial() {
        return noCommercial;
    }

    public void setNoCommercial(int noCommercial) {
        this.noCommercial = noCommercial;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Client{" + "noclient=" + noCommercial + ", nom=" + nom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.noCommercial;
        hash = 47 * hash + Objects.hashCode(this.nom);
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
        final Commercial other = (Commercial) obj;
        if (this.noCommercial != other.noCommercial) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

}
