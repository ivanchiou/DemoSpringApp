package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.DemoModel;
import com.example.demo.model.DemoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DemoService {
    @Autowired
    private DemoRepository demoRepository;

    // 根據名字查詢使用者
    public Optional<DemoModel> getUserByName(String name) {
        return Optional.ofNullable(this.demoRepository.findByName(name));
    }

    public List<DemoModel> filterModelsByName(String keyword) {
        List<DemoModel> models = this.demoRepository.findAll();
        return models.stream()
                    .filter(model -> model.getName().contains(keyword))
                    .collect(Collectors.toList());
    }

    public DemoModel getUserByID(int id) {
        return this.demoRepository.findByID(id);
    }

    public boolean checkUserName(String name) {
        return this.demoRepository.existsByName(name);
    }

    public boolean updateUserName(int id, String name) {
        DemoModel model = this.demoRepository.findByID(id);
        model.setName(name);
        return this.demoRepository.save(model) != null ? true : false;
    }

    public DemoModel createUserByName(String name) {
        DemoModel model = new DemoModel();
        model.setName(name);
        return this.demoRepository.save(model);
    }
}
