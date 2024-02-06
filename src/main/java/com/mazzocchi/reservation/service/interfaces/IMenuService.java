package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.models.*;

import java.util.*;

public interface IMenuService {
    List<Menu> findAllMenus();
    Menu findMenuById(int id);

}
