package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Commercial;
import static model.Compte.getComptes;

@WebServlet(name = "ClientsServlet", urlPatterns = {"/clients"})
public class ClientsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sNoCommercial = request.getParameter("noCommercial");
        String msg;
        try {
            // Le commercial en dur, a prendre ensuite dans la session
            Commercial commercial = new Commercial(2, "Lampion");
            request.setAttribute("clients", commercial.getClients());
        } catch (SQLException | NumberFormatException ex) {
            request.setAttribute("msg", "erreur");
        }
        request.getRequestDispatcher("WEB-INF/clients.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
