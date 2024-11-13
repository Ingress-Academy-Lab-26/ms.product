package org.example.msproduct.client.decoder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JsonNodeFieldName {
    MESSAGE("message"),
    CODE("code");
    private final String value;
}
