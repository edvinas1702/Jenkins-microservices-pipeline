package com.edvinas.user.service;

import com.edvinas.user.VO.Department;
import com.edvinas.user.VO.ResponseTemplateVO;
import com.edvinas.user.entity.User;
import com.edvinas.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //With the restTemplate we will call the department service and get the department object
    @Autowired
    private RestTemplate restTemplate;


    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        //Gaunamas user naudojant userId
        User user = userRepository.findByUserId(userId);
        // Gaunamas department naudojant user'io departmentId
        Department department =
                restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId() , Department.class);

        vo.setUser(user);
        vo.setDepartment(department);

        return vo;

        // Postman commands:
        // GET http://localhost:9002/users/1

        //HOW TO POST USER:
        //{
        //    "firstName": "Edvinas",
        //    "lastName": "K.",
        //    "email": "email123@gmail.com",
        //    "departmentId":"1"
        //}

        // HOW TO POST DEPARTMENT:
        //{
        //    "departmentName":"IT",
        //    "departmentAddress":"3rd Cross, First Street",
        //    "departmentCode": "IT-006"
        //}

    }

    public void deleteUser(Long userId) {
        log.info("Inside deleteUser of UserService");
        userRepository.deleteById(userId);
    }

    public User updateUser(Long userId, User userDetails) {
        log.info("Inside updateUser of UserService");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setDepartmentId(userDetails.getDepartmentId());

        return userRepository.save(user);
    }

}
