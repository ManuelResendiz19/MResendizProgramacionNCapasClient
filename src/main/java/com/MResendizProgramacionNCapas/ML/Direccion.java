
package com.MResendizProgramacionNCapas.ML;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Direccion {
    
    @JsonProperty("idDireccion")
    private int IdDireccion;
    
    @JsonProperty("calle")
    private String Calle;
    
    @JsonProperty("numeroInterior")
    private String NumeroInterior;
    
    @JsonProperty("numeroExterior")
    private String NumeroExterior;
    
    public Colonia Colonia;
    
    
    public Direccion() {
    }
    
    public Direccion( String Calle, String NumeroInterior, String NumeroExterior){
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
    }
    
    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }
    
    public int getIdDireccion (){
        return IdDireccion;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }
    
    
    public String getCalle() {
        return Calle;
    }

    
    public void setNumeroInterior(String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    
    
    public String getNumeroInterior (){
        return NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
    
    
            
}
