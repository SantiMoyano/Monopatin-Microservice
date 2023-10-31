package poroto.po.monopatin.response;

public class Distancia {
    private Long monopatin;
    private Long parada;
    private Float distancia;
    public Distancia() {
    }
    public Distancia(Long monopatin, Long parada, Float distancia) {
        this.monopatin = monopatin;
        this.parada = parada;
        this.distancia = distancia;
    }
    public Long getMonopatin() {
        return monopatin;
    }
    public void setMonopatin(Long monopatin) {
        this.monopatin = monopatin;
    }
    public Long getParada() {
        return parada;
    }
    public void setParada(Long parada) {
        this.parada = parada;
    }
    public Float getDistancia() {
        return distancia;
    }
    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }
   
    
}
