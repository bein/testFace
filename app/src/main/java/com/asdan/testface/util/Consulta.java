package com.asdan.testface.util;

/**
 * Consulta.java
 *
 * Enum en el cual hago el modelo de base de datos de la aplicación.
 *
 * @author alberto.quirino
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