package poroto.po.monopatin.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import poroto.po.monopatin.entity.Monopatin;

public interface MonopatinRepo extends JpaRepository<Monopatin,Long>{
    
    
}
