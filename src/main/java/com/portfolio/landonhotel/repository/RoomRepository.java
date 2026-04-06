package com.portfolio.landonhotel.repository;

import java.util.List;

import com.portfolio.landonhotel.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;



public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

	//RoomEntity findById(Long id);
}

