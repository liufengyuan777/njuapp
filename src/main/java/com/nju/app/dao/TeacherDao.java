package com.nju.app.dao;

import com.nju.app.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<Teacher,Integer> {

    public Teacher findByTId(String username);

}
