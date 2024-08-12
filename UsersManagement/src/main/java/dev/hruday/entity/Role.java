package dev.hruday.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public enum Role{
    HOST,
    PARTICIPANT,
    ADMIN

}