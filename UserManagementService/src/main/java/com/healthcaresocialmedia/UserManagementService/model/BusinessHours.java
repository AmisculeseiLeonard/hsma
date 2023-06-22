package com.healthcaresocialmedia.UserManagementService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessHours {

//    @JsonFormat(pattern = "HH:mm:ss")
//    @JsonSerialize(using = StringSerializer.class)
//    @JsonDeserialize(using = LocalTimeDeserializer.class)
//    private LocalTime from;
//    @JsonFormat(pattern = "HH:mm:ss")
//    @JsonSerialize(using = StringSerializer.class)
//    @JsonDeserialize(using = LocalTimeDeserializer.class)
//    private LocalTime to;

    private String from;
    private String to;
}
