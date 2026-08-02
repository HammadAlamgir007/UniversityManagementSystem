package com.test.firstproject.controller;
import com.test.firstproject.dto.response.ApiResponse;
import com.test.firstproject.dto.response.StudentWithProfileResponse;
import com.test.firstproject.service.StudentService;
import com.test.firstproject.dto.request.StudentRequest;
import com.test.firstproject.dto.response.StudentResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    // constructure injection
    // setter injection? autowired? @RequiredArgConstructure  @RestController / @Controller
    // ResponseEntity
    // return specific response code in all apis
    private final StudentService studentService;
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest request )
    {
        StudentResponse student = studentService.createStudent(request);
        ApiResponse<StudentResponse> response =
                new ApiResponse<>(
                        "Student created successfully",
                        "00",
                        student
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents()
    {

        List<StudentResponse> student = studentService.getAllStudents();
        ApiResponse<List<StudentResponse>> response =
                new ApiResponse<>(
                        "Student created successfully",
                        "00",
                        student
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@Valid @PathVariable Long id) {

        StudentResponse student = studentService.getStudentById(id);

        ApiResponse<StudentResponse> response =
                new ApiResponse<>(
                        "Student fetched successfully",
                        "00",
                        student
                );

            return ResponseEntity.ok(response);

    }
    @GetMapping("/{id}/profile")
    public ResponseEntity<ApiResponse<StudentWithProfileResponse>>
    getStudentWithProfile(
            @PathVariable Long id
    ){

        StudentWithProfileResponse profile =
                studentService.getStudentWithProfile(id);


        ApiResponse<StudentWithProfileResponse> response =
                new ApiResponse<>(
                        "Student profile fetched successfully",
                        "00",
                        profile
                );


        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent( @PathVariable Long id,@Valid
             @RequestBody StudentRequest request ) {
        StudentResponse updatedStudent = studentService.updateStudent(id,request);
        ApiResponse<StudentResponse> response =
        new ApiResponse<>(
                "Student fetched successfully",
                "00",
                updatedStudent
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @PathVariable Long id)
    {

        studentService.deleteStudent(id);
        ApiResponse<Void> response =
                new ApiResponse<>(
                        "Student fetched successfully",
                        "00",
                        null

                );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> searchStudent(
            @Valid @RequestParam(required = false)
            String name,
            @RequestParam(required = false)
            String email) {

        List<StudentResponse> students =
                studentService.searchStudent(name, email);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Students fetched successfully",
                        "00",
                        students
                )
        );
    }
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<StudentResponse>>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        Page<StudentResponse> students = studentService.getStudentsPaginated(page, size, sortBy, direction);

        ApiResponse<Page<StudentResponse>> response = new ApiResponse<>(
                "Students fetched successfully",
                "00",
                students
        );

        return ResponseEntity.ok(response);
    }
}
