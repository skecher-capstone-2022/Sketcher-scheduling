package sketcher.scheduling.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ManagerHopeTimeRepositoryCustom {
    ArrayList<String> findHopeTimeById(String id);
    void deleteByUserId(String id);

    public HashMap<String, Long> CountByHopeTime();
}
