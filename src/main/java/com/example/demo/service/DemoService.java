package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.DemoModel;
import com.example.demo.model.DemoRepository;

@Service
public class DemoService {
    @Autowired
    private DemoRepository demoRepository;

    // 根據名字查詢使用者
    public DemoModel getUserByName(String name) {
        return this.demoRepository.findByName(name);
    }

    public DemoModel createUserByName(String name) {
        DemoModel model = new DemoModel();
        model.setName(name);
        return this.demoRepository.save(model);
    }
}
