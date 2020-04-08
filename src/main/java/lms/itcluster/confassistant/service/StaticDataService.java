package lms.itcluster.confassistant.service;

import java.util.List;
import java.util.Map;

public interface StaticDataService {

    Map<Integer, String> getMonthMap();

    List<Integer> getYears();

    List<Integer> getDays(Integer year, Integer month);
}
