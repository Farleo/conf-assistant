package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.service.StaticDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StaticDataServiceImpl implements StaticDataService {

    private Map<Long, String> updatedEmail = new HashMap<>();

    @Override
    public Map<Integer, String> getMonthMap() {
        String[] month = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        Map<Integer, String> monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthMap.put(i, month[i-1]);
        }
        return monthMap;
    }

    @Override
    public List<Integer> getYears() {
        List<Integer> list = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        for (int i = startDate.getYear(); i < startDate.getYear() + 5; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public List<Integer> getDays(Integer year, Integer month) {
        List<Integer> list = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);
        int days = date.lengthOfMonth();
        for (int i = 1; i <= days; i++) {
            list.add(i);
        }
        return list;
    }

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
