package com.cst438.controller;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;


@RestController
public class StudentController {

	@Autowired 
	StudentRepository studentRepository;
	
	@PostMapping("/student")
	@Transactional
	public Student createnewStudent(@RequestBody StudentDTO studentDTO) {
		System.out.println("/student made");
		Student student = studentRepository.findByEmail(studentDTO.getEmail());
		
		if(student == null) {
			student = new Student();
			student.setEmail(studentDTO.getEmail());
			student.setName(studentDTO.getName());
			System.out.println("/created student "+student.getName()+" "+student.getEmail());
			
			studentRepository.save(student);
			
			return student;
		}else {
			System.out.println("/student not found. "+ studentDTO.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student already exists");
		}
	}
	
	
	@PostMapping("/student/hold")
	@Transactional
	public Student Hold(@RequestBody StudentDTO studentDTO) {
		Student student = studentRepository.findByEmail(studentDTO.getEmail());
		
		if(student!= null) {
			student.setStatusCode(1);
			student.setStatus("Hold");
			
			System.out.println("/place student hold "+student.getName()+" "+student.getStatus() + " " + student.getStatusCode());
			studentRepository.save(student);
			return student;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student Record not found.");
		}
		
	}
	
	@PostMapping("/student/deleteHold")
	@Transactional
	public Student deleteHold(@RequestBody StudentDTO studentDTO) {
		Student student = studentRepository.findByEmail(studentDTO.getEmail());
		
		if(student !=null) {
			student.setStatusCode(0);
			student.setStatus(null);
			System.out.println("/remove hold "+student.getName()+" "+student.getStatus() + " " + student.getStatusCode());
			studentRepository.save(student);
			
			return student;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student non Existent");
		}
	}
} //adding comment to studentcontroller to test if we're able to make a new pull request since i made a mistake closing it 
