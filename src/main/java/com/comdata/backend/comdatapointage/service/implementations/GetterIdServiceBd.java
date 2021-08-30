package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.*;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class GetterIdServiceBd implements IGetterIdService {

    @Autowired private UserRepository userRepository;
    @Autowired private ActiviterRepository activiterRepository;
    @Autowired private TempsRepository tempsRepository;
    @Autowired private ParametrageRepository parametrageRepository;
    @Autowired private TypePauseRepository typePauseRepository;

    @Override
    public User getUser(String matricule) throws Exception {
        Optional<User> optional = userRepository.findById(matricule);
        if(optional.isPresent())
            return optional.get();
        throw new Exception("user not found");
    }

    @Override
    public Activiter getActiviter(Integer id) throws Exception {
        Optional<Activiter> optional = activiterRepository.findById(id);
        if(optional.isPresent())
            return optional.get();
        throw new Exception("activiter not found");
    }

    @Override
    public Temps getTemps(Integer id) throws Exception {
        Optional<Temps> optional = tempsRepository.findById(id);
        if(optional.isPresent())
            return optional.get();
        throw new Exception("temps not found");
    }

    @Override
    public Parametrage getParametrage(Integer id) throws Exception {
        Optional<Parametrage> optional = parametrageRepository.findById(id);
        if(optional.isPresent())
            return optional.get();
        throw new Exception("parametrage not found");
    }

    @Override
    public TypePause getTypePause(Integer id) throws Exception {
        Optional<TypePause> optional = typePauseRepository.findById(id);
        if(optional.isPresent())
            return optional.get();
        throw new Exception("type not found");
    }
}
