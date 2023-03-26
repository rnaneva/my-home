package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.repository.AgencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    private AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public AgencyEntity findByName(String name){
       return agencyRepository.findByName(name).orElse(null);
    }

    public List<String> getAllAgenciesNames(){
        return agencyRepository.getAllAgenciesNames();
    }
}
