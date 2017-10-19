package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Compte;

@WebServlet(name = "CompteServlet", urlPatterns = {"/creerCompte"})
public class CreerCompteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/creerCompte.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {
		String sValeur = request.getParameter("valeur");
		String nClient = request.getParameter("noClient");
		boolean estOk = false;
		try {
			double valeur = Double.parseDouble(sValeur);
			if (valeur >= 200) {
				int noClient = Integer.parseInt(nClient);
				Compte compte = new Compte(0, valeur, noClient);
				compte.inserer2();
				request.setAttribute("msgSucces", "Compte créé pour le client 1 sous le n° " + compte.getNoCompte());
				estOk = true;
			} else {
				request.setAttribute("msgErreur", "Entrer un montant suffisant (>=200) ");
			}
		} catch (NumberFormatException exc) {
			request.setAttribute("msgErreur", "Entrer un nombre (ex : 5.2)");
		} catch (SQLException ex) {
			request.setAttribute("msgErreur", "Client n° " + nClient + "inexistant");
		}
		if (estOk) {
			response.sendRedirect("comptes?noClient="+nClient);
		} else {
			request.getRequestDispatcher("WEB-INF/creerCompte.jsp").forward(request, response);
		}
	}
}
