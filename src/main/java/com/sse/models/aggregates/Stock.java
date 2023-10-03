package com.sse.models.aggregates;

import com.sse.models.valueobjects.Status;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Entity
@ToString
public class Stock {
    @Id
    private String stockCode;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Float rate;
}
