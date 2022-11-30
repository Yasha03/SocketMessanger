package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Message implements Serializable {

    private byte type;

    private byte[] data;

    @Override
    public String toString() {
//        StringBuilder res = new StringBuilder();
//        for (byte datum : data) {
//            char symbol = (char) datum;
//            res.append(symbol);
//        }
//        return res.toString();
        return new String(data, StandardCharsets.UTF_16);
    }

}
