package org.Tp1;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.Tp1.entities.Membre;
import org.junit.Test;

public class JUnitRechercheMembre {

	@Test
	public void test() {
		ApplicationTP1 test = new ApplicationTP1();
		try {
			List<Membre> retour = test.rechercherMembres();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
