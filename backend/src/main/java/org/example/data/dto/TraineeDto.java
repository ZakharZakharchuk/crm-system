package org.example.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TraineeDto extends UserDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^[a-zA-Z0-9/.\\s]+$", message = "Address should only contain letters, numbers, spaces, dots and forward slashes")
    private String address;
}
