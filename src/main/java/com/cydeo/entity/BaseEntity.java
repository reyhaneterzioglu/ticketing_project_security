package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

//    @PrePersist
//    private void onPrePersist() {
//        this.insertDateTime = LocalDateTime.now();
//        this.lastUpdateDateTime = LocalDateTime.now();
//        this.insertUserId = 1L;
//        this.lastUpdateUserId = 1L;
//    }
//
//    @PreUpdate
//    private void onPreUpdate() {
//        this.lastUpdateDateTime = LocalDateTime.now();
//        this.lastUpdateUserId = 1L;
//    }

//    @PrePersist
//    private void onPrePersist() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        this.insertDateTime = LocalDateTime.now();
//        this.lastUpdateDateTime = LocalDateTime.now();
//
//        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
//
//            Object principal = authentication.getPrincipal();
//
//            insertUserId=((UserPrincipal) principal).getId();
//            lastUpdateUserId=((UserPrincipal) principal).getId();
//        }
//    }
//    @PreUpdate
//    private void onPreUpdate() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
//
//            Object principal = authentication.getPrincipal();
//
//            lastUpdateUserId=((UserPrincipal) principal).getId();
//        }
//
//    }

}
