/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexa
 */
public class CommercialTest {
    
    public CommercialTest() {
    }
    
    
    @Test
    public void testGetClients() throws Exception {
        System.out.println("getClients");
        Commercial instance = new Commercial(1, "Lampion");
        List<Client> expected = new ArrayList<>();
        expected.add(new Client(2, "Tintin", "tintin@herge.be", null));
        expected.add(new Client(3, "Haddock", "haddock@moulinsart.fr",
                "Grand amateur de Loch Lhomond"));
        List<Client> result = instance.getClients();
        assertEquals(expected, result);
    }
}
