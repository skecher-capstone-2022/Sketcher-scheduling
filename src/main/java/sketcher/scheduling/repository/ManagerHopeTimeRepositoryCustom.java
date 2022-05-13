package sketcher.scheduling.repository;

import java.util.ArrayList;

public interface ManagerHopeTimeRepositoryCustom {
    ArrayList<String> findHopeTimeById(String id);
    void deleteByUserId(String id);
}
