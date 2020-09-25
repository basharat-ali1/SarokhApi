package net.sarokh.api.service;

import net.sarokh.api.dao.ReceiverRepository;
import net.sarokh.api.model.entity.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiverService {

    @Autowired
    private ReceiverRepository repository;

    public Receiver addReceiver (Receiver receiver){
        return repository.save(receiver);
    }

    public Optional<Receiver> getReceiverById(Integer id){
        return repository.findById(id);
    }

    public Iterable<Receiver> getReceiversList() {
        return repository.findAll();
    }

    public Receiver upateReceiver (Receiver receiver){
        return repository.save(receiver);
    }

    public void deleteReceiver (Integer id){
        repository.deleteById(id);
    }
}
