package org.gnsg.gms.v1.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.gnsg.gms.domain.PRoul;
import org.gnsg.gms.domain.PRoulBean;
import org.gnsg.gms.domain.PRoulBeanBhogDate;
import org.gnsg.gms.domain.PathReport;
import org.gnsg.gms.domain.enumeration.PATHSEARCHBY;
import org.gnsg.gms.domain.enumeration.PROGTYPE;
import org.gnsg.gms.repository.PRoulRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class PathiReportHelper {
    @Autowired
    PRoulRepository pRoulRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final int PathiBheta = 20;

    private final Logger log = LoggerFactory.getLogger(PathiReportHelper.class);

    public PathReport generatePathReport(PathReport pathReport) {
        List<PRoul> rouls = null;
        List<PRoulBeanBhogDate> pathNameBeanList = null;
        List<PRoulBean> roulBeanList = null;
        if (pathReport.getSearchBy().equals(PATHSEARCHBY.PATHI_SINGH_NAME)) {
            if (pathReport.getPathi() != null && pathReport.getPathi().getName() != null && pathReport.getPathType().equals(PROGTYPE.ALL)) {
                String sql =
                    "SELECT DISTINCT ( pro.pathi_Name)  ,   SUM (pro.total_Roul)  as  RoulTotal  , SUM (pro.total_amt)  as RoulTotalAmt  FROM  P_Roul pro where pro.pathi_name=? and  pro.bhog_date BETWEEN ? AND ? GROUP BY pro.pathi_Name";
                roulBeanList =
                    jdbcTemplate.query(
                        sql,
                        new Object[] { pathReport.getPathi().getName(), pathReport.getStartDate(), pathReport.getEndDate() },
                        new RoulRowMapper()
                    );
                String pathNamesql =
                    "SELECT DISTINCT ( pro.jhi_desc)  as pathi_Name  , pro.bhog_date  FROM  P_Roul pro where pro.pathi_name=? and pro.bhog_date BETWEEN ? AND ? GROUP BY pro.bhog_date , pro.jhi_desc";
                pathNameBeanList =
                    jdbcTemplate.query(
                        pathNamesql,
                        new Object[] { pathReport.getPathi().getName(), pathReport.getStartDate(), pathReport.getEndDate() },
                        new PathRoulRowMapper()
                    );
            } else if (pathReport.getPathType() != PROGTYPE.ALL) {
                String sql =
                    "SELECT DISTINCT ( pro.pathi_Name)  ,   SUM (pro.total_Roul)  as  RoulTotal  , SUM (pro.total_amt)  as RoulTotalAmt  FROM  P_Roul pro where pro.pathi_name=? and pro.jhi_desc like ? and  pro.bhog_date BETWEEN ? AND ? GROUP BY pro.pathi_Name";
                roulBeanList =
                    jdbcTemplate.query(
                        sql,
                        new Object[] {
                            pathReport.getPathi().getName(),
                            pathReport.getPathType().toString(),
                            pathReport.getStartDate(),
                            pathReport.getEndDate(),
                        },
                        new RoulRowMapper()
                    );
                String pathNamesql =
                    "SELECT DISTINCT ( pro.jhi_desc)  as pathi_Name  , pro.bhog_date  FROM  P_Roul pro where pro.pathi_name=? and pro.bhog_date BETWEEN ? AND ? GROUP BY pro.bhog_date , pro.jhi_desc";
                pathNameBeanList =
                    jdbcTemplate.query(
                        pathNamesql,
                        new Object[] { pathReport.getPathi().getName(), pathReport.getStartDate(), pathReport.getEndDate() },
                        new PathRoulRowMapper()
                    );
            } else {
                log.warn("  Report type is selected pathi singh name but pathi not selected");
            }
        } else {
            rouls = pRoulRepository.findByBhogDateBetween(pathReport.getStartDate(), pathReport.getEndDate());
            //  rouls=	 pRoulRepository.findAllPaybelDutiesByBhogDateBetween(pathReport.getStartDate(), pathReport.getEndDate());
            //   Map<String, List<PRoul>> studlistGrouped =   rouls.stream().collect(Collectors.groupingBy(w -> w.getPathiName()));
            String sql =
                "SELECT DISTINCT ( pro.pathi_Name)  ,   SUM (pro.total_Roul)  as  RoulTotal  , SUM (pro.total_amt)  as RoulTotalAmt  FROM  P_Roul pro where pro.bhog_date BETWEEN ? AND ? GROUP BY pro.pathi_Name";
            roulBeanList =
                jdbcTemplate.query(sql, new Object[] { pathReport.getStartDate(), pathReport.getEndDate() }, new RoulRowMapper());

            String pathNamesql =
                "SELECT DISTINCT ( pro.jhi_desc)  as pathi_Name  , pro.bhog_date  FROM  P_Roul pro where pro.bhog_date BETWEEN ? AND ? GROUP BY pro.bhog_date , pro.jhi_desc";
            pathNameBeanList =
                jdbcTemplate.query(
                    pathNamesql,
                    new Object[] { pathReport.getStartDate(), pathReport.getEndDate() },
                    new PathRoulRowMapper()
                );
            /*
             * log.warn(" Test report selected  " +roulBeanList);
             *
             *
             * log.warn(" Test report pathNameBeanList selected  " +pathNameBeanList);
             */

        }

        log.warn("json object found   " + roulBeanList);
        //  String json = CsvHelper.ListJson(roulBeanList);

        String headers = "SR.NO  , PATHI NAME , TOTAL ROUL , TOTAL MONEY ,  SIGNATURE";
        String pathheaders = "SR.NO , PATH INFO , PATH BHOG DATE";

        String json = OpenCsvHelper.ListJson(roulBeanList);

        String pathjson = OpenCsvHelper.ListJson(pathNameBeanList);

        json = headers + "\n" + json;

        pathjson = pathheaders + "\n" + pathjson;

        if (json != null) {
            log.warn("json object found   " + json);

            double sum = roulBeanList.stream().mapToDouble(PRoulBean::getTotalAmt).sum();
            double roulsum = roulBeanList.stream().mapToDouble(PRoulBean::getTotalRoul).sum();

            double totalPath = pathNameBeanList.stream().count();

            ReportObj reportObj = new ReportObj("Roul Bheta  ", pathReport.getStartDate(), pathReport.getEndDate(), sum);
            final Map<String, String> testMap = new LinkedHashMap<String, String>();
            testMap.put("ਰਿਪੋਰਟ  ਦੀ ਤਾਰੀਖ      ", LocalDate.now().toString());
            testMap.put("ਰਿਪੋਰਟ  ", reportObj.getReportType());
            testMap.put("ਤਾਰੀਖ  ਤੋਂ ", reportObj.getStartDate().toString());
            testMap.put("ਤਾਰੀਖ ਤੱਕ ", reportObj.getEndDate().toString());
            testMap.put("ਕੁੱਲ ਭੇਟਾ", reportObj.getReportTotal().toString());
            testMap.put(" ਕੁੱਲ ਰੌਲਾਂ", "" + roulsum);
            testMap.put(" ਕੁੱਲ ਪਾਠ  ", "" + totalPath);
            testMap.put("GENERATED BY ", reportObj.getReportType());

            byte[] generatedPdf = CsvToPdfConverter.pathiReportcsvToPdfConverter(json, pathjson, reportObj, testMap);

            if (generatedPdf == null) {
                log.warn("Generated Pdf null ");
            } else {
                pathReport.setReport(generatedPdf);
                pathReport.setReportContentType("application/pdf");
            }
        }

        return pathReport;
    }
}

class RoulRowMapper implements RowMapper<PRoulBean> {

    @Override
    public PRoulBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        PRoulBean pRoulBean = new PRoulBean();
        pRoulBean.setIndex(rowNum + 1);
        pRoulBean.setPathiName(rs.getString("pathi_name"));
        pRoulBean.setTotalRoul(rs.getDouble("RoulTotal"));
        pRoulBean.setTotalAmt(rs.getDouble("RoulTotalAmt"));
        pRoulBean.setSign(" ਦਸਤਖਤ                         ਤਾਰੀਖ                  N");
        return pRoulBean;
    }
}

class PathRoulRowMapper implements RowMapper<PRoulBeanBhogDate> {

    @Override
    public PRoulBeanBhogDate mapRow(ResultSet rs, int rowNum) throws SQLException {
        PRoulBeanBhogDate pRoulBean = new PRoulBeanBhogDate();
        pRoulBean.setIndex(rowNum + 1);
        pRoulBean.setPathiName(rs.getString("pathi_Name"));
        pRoulBean.setBhogDate(rs.getDate("bhog_date").toString());
        return pRoulBean;
    }
}
