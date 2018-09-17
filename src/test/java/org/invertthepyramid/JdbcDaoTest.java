package org.invertthepyramid;

import org.invertthepyramid.nonfunctionals.ILogger;
import org.invertthepyramid.nonfunctionals.IMetrics;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

public class JdbcDaoTest {

    @Test
    public void testHappyPathWritesToDatabaseAndProducesLogsMetrics() {
        ILogger log = mock(ILogger.class);
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        IMetrics metrics = mock(IMetrics.class);
        JdbcDAOFunctional dao = new JdbcDAOFunctional(log, jdbc, metrics);

//        dao.read()

//        verify(jdbc, times(1)).update(sql);
//        verify(metrics, times(1)).mark(successfulMetricName, 1);
//        verify(log, times(1)).info("Written to database");
    }

}


