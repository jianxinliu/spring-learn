# JPA

实体注解：

- `@Entity`, `@MappedSupperClass`
- `@Table`

主键：
- `@Id`
  - `@GeneratedValue(strategy, generator)`
  - `@SequenceGenerator(name, sequenceName)`
  
映射:
- `@Column(name, nullable, length, insertable, updatable)`
- `@JoinTable(name)`, `@JoinColumn(name)`

关系:
- `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`
- `@OrderBy`

Repository: 用于操作实体和表的接口

**spring.data.repository**:
- `CrudRepository`
- `PagingAndSortintRepository`

**spring.data.jpa.repository**
- `JpaRepository`
- `SimpleJpaRepository`