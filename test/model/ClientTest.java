/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static model.Compte.getComptes;

/**
 *
 * @author alexa
 */
public class ClientTest {

    @Test
    public void testGetClient() throws SQLException, InstantiationException {
        System.out.print("test ligne client");
        Client expected = new Client(1, "Dupont", "dupont@interpol.com", "Client distrait. Je dirai même plus ...");
        assertEquals(expected, Client.getClient(1));

    }
}
