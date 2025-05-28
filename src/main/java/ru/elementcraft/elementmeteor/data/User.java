package ru.elementcraft.elementmeteor.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class User {
    private final String name;
    private int uses;

}
