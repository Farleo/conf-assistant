package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.service.StaticDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaticDataServiceImpl implements StaticDataService {

    private Map<Long, String> updatedEmail = new HashMap<>();

    @Override
    public String getUpdatedEmail(Long userId) {
        return updatedEmail.get(userId);
    }

    @Override
    public void addUpdatedEmail(Long id, String email) {
        updatedEmail.put(id, email);
    }

    @Override
    public void removeUpdatedEmail(Long id) {
        updatedEmail.remove(id);
    }

    @Override
    public Map<Long, String> getEmailMap() {
        return updatedEmail;
    }
}
