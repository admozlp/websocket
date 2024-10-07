package com.ademozalp.websocket.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Message {
    private String content;
    private String to;
    private String dateTime;
}
