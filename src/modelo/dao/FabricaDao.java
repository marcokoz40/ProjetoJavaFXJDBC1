package modelo.dao;

import db.DB;
import modelo.dao.impl.DepartamentoDaoJDBC;
import modelo.dao.impl.VendedorDaoJDBC;

public class FabricaDao {
	
	public static VendedorDao criaVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao criaDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}

}
