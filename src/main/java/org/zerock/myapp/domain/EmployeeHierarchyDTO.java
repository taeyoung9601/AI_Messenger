package org.zerock.myapp.domain;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Data;

@Data
public class EmployeeHierarchyDTO {


private String empno;
private String department;
private String position;
private String name;
private String path;

public EmployeeHierarchyDTO(String empno, String department, String position, String name, String path) {
    this.empno = empno;
    this.department = department;
    this.position = position;
    this.name = name;
    this.path = path;
}



}