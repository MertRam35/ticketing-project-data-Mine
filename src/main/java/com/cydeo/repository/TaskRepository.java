package com.cydeo.repository;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {




    @Override
    List<Task> findAll();

    @Query("select count(t) from Task t WHERE t.project.projectCode = ?1 and t.taskStatus <> 'Complete'")
    int totalCompletedTasks(String projectCode);


    @Query(nativeQuery = true, value = "select count(*)"
            + " from tasks t join projects p on t.project_id = p.id "
            + "where p.project_code =?1 and t.task_status ='COMPLETE'")
    int totalNonCompletedTasks(String projectCode);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User user);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User user );
}
