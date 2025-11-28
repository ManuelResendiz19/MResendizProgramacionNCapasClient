
package com.MResendizProgramacionNCapas.ML;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Colonia {
    
    @JsonProperty("idColonia")
    private int IdColonia;
    
    @JsonProperty("nombre")
    private String Nombre;
    
    @JsonProperty("codigoPostal")
    private String CodigoPostal;
    
    public Municipio Municipio;
    
    
    public Colonia(){
        
    }
    
    public Colonia(int IdColonia, String Nombre, String CodigoPostal){
        this.IdColonia = IdColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        
    }
    
    public void setIdColonia(int IdColonia){
        this.IdColonia = IdColonia;
    }
    
    public int getIdColonia(){
        return IdColonia;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    
    public String getNombre(){
        return Nombre;
    }
    
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }
    
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
    
    
}
