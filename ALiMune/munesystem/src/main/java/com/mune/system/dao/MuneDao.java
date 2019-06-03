package com.mune.system.dao;

import com.mune.system.entity.Mune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MuneDao  extends JpaRepository<Mune,Long>  {
}
