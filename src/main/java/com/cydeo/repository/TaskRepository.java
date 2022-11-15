package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE' ")
    int totalNonCompletedTasks(String projectCode);

    @Query(value = "select count(*) " +
            "from tasks t join projects p on t.project_id=p.id " +
            "where p.project_code=?1 and t.task_status = 'COMPLETE'", nativeQuery = true)
    int totalCompletedTasks(String projectCode);
    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User loggedInUser);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User loggedInUser);
}
