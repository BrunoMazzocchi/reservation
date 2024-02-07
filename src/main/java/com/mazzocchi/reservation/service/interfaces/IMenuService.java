package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IMenuService {
    Page<Menu> findAllMenus(State state, Pageable pageable);
    Menu findMenuById(Long id);

}
