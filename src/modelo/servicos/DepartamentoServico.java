package modelo.servicos;

import java.util.List;

import modelo.dao.DepartamentoDao;
import modelo.dao.FabricaDao;
import modelo.entidades.Departamento;

public class DepartamentoServico {
	
	private DepartamentoDao depDao = FabricaDao.criaDepartamentoDao();
	
	public List<Departamento> buscaTodos() {
		return depDao.buscaTodos();
	}

}
