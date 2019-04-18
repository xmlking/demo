package com.example.demo;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@MappedSuperclass
class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}


@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper=true, of = "id")
@Builder
@Entity
@Table(name = "customer", schema = "demo")
class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String city;
    private Long ppn;
}


@Data
@ToString()
@AllArgsConstructor(staticName = "of")
@Builder
class CustomerDto {
    private Long id;
    private String city;
}