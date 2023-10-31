package poroto.po.monopatin.entity;

public class Parada {
    private Long id;
    private Float latitud;
    private Float longitud;
    public Parada() {
    }
    public Parada(Long id, Float latitud, Float longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Float getLatitud() {
        return latitud;
    }
    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }
    public Float getLongitud() {
        return longitud;
    }
    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
    
    
}
