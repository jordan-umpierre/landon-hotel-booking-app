package com.portfolio.landonhotel.convertor;

import com.portfolio.landonhotel.entity.ReservationEntity;
import com.portfolio.landonhotel.repository.ReservationRepository;
import com.portfolio.landonhotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: Landon Hotel Booking App
 * Package: com.portfolio.landonhotel.convertor
 * <p>
 * User: carolyn.sher
 * Date: 9/16/2022
 * Time: 6:07 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ReservationServiceImpl implements ReservationService{

    private ReservationRepository reservationRepository;

    @Autowired
    public  ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository=reservationRepository;
    }
    @Override
    public ReservationEntity findLast() {
        List<ReservationEntity> allReservations= (List<ReservationEntity>) reservationRepository.findAll();
        return allReservations.get(allReservations.size()-1);
    }

    @Override
    public List<ReservationEntity> findAll(){
        return (List<ReservationEntity>) reservationRepository.findAll();
    }
}

