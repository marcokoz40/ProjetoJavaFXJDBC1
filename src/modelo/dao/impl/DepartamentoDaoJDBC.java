package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import modelo.dao.DepartamentoDao;
import modelo.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	private Connection conn;

	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insere(Departamento departamento) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO departamento " + "(Nome) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, departamento.getNome());

			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					departamento.setId(id);
				}
				DB.fechaResultSet(rs);
			} else {
				throw new DBException("Erro! Nenhuma linha foi afetada");
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaStatement(ps);
		}

	}

	@Override
	public void atualiza(Departamento departamento) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE departamento SET Nome = ? WHERE Id = ?");
			ps.setString(1, departamento.getNome());
			ps.setInt(2, departamento.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaStatement(ps);
		}

	}

	@Override
	public void deletaId(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM departamento WHERE Id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaStatement(ps);
		}

	}

	@Override
	public Departamento buscaId(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM departamento WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciaDepartamento(rs);
				return dep;

			}
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(ps);
		}
	}

	@Override
	public List<Departamento> buscaTodos() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM departamento ORDER BY Id");
			rs = ps.executeQuery();
			List<Departamento> listaDepartamento = new ArrayList<>();

			while (rs.next()) {
				Departamento dep = new Departamento();
				dep = instanciaDepartamento(rs);
				listaDepartamento.add(dep);
			}
			return listaDepartamento;

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(ps);
		}
	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("Nome"));
		return dep;
	}

}
