package com.nkd.pos.posapi.service;

import java.util.Optional;

import com.nkd.pos.posapi.entity.EntityAddress;
import com.nkd.pos.posapi.entity.Usr;

public interface UserService {
public Usr getById(int id);
public EntityAddress getUserAdress(Usr User);
public Optional<Usr> getByUsername(String username);
public Usr save(Usr User);
}
