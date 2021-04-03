package devinc.dwitter.service;

import devinc.dwitter.entity.dto.RegistrationRequest;
import devinc.dwitter.entity.dto.UserDto;

public interface RegistrationService {
    UserDto registerUser(RegistrationRequest registrationRequest);


}
