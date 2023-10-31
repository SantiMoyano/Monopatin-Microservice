package poroto.po.monopatin.dtos;

import java.time.LocalTime;

public class ReporteMonopatinesDTO {
    
    private Long monopatin_id;
    private Integer kmtsUltimoService;
    private LocalTime tiempoDeUsoConPausa;

    public ReporteMonopatinesDTO() {}

    public Long getMonopatin_id() {
        return monopatin_id;
    }

    public void setMonopatin_id(Long monopatin_id) {
        this.monopatin_id = monopatin_id;
    }

    public Integer getKmtsUltimoService() {
        return kmtsUltimoService;
    }

    public void setKmtsUltimoService(Integer kmtsUltimoService) {
        this.kmtsUltimoService = kmtsUltimoService;
    }

    public LocalTime getTiempoDeUsoConPausa() {
        return tiempoDeUsoConPausa;
    }

    public void setTiempoDeUsoConPausa(LocalTime tiempoDeUsoConPausa) {
        this.tiempoDeUsoConPausa = tiempoDeUsoConPausa;
    }

    @Override
    public String toString() {
        return "ReporteMonopatinesDTO [monopatin_id=" + monopatin_id + ", kmtsUltimoService=" + kmtsUltimoService
                + ", tiempoDeUsoConPausa=" + tiempoDeUsoConPausa + "]";
    }
    
}
