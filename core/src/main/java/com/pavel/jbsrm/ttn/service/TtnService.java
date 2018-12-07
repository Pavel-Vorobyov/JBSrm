package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;

import javax.validation.Valid;

public interface TtnService {

    TtnDto createClient(@Valid CreateTtnDto createTtnDto);

    TtnDto updateClient(long id, @Valid UpdateTtnDto updateTtnDto);

    void deleteClient(long id);

    TtnDto find(long id);
}
