package com.test.firstproject.service;
import com.test.firstproject.dto.request.StudentRequest;
import com.test.firstproject.dto.response.StudentResponse;
import com.test.firstproject.dto.response.StudentWithProfileResponse;
import org.springframework.data.domain.Page;


import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);
    Page<StudentResponse> getStudentsPaginated(int page, int size, String sortBy, String direction);
    List<StudentResponse> searchStudent(String name,String email);
    StudentWithProfileResponse getStudentWithProfile(Long id);
}
