
package com.MResendizProgramacionNCapas.ML;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Rol {
    
    @JsonProperty("idRols")
    private int IdRols;
    
    @JsonProperty("nombreRol")
    private String NombreRol;

    public int getIdRols() {
        return IdRols;
    }

    public void setIdRols(int IdRols) {
        this.IdRols = IdRols;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String NombreRol) {
        this.NombreRol = NombreRol;
    }
  
}
