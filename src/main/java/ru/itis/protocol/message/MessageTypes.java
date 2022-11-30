package ru.itis.protocol.message;

import ru.itis.protocol.exceptions.MessageTypeException;

import java.lang.reflect.Field;

public class MessageTypes {

    public static final byte DEFAULT_MESSAGE = 1;

    public static final byte STICKER_MESSAGE = 2;

    public static final byte ERROR_MESSAGE = 3;

    public static boolean typeIsExist(byte typeNumber) throws MessageTypeException {
        for(Field field : MessageTypes.class.getDeclaredFields()){
            try {
                if(field.getByte(new MessageTypes()) == typeNumber){
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new MessageTypeException("Error with existing message types");
            }
        }
        return false;
    }
}
