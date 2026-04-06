package com.portfolio.landonhotel.convertor;

import com.portfolio.landonhotel.entity.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: Landon Hotel Booking App
 * Package: com.portfolio.landonhotel.convertor
 * <p>
 * User: carolyn.sher
 * Date: 9/16/2022
 * Time: 6:06 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

public interface ReservationService {
    public ReservationEntity findLast();
    public List<ReservationEntity> findAll();
}

