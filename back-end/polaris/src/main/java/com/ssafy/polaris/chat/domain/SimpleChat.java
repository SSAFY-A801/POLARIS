package com.ssafy.polaris.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleChat {
    private Long id;
    private String username;
    private String text;

    // Getters and setters...

    @Override
    public String toString() {
        return "ChatMessages{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
