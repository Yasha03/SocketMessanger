package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Message implements Serializable {

    private byte type;

    private byte[] data;

    private final Charset messageTextCharset = StandardCharsets.UTF_16;

    @Override
    public String toString() {
        return new String(data, messageTextCharset);
    }

}
