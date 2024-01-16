package com.spring.security.services.models.validation;

import com.spring.security.persistence.entities.User;
import com.spring.security.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validate(User user){
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setNumOfErrors(0);

        if(user.getFirstName() == null ||
           user.getFirstName().length() < 3 ||
           user.getFirstName().length() > 15
        ){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("El campo first name no puede ser" +
                    "nulo y debe tener entre 3 y 15 caracteres");
        }

        if(user.getLastName() == null ||
        user.getLastName().length() < 3 ||
        user.getLastName().length() > 30
        ){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("El campo last name no puede ser" +
                    "nulo y debe tener entre 3 y 30 caracteres");
        }

        if(user.getEmail() == null ||
           !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        ){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("El campo email no es valido");
        }

        if(user.getPassword() == null ||
           !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")
        ){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("La password debe tener entre 8 y 16 caracteres" +
                    ", al menos un numero, una minuscula y una mayuscula");
        }
        return responseDTO;
    }
}
