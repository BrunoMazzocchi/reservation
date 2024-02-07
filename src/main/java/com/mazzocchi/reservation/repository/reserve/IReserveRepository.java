package com.mazzocchi.reservation.repository.reserve;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IReserveRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByState(State state, Pageable pageable);

}
