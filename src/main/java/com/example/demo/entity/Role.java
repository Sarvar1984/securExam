package com.example.demo.entity;

import com.example.demo.entity.temp.AbcEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Role extends AbcEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
