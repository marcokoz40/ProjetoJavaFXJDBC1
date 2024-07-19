package modelo.dao;

import java.util.List;

import modelo.entidades.Departamento;

public interface DepartamentoDao {
	
	void insere(Departamento dep);
	void atualiza(Departamento dep);
	void deletaId(Integer id);
	Departamento buscaId(Integer id);
	List<Departamento> buscaTodos();

}
