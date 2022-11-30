package ru.itis.protocol;

import ru.itis.protocol.exceptions.MessageTypeException;
import ru.itis.protocol.message.MessageTypes;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println(MessageTypes.typeIsExist((byte) 0));
        } catch (MessageTypeException e) {
            throw new IllegalArgumentException("Invalid type constants");
        }
        byte b = 65;
        char c = (char) b;
        System.out.println("CHAR: " + c);
    }
}
