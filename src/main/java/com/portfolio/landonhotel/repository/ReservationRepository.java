package com.portfolio.landonhotel.repository;


import com.portfolio.landonhotel.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}
