package lms.itcluster.confassistant.service;

import java.util.List;
import java.util.Map;

public interface StaticDataService {

    Map<Integer, String> getMonthMap();

    List<Integer> getYears();

    List<Integer> getDays(Integer year, Integer month);

    String getUpdatedEmail(Long userId);

    void addUpdatedEmail(Long id, String email);

    void removeUpdatedEmail(Long id);

    Map<Long, String> getEmailMap();
}
