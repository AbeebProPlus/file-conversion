package com.converter.fileconverter.dto.request;

import com.converter.fileconverter.data.User;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class CsvDto {
    private List<User> users;
}
