package poroto.po.monopatin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import poroto.po.monopatin.dtos.FinalizarViajeDTO;
import poroto.po.monopatin.dtos.ReporteMonopatinesDTO;
import poroto.po.monopatin.entity.Monopatin;
import poroto.po.monopatin.repsitory.MonopatinRepo;
import poroto.po.monopatin.response.Distancia;
import poroto.po.monopatin.servicio.CuentaServicio;
import poroto.po.monopatin.servicio.ParadaServicio;
import poroto.po.monopatin.servicio.TokenServicio;
import poroto.po.monopatin.servicio.ViajeServicio;

@RestController
@Tag(name = "Servicio Monopatin", description = "Encargado de todo lo referente al vehiculo monopatin")
@RequestMapping("/monopatin")
public class MonopatinController {
    @Autowired
    private MonopatinRepo monoRepo;

    @Autowired
    private ParadaServicio servicio;

    @Autowired
    private ParadaServicio paradaServicio;

    @Autowired
    private TokenServicio token;

    @GetMapping
    @Operation(summary = "Lista monopatines", description = "Da una lista detallada de todos los monopatines en formato JSON")

    public List<Monopatin> dameMonos(@RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        return monoRepo.findAll();
    }

    @PostMapping
    @Operation(summary = "Agregar un monopatin", description = "Se incorpora un monoptin especificado en un JSON")
    public Monopatin registrarMono(@RequestBody Monopatin m, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization).contains("ADMIN")) {
            return monoRepo.save(m);
        }
        return null;
    }

    @PutMapping("/encender/{idMono}")
    @Operation(summary = "Encender el Monopatin", description = "Se computa el consomo y se hace uso de la bateria")

    public String encender(
            @Parameter(description = "El Id del Monopatin a encender", example = "123") @PathVariable Long idMono,
            @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        Monopatin m = monoRepo.findById(idMono).get();
        if (m != null) {
            m.setEncendido(true);
            monoRepo.save(m);
            return "encendido Correctamente con la cuenta: ";
        } else
            return "Hubo un problema";

    }

    @PutMapping("/apagar/{id}")
    @Operation(summary = "Apaga circuito electrico monopatin", description = "Da señal a monopatin de cortar circuitos electricos")

    public String apagar(@PathVariable Long id, @RequestBody FinalizarViajeDTO finalizarViajeDTO,
            @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        Monopatin m = monoRepo.findById(id).get();

        Boolean estacionado = paradaServicio.estaEstacionado(m);

        if (estacionado) {
            Long kmts = finalizarViajeDTO.getKmts();
            m.sumarKmts(kmts);
            m.setTiempoDeUsoConPausa(finalizarViajeDTO.getTiempoDeUsoConPausa());
            m.setTiempoDeUsoSinPausa(finalizarViajeDTO.getTiempoDeUsoSinPausa());
            m.setEncendido(false);
            monoRepo.save(m);
            return "se estaciono correctamente";
        } else
            return "no esta en un parada";

    }

    @PutMapping("/mantenimiento/{id}")
    @Operation(summary = "Agregar a area mantenimiento", description = "Registra en el sistema que la unidad se encuentra en reparaciones o mantenimiento")

    public Monopatin ponerEnReparacion(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization).contains("ADMIN")) {

            Monopatin m = monoRepo.findById(id).get();
            m.setEn_taller(true);
            return monoRepo.save(m);
        }
        return null;

    }

    @PutMapping("/mantenimiento/{id}/listo")
    @Operation(summary = "Listo para usar", description = "Registra que el monopatin se incorpora a su uso habitual")

    public Monopatin ponerEnLaCalle(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization).contains("ADMIN")) {

            Monopatin m = monoRepo.findById(id).get();
            m.setEn_taller(false);
            return monoRepo.save(m);
        }
        return null;

    }

    // Si esta prendido se pone en standby y si es al contrario se prende
    @GetMapping("/ponerEnStandBy/{id}")
    @Operation(summary = "StanBy", description = "Se contacta con el sistema de ahorro de energia")

    public Monopatin suspenderMonopatin(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        Monopatin m = monoRepo.findById(id).get();
        m.setEncendido(!m.isEncendido());
        return monoRepo.save(m);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar monopatin", description = "Se retira del sistema tal monopatin")

    public void borrar(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization).contains("ADMIN")) {

            monoRepo.deleteById(id);
        }
        return;
    }

    @GetMapping("/paradaCercana/{idMono}")
    @Operation(summary = "Lista de paradas cercanas", description = "Lista ordenada con las paradas mas cercanas a tal monopatin")

    public List<Distancia> dameParadaCercana(@PathVariable Long idMono,
            @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        // public List<Monopatin> dameParadas() {
        List<Distancia> resultado = new ArrayList<>();
        Monopatin monopatin = monoRepo.findById(idMono).get();
        String body = servicio.dameParadas().getBody();
        ObjectMapper jsonMap = new ObjectMapper();
        JsonNode array;
        try {
            array = jsonMap.readTree(body);
            for (JsonNode nodo : array) {

                Long id = nodo.get("id_parada").asLong();
                Float latitud = (float) nodo.get("latitud").asDouble();
                Float longitud = (float) nodo.get("longitud").asDouble();
                Float distancia = (float) Math.sqrt((Math.pow((latitud - monopatin.getLatitud()), 2))
                        + Math.pow((longitud - monopatin.getLongitud()), 2)) * 100;

                Distancia resp = new Distancia(idMono, id, distancia);
                resultado.add(resp);
            }
            Comparator<Distancia> comparador = Comparator.comparing(Distancia::getDistancia);
            Collections.sort(resultado, comparador);

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultado;
    }

    @GetMapping("/estaListoParaUsar/{id}")
    @Operation(summary = "Disponobilidad", description = "Aqui se cosulta si esta listo para ser usado y emprender un viaje")

    public Boolean estaLista(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization) == null)
            return null;
        Monopatin m = this.monoRepo.getById(id);
        if (m == null)
            return false;
        return (!m.isEn_taller() && !m.isEncendido() && paradaServicio.estaEstacionado(m));
    }

    @GetMapping("/reporteMonopatines/{pausa}")
    @Operation(summary = "Reporte", description = "Brinda reporte como se lo solicito en el inciso X del contrato Z")

    public List<ReporteMonopatinesDTO> generarReporte(@PathVariable Boolean pausa,
            @RequestHeader("Authorization") String authorization) {

        if (token.autorizado(authorization).contains("ENC")) {

            boolean conPausa = false;
            if (pausa)
                conPausa = true;

            ArrayList<ReporteMonopatinesDTO> reporte = new ArrayList<ReporteMonopatinesDTO>();

            // Busca monopatines y los ordena por kilometros
            List<Monopatin> monopatines = monoRepo.findAll();
            Collections.sort(monopatines,
                    (m1, m2) -> Integer.compare(m2.getKm_ultimo_service(), m1.getKm_ultimo_service()));

            for (Monopatin monopatin : monopatines) {
                ReporteMonopatinesDTO dto = new ReporteMonopatinesDTO();
                dto.setMonopatin_id(monopatin.getId_monopatin());
                dto.setKmtsUltimoService(monopatin.getKm_ultimo_service());
                if (conPausa) {
                    dto.setTiempoDeUsoConPausa(monopatin.getTiempoDeUsoConPausa());
                }
                reporte.add(dto);
            }

            return reporte;
        }
        return null;
    }

    @GetMapping("/cantidadMonopatines")
    @Operation(summary = "Cantidad de monopatines", description = "Brinda la cantidad detallada de monopatines en operacion y en mantenimiento")

    public Map<String, Integer> obtenerCantidadMonopatines(@RequestHeader("Authorization") String authorization) {
        if (token.autorizado(authorization).contains("ADMIN")) {
            int enOperacion = monoRepo.countMonopatinesEnOperacion();
            int enMantenimiento = monoRepo.countMonopatinesEnMantenimiento();

            Map<String, Integer> cantidadMonopatines = new HashMap<>();
            cantidadMonopatines.put("Operacion", enOperacion);
            cantidadMonopatines.put("Mantenimiento", enMantenimiento);

            return cantidadMonopatines;

        }
        return null;
    }
}
