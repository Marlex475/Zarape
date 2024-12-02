package org.utleon.zarape.model;

public class Cliente {
    private int idCliente;
    private Persona persona;
    private byte activo;
    private Usuario user;

    public Cliente(){

    }

    public Cliente(int idCliente, Persona persona, Usuario User,byte activo) {
        this.idCliente = idCliente;
        this.persona = persona;
        this.activo = activo;
        this.user = User;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public byte getActivo() {
        return activo;
    }

    public void setActivo(byte activo) {
        this.activo = activo;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario User) {
        this.user = User;
    }


}

