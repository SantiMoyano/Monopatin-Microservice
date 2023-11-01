package poroto.po.monopatin.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poroto.po.monopatin.entity.Monopatin;

@Repository
public interface MonopatinRepo extends JpaRepository<Monopatin,Long>{
    @Query("SELECT COUNT(m) FROM Monopatin m WHERE m.en_taller = false")
    public int countMonopatinesEnOperacion();

    @Query("SELECT COUNT(m) FROM Monopatin m WHERE m.en_taller = true")
    public int countMonopatinesEnMantenimiento();
}
