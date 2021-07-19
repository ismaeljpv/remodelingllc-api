package com.remodelingllc.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DbTablesInfo {

    @Id
    private long id;
    private String tableName;
    private int totalSize;

}
