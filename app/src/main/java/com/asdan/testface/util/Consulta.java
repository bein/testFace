package com.asdan.testface.util;

/**
 * Created by alberto.quirino on 14/08/2015.
 */
public enum Consulta {

    CREA_TABLA_LOGIN(
            "CREATE TABLE [login] ([usuario] TEXT,[contrasena] TEXT) "),

    INSERTA_LOGIN(
            "INSERT INTO login (usuario,contrasena) " + "SELECT '','' ");

    private String consulta;

    private Consulta(String consulta) {
        this.consulta = consulta;
    }

    public String getConsulta() {
        return consulta;
    }

}