package com.example.autoHRM_backend.domain.apply;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Query("SELECT a FROM Apply a WHERE " +
            "a.employee IN :employees AND " +
            "(:type IS NULL OR a.type = :type) AND " +
            "(:status IS NULL OR a.status = :status)")
    List<Apply> findByEmployeeInAndOptionalTypeAndStatus(
            @Param("employees") List<Employee> employees,
            @Param("type") String type,
            @Param("status") Boolean status);


}
