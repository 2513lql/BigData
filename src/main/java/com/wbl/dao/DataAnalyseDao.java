package com.wbl.dao;

import com.wbl.modal.CountModal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 17:16
 */
public interface DataAnalyseDao {
        /**
         * query data times of operate in a day
         * @param dataId id of data
         * @param month month
         * @param year year
         * @return countModal
         */
        List<CountModal> getDataOperateTimesByDays(@Param("dataId")String dataId,@Param("month")String month,
                                                   @Param("year")String year);

        /**
         * query data times of operate in a month
         * @param dataId id of data
         * @param year year
         * @return countModal
         */
        List<CountModal> getDataOperateTimesByMonth(@Param("dataId")String dataId,@Param("year")String year);

        /**
         * query data times of operate group by year
         * @param dataId id of data
         * @return countModal
         */
        List<CountModal> getDataOperateTimesByYear(@Param("dataId")String dataId);

        /**
         * query data operate times group by type
         * @param dataId id of data
         * @return countModal
         */
        List<CountModal> getDataOperateTimesByType(String dataId);


        List<CountModal> getHotDataByDownloadTimes();

        List<CountModal> getSupplyTimesByDataId(String dataId);

        List<CountModal> getUseTimesByDataId(String dataId);
}
