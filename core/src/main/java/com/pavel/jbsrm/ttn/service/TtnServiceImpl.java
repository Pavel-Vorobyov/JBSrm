package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.repository.TtnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class TtnServiceImpl implements TtnService {

    @Autowired
    private TtnRepository ttnRepository;

    @Override
    public TtnDto createClient(@Valid CreateTtnDto createTtnDto) {
        return null;
    }

    @Override
    public TtnDto updateClient(long id, @Valid UpdateTtnDto updateTtnDto) {
        return null;
    }

    @Override
    public void deleteClient(long id) {

    }

    @Override
    public TtnDto find(long id) {
        return null;
    }
}
