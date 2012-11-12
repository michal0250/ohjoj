package com.example.lowpricesapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lowpricesapp.DAO.CreateDBDAO;

@Service
public class CreateDBService {

    @Autowired
    private CreateDBDAO createDBDAO;

    public void createNewDB() throws Exception {
        createDBDAO.createDataBase();
    }

    public void setCreateDBDAO(CreateDBDAO createDBDAO) {
        this.createDBDAO = createDBDAO;
    }

}
