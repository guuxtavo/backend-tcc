package com.ads.tcc.services.exceptions;

import javax.swing.text.html.parser.Entity;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String msg){
        super(msg);
    }

}
