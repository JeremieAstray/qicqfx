package com.jeremie.qicqfx.client.service.impl;

import com.jeremie.qicqfx.client.dto.UserDTO;
import com.jeremie.qicqfx.client.service.FooService;
import org.springframework.stereotype.Service;

/**
 * Created by jeremie on 15/5/8.
 */
@Service("fooService")
public class FooServiceImpl implements FooService {

    @Override
    public UserDTO findFooById(int id) {
        return new UserDTO();
    }
}
