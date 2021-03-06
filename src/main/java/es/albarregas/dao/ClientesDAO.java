/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.dao;

import es.albarregas.beans.Clientes;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Oscar
 */
public class ClientesDAO implements IClientesDAO{
    Clientes cliente;
    String consulta;
    Statement sentencia;

    @Override
    public void inicializarClientes(int idUsuario) {
        consulta = "insert into Clientes(IdCliente,Nombre,Apellidos,NIF,FechaAlta) values("+idUsuario+",'','','',now())";
        try {
            sentencia = ConnectionFactory.getConnection().createStatement();
            sentencia.executeUpdate(consulta);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void closeConnection() {
        ConnectionFactory.closeConnection();
    }

    @Override
    public Clientes getOne(String where) {
        try {
            consulta = "Select nombre,apellidos,nif,FechaNacimiento,FechaAlta from Clientes " + where;
            sentencia = ConnectionFactory.getConnection().createStatement();
            ResultSet resultado = sentencia.executeQuery(consulta);
            while (resultado.next()) {
                cliente = new Clientes();
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidos(resultado.getString("apellidos"));
                cliente.setNif(resultado.getString("nif"));
                cliente.setFechaNacimiento(resultado.getDate("FechaNacimiento"));
                cliente.setFechaAlta(resultado.getDate("FechaAlta"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return cliente;
    }

    @Override
    public void updateClientes(Clientes cliente) {
         try {
             System.out.println("Entro al dao");
             System.out.println(cliente.getNombre());
             System.out.println(cliente.getApellidos());
             System.out.println(cliente.getNif());
             System.out.println(cliente.getFechaNacimiento());
             System.out.println(cliente.getIdCliente());
            String sql = "update Clientes set nombre=?,apellidos=?,nif=?,fechaNacimiento=? where idCliente=?";
            PreparedStatement preparada = ConnectionFactory.getConnection().prepareStatement(sql);
            preparada.setString(1, cliente.getNombre());
            preparada.setString(2, cliente.getApellidos());
            preparada.setString(3, cliente.getNif());
            preparada.setDate(4, (Date) cliente.getFechaNacimiento());
            preparada.setInt(5, cliente.getIdCliente());
            preparada.executeUpdate();
        } catch (SQLException ex) {
          ex.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }
    
    }
    

