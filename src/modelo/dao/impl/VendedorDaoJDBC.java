package modelo.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insere(Vendedor vendedor) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO vendedor " + "(Nome, Email, Data_Nasc, Salario, IdDepartamento) "
					+ "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vendedor.getNome());
			ps.setString(2, vendedor.getEmail());
			ps.setDate(3, new Date(vendedor.getDataNasc().getTime()));
			ps.setDouble(4, vendedor.getSalario());
			ps.setInt(5, vendedor.getDepartamento().getId());
			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vendedor.setId(id);
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
	public void atualiza(Vendedor vendedor) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE vendedor "
					+ "SET Nome = ?, Email = ?, Data_Nasc = ?, Salario = ?, IdDepartamento = ? " + "WHERE Id = ?");
			ps.setString(1, vendedor.getNome());
			ps.setString(2, vendedor.getEmail());
			ps.setDate(3, new Date(vendedor.getDataNasc().getTime()));
			ps.setDouble(4, vendedor.getSalario());
			ps.setInt(5, vendedor.getDepartamento().getId());
			ps.setInt(6, vendedor.getId());
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
			ps = conn.prepareStatement("DELETE FROM vendedor WHERE Id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaStatement(ps);
		}

	}

	@Override
	public Vendedor buscaId(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT vendedor.*, departamento.Nome AS NomeDep " + "FROM vendedor INNER JOIN departamento "
							+ "ON vendedor.IdDepartamento = departamento.Id " + "WHERE vendedor.Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Departamento dep = instanciaDepartamento(rs);
				Vendedor vend = instanciaVendedor(rs, dep);
				return vend;

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
	public List<Vendedor> buscaIdDepartamento(Departamento departamento) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT vendedor.*, departamento.Nome AS NomeDep "
					+ "FROM vendedor INNER JOIN departamento " + "ON vendedor.IdDepartamento = departamento.Id "
					+ "WHERE IdDepartamento = ? " + "ORDER BY Nome");
			ps.setInt(1, departamento.getId());
			rs = ps.executeQuery();
			List<Vendedor> listaVendedor = new ArrayList<>();
			Map<Integer, Departamento> mapa = new HashMap<>();

			while (rs.next()) {
				Departamento dep = mapa.get(rs.getInt("IdDepartamento"));
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					mapa.put(rs.getInt("IdDepartamento"), dep);
				}
				Vendedor vend = instanciaVendedor(rs, dep);
				listaVendedor.add(vend);

			}
			return listaVendedor;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(ps);
		}
	}

	@Override
	public List<Vendedor> buscaTodos() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT vendedor.*, departamento.Nome AS NomeDep " + "FROM vendedor INNER JOIN departamento "
							+ "ON vendedor.IdDepartamento = departamento.Id " + "ORDER BY Id");

			rs = ps.executeQuery();
			List<Vendedor> listaVendedor = new ArrayList<>();
			Map<Integer, Departamento> mapa = new HashMap<>();

			while (rs.next()) {
				Departamento dep = mapa.get(rs.getInt("IdDepartamento"));
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					mapa.put(rs.getInt("IdDepartamento"), dep);
				}
				Vendedor vend = instanciaVendedor(rs, dep);
				listaVendedor.add(vend);

			}
			return listaVendedor;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(ps);
		}
	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("IdDepartamento"));
		dep.setNome(rs.getString("NomeDep"));
		return dep;
	}

	private Vendedor instanciaVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("Id"));
		vend.setNome(rs.getString("Nome"));
		vend.setEmail(rs.getString("Email"));
		vend.setDataNasc(rs.getDate("Data_Nasc"));
		vend.setSalario(rs.getDouble("Salario"));
		vend.setDepartamento(dep);
		return vend;
	}

}
