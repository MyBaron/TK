package com.mune.system.service;


import com.mune.system.dao.MuneDao;
import com.mune.system.entity.Mune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MuneService {

    @Autowired
    private MuneDao muneDao;

    public List<Mune> getAllMune() {
        return muneDao.findAll();
    }

    public Optional<Mune> getMuneById(Long id){
        return muneDao.findById(id);
    }

    public List<Mune> getMuneListByIds(List<Long> idList) {
        return muneDao.findAllById(idList);
    }

    public Map<Long,Mune>  getMuneMapByIds(List<Long> idList){
        List<Mune> muneListByIds = getMuneListByIds(idList);
        Map<Long, Mune> muneMap = new HashMap<>();
        muneListByIds.forEach(s->muneMap.put(s.getId(),s));
        return muneMap;
    }

    public void save(Mune mune){
        muneDao.save(mune);
    }

    public void delete(long id) {
         muneDao.deleteById(id);
    }

}
