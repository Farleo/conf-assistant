package lms.itcluster.confassistant.service;

import java.util.Map;

public interface StaticDataService {

    String getUpdatedEmail(Long userId);

    void addUpdatedEmail(Long id, String email);

    void removeUpdatedEmail(Long id);

    Map<Long, String> getEmailMap();
}
