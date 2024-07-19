package modelo.dao;

import java.util.List;

import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public interface VendedorDao {
	
	void insere(Vendedor vend);
	void atualiza(Vendedor vend);
	void deletaId(Integer id);
	Vendedor buscaId(Integer id);
	List<Vendedor> buscaTodos();
	List<Vendedor> buscaIdDepartamento(Departamento dep);

}
