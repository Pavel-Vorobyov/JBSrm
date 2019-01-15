package com.pavel.jbsrm.common.utill;

import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ObjectMapperUtillsTest {

    @Test
    public void mapSameTypeFieldsTest() {
        UpdateTtnDto mappingFromDto = UpdateTtnDto.builder()
                .driverId(1L)
                .transportId(2L)
                .build();
        UpdateTtnDto mappingToDto = UpdateTtnDto.builder().build();

        ObjectMapperUtills.mapTo(mappingFromDto, mappingToDto);
        Assert.assertEquals(mappingFromDto, mappingToDto);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class TestDto {
        private long id;
        private long firstDtoId;
        private long secondDtoId;
    }
}