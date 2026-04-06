package com.portfolio.landonhotel.repository;

import com.portfolio.landonhotel.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

/*
public interface PageableRoomRepository extends PagingAndSortingRepository<RoomEntity, Long> {
	
	Page<RoomEntity> findById(Long id, Pageable page);
	Page<RoomEntity> findAvailableRooms(LocalDate checkin, LocalDate checkout, Pageable page);

}
*/
