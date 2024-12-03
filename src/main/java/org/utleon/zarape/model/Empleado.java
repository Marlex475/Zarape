package org.utleon.zarape.model;

public class Empleado {

    private int idEmpleado;
    private int idSucursal;
    private int idPersona;
    private int idUsuario;
    private int activo;
    private Persona persona;
    private Usuario usuario;

    // Constructor
    public Empleado(int idEmpleado, int idSucursal, int idPersona, int idUsuario, int activo, Persona persona, Usuario usuario) {
        this.idEmpleado = idEmpleado;
        this.idSucursal = idSucursal;
        this.idPersona = idPersona;
        this.idUsuario = idUsuario;
        this.activo = activo;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Empleado() {

    }

    // Getters and Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", idSucursal=" + idSucursal + ", idPersona=" + idPersona + ", idUsuario=" + idUsuario + ", activo=" + activo + ", persona=" + persona + ", usuario=" + usuario + '}';
    }

}
