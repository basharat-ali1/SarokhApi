package net.sarokh.api.service;

import net.sarokh.api.dao.BankAccountRepository;
import net.sarokh.api.model.entity.BankAccount;
import net.sarokh.api.model.BankAccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public BankAccount addBankAccount(BankAccountDTO bankAccountDTO){

        BankAccount accountEntity = modelMapper.map(bankAccountDTO, BankAccount.class);

        BankAccount account = repository.save(accountEntity);

        return account;
    }

    public Optional<BankAccount> getBankAccountById(Integer id){
        return repository.findById(id);
    }

    public Iterable<BankAccount> getBankAccountsList() {
        return repository.findAll();
    }

    public BankAccount updateBankAccount(BankAccount bankAccount){
        return repository.save(bankAccount);
    }

    public void deleteBankAccount(Integer id){
        repository.deleteById(id);
    }

    
}
